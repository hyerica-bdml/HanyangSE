package edu.hanyang.indexer;

public interface IntermediatePositionalList {
    void putDocIdAndPos(int docId, int pos);
    int getPositionCount();
    int getDocumentId(int location);
    int getPosition(int location);
}
