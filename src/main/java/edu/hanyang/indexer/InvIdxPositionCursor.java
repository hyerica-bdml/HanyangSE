package edu.hanyang.indexer;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;

public class InvIdxPositionCursor extends PositionCursor {

    private RandomAccessFile raf;
    private int start, end, currentIndex;
    private int currentPosition;
    private byte[] buf;
    private ByteBuffer bbuf;

    public InvIdxPositionCursor(String postingListFile,
                                int start,
                                int end,
                                int blockSize,
                                int nBlocks) throws IOException {
        this.raf = new RandomAccessFile(postingListFile, "r");
        this.start = start;
        this.end = end;

        buf = new byte[blockSize];
        bbuf = ByteBuffer.wrap(buf);

        currentIndex = start;
        this.raf.seek(start);
        this.raf.read(buf);
        currentPosition = bbuf.getInt();
    }

    @Override
    public boolean isEol() throws IOException {
        return currentIndex + 4 >= end;
    }

    @Override
    public int getPos() throws IOException {
        return currentPosition;
    }

    @Override
    public void goNext() throws IOException {
        if (isEol())
            throw new IOException("End of position list");

        currentPosition = bbuf.getInt();
        currentIndex += 4;

        if (currentIndex + 4 >= buf.length) {
            bbuf.clear();
            this.raf.read(buf);
        }
    }

    @Override
    public int getTermCount() throws IOException {
        return (end - start)/4;
    }
}
