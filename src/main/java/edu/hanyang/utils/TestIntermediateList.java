package edu.hanyang.utils;

import edu.hanyang.indexer.IntermediateList;

import java.util.ArrayList;
import java.util.List;

public class TestIntermediateList implements IntermediateList {
    public List<Integer> docList;

    public TestIntermediateList() {
        docList = new ArrayList<>();
    }

    @Override
    public void putDocId(int docId) {
        docList.add(docId);
    }

    @Override
    public int getDocumentCount() {
        return docList.size();
    }

    @Override
    public int getDocumentId(int location) {
        return docList.get(location);
    }
}
