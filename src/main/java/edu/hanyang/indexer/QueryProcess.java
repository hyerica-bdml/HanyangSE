package edu.hanyang.indexer;

import edu.hanyang.indexer.*;

import java.io.IOException;

public interface QueryProcess {
    public void opAndWithoutPosition (DocumentCursor op1, DocumentCursor op2, IntermediateList out) throws IOException;
    public void opAndWithPosition (DocumentCursor op1, DocumentCursor op2, int shift, IntermediatePositionalList out) throws IOException;
    public QueryPlanTree parseQuery(String query, StatAPI stat) throws Exception;
}
