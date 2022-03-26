package edu.hanyang.utils;

import edu.hanyang.indexer.IntermediatePositionalList;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TestIntermediatePositionalList implements IntermediatePositionalList {
    public List<Integer> docIdList;
    public List<Integer> posList;

    public TestIntermediatePositionalList() {
        docIdList = new LinkedList<>();
        posList = new LinkedList<>();
    }

    // list:    1 1 2 3 4
    // idx:     0 1 2 3 4
    // docIdx:  0
    // nPosIdx: 0 + 1 = 1
    // nPos:    5 - 0 - 1 = 4
    // result:  1 4 1 2 3 4, 2
    // idx:     0 1 2 3 4 5, 6

    // list:    1 4 1 2 3 4, 2 1 2
    // idx:     0 1 2 3 4 5, 6 7 8
    // docIdx:  6
    // nPosIdx: 6 + 1 = 7
    // nPos:    9 - 6 - 1 = 2
    // result:  1 4 1 2 3 4, 2 2 1 2
    @Override
    public void putDocIdAndPos(int docId, int pos) {
        int index = find(docId, pos);
        insert(index, docId, pos);
    }

    @Override
    public int getPositionCount() {
        return posList.size();
    }

    @Override
    public int getDocumentId(int location) {
        return docIdList.get(location);
    }

    @Override
    public int getPosition(int location) {
        return posList.get(location);
    }

    private int find(int docId, int pos) {
        int index;

        for (index = 0; index < posList.size(); index += 1) {
            int _docId = docIdList.get(index);
            int _pos = posList.get(index);

            if (docId <= _docId) {
                if (pos <= _pos) {
                    break;
                }
            }
        }

        return index;
    }

    private void insert(int index, int docId, int pos) {
        docIdList.add(index, docId);
        posList.add(index, pos);
    }
}
