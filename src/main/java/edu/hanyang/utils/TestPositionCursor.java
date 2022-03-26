package edu.hanyang.utils;

import java.io.IOException;
import java.util.List;

import edu.hanyang.indexer.PositionCursor;

public class TestPositionCursor extends PositionCursor {
    public List<Integer> posList;
    public int currentPosIdx = 0;

    public TestPositionCursor(List<Integer> posList) {
        this.posList = posList;
        this.currentPosIdx = 0;
    }

    @Override
    public boolean isEol() {
        return currentPosIdx >= posList.size();
    }

    @Override
    public int getPos() throws IOException {
        if(isEol()){
            throw new IOException("Wrong use of PositionCursor : out of index");
        }
        return posList.get(currentPosIdx);
    }

    @Override
    public void goNext() throws IOException {
        if(isEol()){
            throw new IOException("Wrong use of PositionCursor : out of index");
        }

        currentPosIdx++;
    }

    @Override
    public int getTermCount() {
        return posList.size();
    }
}