package io.github.hyerica_bdml.indexer;

import java.io.IOException;


/**
 * Interface for B+ Tree
 *  
 * @author Younghoon Kim
 *
 *
 */
public interface BPlusTree {

    /**
     * Opening and initializing the directory 
     * 
     * @param metafile	A meta-file with configurations for the dictionary (e.g., pagesize)  
     * @param filepath	Directory or path for opening the dictionary
     * @param blocksize	Available blocksize in the main memory of the current system for B+ tree
     * @param nblocks	Available block numbers in the main memory of the current system for B+ tree
     * @throws IOException	Exception while opening B+ tree
     */
    void open(String metafile, String filepath, int blocksize, int nblocks) throws IOException;
    
    /**
     * Searching for a key
     * 
     * @param key	The integer key of index term to search
     * @return	Status code
     * @throws IOException	Exception while accessing B+ tree
     */
    int search(int key) throws IOException;
    
    /**
     * Inserting a key and the bound value
     * 
     * @param key Key
     * @param val Value
     * @throws IOException	Exception while accessing B+ tree
     */
    void insert(int key, int val) throws IOException;
    
    
    /**
     * Closing the dictionary
     * 
     * @throws IOException	Exception while closing B+ tree
     */
    void close() throws IOException;
}
