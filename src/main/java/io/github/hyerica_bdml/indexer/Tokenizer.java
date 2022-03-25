package io.github.hyerica_bdml.indexer;

import java.util.List;

/**
 * Interface for tokenizers
 * 
 * @author Younghoon Kim
 *
 */
public interface Tokenizer {

    /**
     * Initializing a tokenizer
     * 
     */
    void setup();
    
    /**
     * Extracting tokens from a given input string
     * 
     * @param str	Input string
     * @return	List of tokens
     */
    List<String> split(String str);
    
    /**
     * Finalizing the tokenizer
     * 
     */
    void clean();
}
