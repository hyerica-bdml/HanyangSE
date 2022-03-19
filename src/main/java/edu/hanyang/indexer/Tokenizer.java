package edu.hanyang.indexer;

import java.util.List;

public interface Tokenizer {

    void setup();
    List<String> split(String str);
    void clean();
}
