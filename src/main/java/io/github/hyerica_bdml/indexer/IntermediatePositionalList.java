package io.github.hyerica_bdml.indexer;

public interface IntermediatePositionalList {
    void putDocIdAndPos(int docId, int pos);
    int getPositionCount();
    int getDocumentId(int location);
    int getPosition(int location);
}
