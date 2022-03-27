package io.github.hyerica_bdml.indexer;

import java.io.IOException;

/**
 * The interface of accessing inverted list
 * 
 * @author Younghoon Kim
 *
 */
public abstract class DocumentCursor {
    public enum LIST_TYPE { POSLIST, NONPOSLIST };
    public LIST_TYPE type = null;

    /**
     * @return
     * true if the cursor is currently located at the end of list
     * false otherwise
     */
    public abstract boolean isEol() throws IOException;
    /**
     * @return
     * docid at the current cursor
     */
    public abstract int getDocId() throws IOException;
    /**
     * shift the cursor to the next document
     */
    public abstract void goNext() throws IOException;
    /**
     * @return
     * an instance of PositionCursor; you may retrieve positions
     * where the term appears in the current document
     */
    public abstract PositionCursor getPositionCursor() throws IOException;
    /**
     * @return
     * the number of documents in the inverted list
     * @throws IOException
     * if the method is not implemented yet, it throws an exception
     */
    public abstract int getDocCount() throws IOException;
    /**
     * @return
     * the minimum document id
     * @throws IOException
     * if the method is not implemented yet, it throws an exception
     */
    public abstract int getMinDocId() throws IOException;
    /**
     * @return
     * the maximum document id
     * @throws IOException
     * if the method is not implemented yet, it throws an exception
     */
    public abstract int getMaxDocId() throws IOException;
}
