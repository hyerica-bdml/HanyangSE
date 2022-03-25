package io.github.hyerica_bdml.indexer;

import java.io.IOException;

import io.github.hyerica_bdml.indexer.*;

/**
 * Interface for a query processor
 * 
 * @author Younghoon Kim
 *
 */
public interface QueryProcess {
    /**
     * Intersection with two inverted lists without considering positions of terms
     * 
     * @param op1	The DocumentCursor instance to access the left inverted list 
     * @param op2	The DocumentCursor instance to access the right inverted list
     * @param out	The IntermediateList instance to output the result
     * @throws IOException	Exception while accessing inverted list
     */
    public void opAndWithoutPosition (DocumentCursor op1, DocumentCursor op2, IntermediateList out) throws IOException;
    
    /**
     * Positional intersection with two inverted lists given the distance between two terms in query
     * 
     * @param op1	The DocumentCursor instance to access the left inverted list 
     * @param op2	The DocumentCursor instance to access the right inverted list
     * @param shift	The distance between two terms in query
     * @param out	The IntermediatePositionalList instance to output the result
     * @throws IOException	Exception while accessing inverted list
     */
    public void opAndWithPosition (DocumentCursor op1, DocumentCursor op2, int shift, IntermediatePositionalList out) throws IOException;
    
    /**
     * Generating query plan tree by parsing the query string
     *  
     * @param query	The query string
     * @param stat	The API to obtain statistics of index terms (e.g., inverted list size)
     * @return	A query plan tree
     * @throws Exception	Exception while parsing query
     */
    public QueryPlanTree parseQuery(String query, StatAPI stat) throws Exception;
}
