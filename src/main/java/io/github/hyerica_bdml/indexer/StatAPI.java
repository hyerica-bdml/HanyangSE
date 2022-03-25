package io.github.hyerica_bdml.indexer;

public abstract class StatAPI {
    public abstract int getPages(int termId) throws Exception;
    public abstract int getDocCount(int termId) throws Exception;
    public abstract int getMinDocId(int termId) throws Exception;
    public abstract int getMaxDocId(int termId) throws Exception;
}
