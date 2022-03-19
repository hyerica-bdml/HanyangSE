package edu.hanyang.indexer;

import edu.hanyang.services.ServiceProvider;

import java.io.IOException;
import java.io.RandomAccessFile;

public class InvIdxDocumentCursor extends DocumentCursor {

    private RandomAccessFile raf;
    private int termId;
    private LIST_TYPE type;

    private int offset;
    private int size;
    private int numOfDocs;
    private int minDocId;
    private int maxDocId;

    private int currentDocId;
    private int numOfPos;

    private int blockSize, nBlocks;

    public InvIdxDocumentCursor(int termId,
                                int postingOffset,
                                LIST_TYPE type,
                                String postingListFileName,
                                int blockSize,
                                int nBlocks) {
        try {
            this.termId = termId;
            this.type = type;
            this.blockSize = blockSize;
            this.nBlocks = nBlocks;

            raf = new RandomAccessFile(postingListFileName, "r");
            raf.seek(postingOffset);

            size = raf.readInt();
            numOfDocs = raf.readInt();
            minDocId = raf.readInt();
            maxDocId = raf.readInt();
            offset = postingOffset + 16;

            currentDocId = raf.readInt();
            numOfPos = raf.readInt();
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }

    @Override
    public boolean isEol() throws IOException {
        return offset + 8 + 4*numOfPos >= size;
    }

    @Override
    public int getDocId() throws IOException {
        return currentDocId;
    }

    @Override
    public void goNext() throws IOException {
        if (isEol())
            throw new IOException("End of posting list");

        offset += 8 + numOfPos*4;
        raf.seek(offset);

        currentDocId = raf.readInt();
        numOfPos = raf.readInt();
    }

    @Override
    public PositionCursor getPositionCursor() throws IOException {
        return null;
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
