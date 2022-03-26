package edu.hanyang.utils;

import java.io.IOException;
import java.util.List;

import edu.hanyang.indexer.DocumentCursor;
import edu.hanyang.indexer.IntermediateList;
import edu.hanyang.indexer.IntermediatePositionalList;
import edu.hanyang.indexer.PositionCursor;

public class TestDocumentCursor extends DocumentCursor {
    public List<Integer> posList; // word1: docID1, nPos, pos1, pos2.., docID2
    // word2: docID1, nPos, pos1, pos2.., docID2
    public int type; // type = 0: IntermediateList, type = 1: IntermediatePositionalList
    public int currentDocPos;

    public TestDocumentCursor(IntermediateList list) {
        this.posList = ((TestIntermediateList) list).docList;
        this.type = 0;
    }

    public TestDocumentCursor(IntermediatePositionalList list) {
        this.posList = ((TestIntermediatePositionalList) list).posList;
        this.type = 1;
    }

    public TestDocumentCursor(List<Integer> positionalList) {
        this.posList = positionalList;
        this.type = 1;
    }

    @Override
    public boolean isEol() {
        if (currentDocPos >= posList.size()) {
            return true;
        }
        return false;
    }

    @Override
    public int getDocId() {
        return posList.get(currentDocPos);
    }

    // postinglist:  1 2 1 2, 2 3 1 2 3
    // index number: 0 1 2 3, 4 5 6 7 8
    // 1st doc pos:  0 + 2 + 1 + 1 = 4
    // 2nd doc pos:  4 + 3 + 1 + 1 = 9
    @Override
    public void goNext() throws IOException {
        if (isEol()) {
            throw new IOException("Wrong use of DocumentCursor : out of index");
        }
        if (type == 0) { // docID list
            currentDocPos++;
        } else { // positional list
            int step = posList.get(currentDocPos + 1);
            currentDocPos += (step + 1 + 1);
        }
    }

    // postinglist:  1 2 1 2, 2 3 1 2 3
    // index number: 0 1 2 3, 4 5 6 7 8
    // start pos:    0 + 2 = 2
    // end pos:      2 + 2 = 4
    @Override
    public PositionCursor getPositionCursor() throws IOException {
        if (type == 0) {
            throw new IOException("Wrong use of DocumentCursor : document id list cannot make position cursor");
        }

        int start = currentDocPos + 2;
        int end = start + posList.get(currentDocPos + 1);
        return new TestPositionCursor(posList.subList(start, end));
    }

    @Override
    public int getDocCount() throws IOException {
        if (type == 0) { // docID list
            return posList.size();
        } else { // positional list
            int tmp = currentDocPos;
            currentDocPos = 0;
            int cnt = 0;

            while (!isEol()) {
                goNext();
                cnt++;
            }

            currentDocPos = tmp;
            return cnt;
        }
    }

    @Override
    public int getMinDocId() {
        return posList.get(0);
    }

    @Override
    public int getMaxDocId() throws IOException {
        if (type == 0) {
            return posList.get(posList.size() - 1);
        } else {
            int tmp = currentDocPos;
            currentDocPos = 0;
            int docID = 0;

            while (!isEol()) {
                docID = getDocId();
                goNext();
            }

            currentDocPos = tmp;
            return docID;
        }
    }
}