package io.github.hyerica_bdml.indexer;

/**
 * The interface to write intermediate list of the result of positional intersection operation
 * 
 * @author Younghoon Kim
 *
 */
public interface IntermediatePositionalList {
    void putDocIdAndPos(int docId, int pos);
    int getPositionCount();
    int getDocumentId(int location);
    int getPosition(int location);
}
