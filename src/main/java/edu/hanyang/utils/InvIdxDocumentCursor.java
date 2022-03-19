package edu.hanyang.utils;

import edu.hanyang.indexer.DocumentCursor;
import edu.hanyang.indexer.PositionCursor;

import java.io.IOException;
import java.io.RandomAccessFile;

public class InvIdxDocumentCursor extends DocumentCursor {

    private RandomAccessFile raf;
    private int termId;
    private LIST_TYPE type;
    private String postingListFileName;

    private int offset, size, numOfDocs, minDocId, maxDocId, currentDocId;
    private int numOfPos;
    private int blockSize, nBlocks;
    private int startIndex, endIndex;

    public InvIdxDocumentCursor(String postingListFileName,
                                LIST_TYPE type,
                                int termId,
                                int postingOffset,
                                int blockSize,
                                int nBlocks) {
        try {
            this.postingListFileName = postingListFileName;
            this.termId = termId;
            this.type = type;
            this.blockSize = blockSize;
            this.nBlocks = nBlocks;
            raf = new RandomAccessFile(postingListFileName, "r");

            offset = postingOffset;
            raf.seek(offset);

            size = raf.readInt();
            startIndex = postingOffset + 16;
            endIndex = startIndex + size;

            numOfDocs = raf.readInt();
            minDocId = raf.readInt();
            maxDocId = raf.readInt();
            offset += 16;

            currentDocId = raf.readInt();
            numOfPos = raf.readInt();
        } catch (IOException exc) {
            System.out.println("IOEXCEPTION");
            System.out.println("TermId: " + termId);
            System.out.println("Seek: " + offset);
            exc.printStackTrace();
        }
    }

    @Override
    public boolean isEol() throws IOException {
        return offset >= endIndex;
    }

    @Override
    public int getDocId() throws IOException {
        return currentDocId;
    }

    @Override
    public void goNext() throws IOException {
        if (isEol())
            throw new RuntimeException("End of posting list");

        offset += 8 + numOfPos * 4;

        if (!isEol()) {
            raf.seek(offset);

            currentDocId = raf.readInt();
            numOfPos = raf.readInt();
        }
    }

    @Override
    public PositionCursor getPositionCursor() throws IOException {
        int start = offset + 8;
        int end = start + numOfPos * 4;
        return new InvIdxPositionCursor(
                postingListFileName,
                start,
                end,
                blockSize,
                nBlocks
        );
    }

    @Override
    public int getDocCount() throws IOException {
        return numOfDocs;
    }

    @Override
    public int getMinDocId() throws IOException {
        return minDocId;
    }

    @Override
    public int getMaxDocId() throws IOException {
        return maxDocId;
    }
}
