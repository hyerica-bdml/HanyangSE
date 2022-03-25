package io.github.hyerica_bdml.indexer;

/**
 * Interface for the 
 * @author nonga
 *
 */
public interface IntermediateList {
    void putDocId(int docId);
    int getDocumentCount();
    int getDocumentId(int location);
}
