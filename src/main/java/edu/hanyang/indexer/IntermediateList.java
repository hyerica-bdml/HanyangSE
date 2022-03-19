package edu.hanyang.indexer;

public interface IntermediateList {
    void putDocId(int docId);
    int getDocumentCount();
    int getDocumentId(int location);
}
