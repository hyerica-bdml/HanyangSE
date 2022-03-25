package io.github.hyerica_bdml.indexer;

import java.io.IOException;

/**
 * Interface for external merge-sort
 * 
 * @author Younghoon Kim
 *
 */
public interface ExternalSort {

    /**
     * Sorting the given input file to an output file
     * 
     * @param infile	Input file
     * @param outfile	Output file
     * @param tmpdir	Temporary directory to be used for saving intermediate results on 
     * @param blocksize	Available blocksize in the main memory of the current system
     * @param nblocks	Available block numbers in the main memory of the current system
     * @throws IOException	Exception while performing external sort
     */
    void sort(String infile, String outfile, String tmpdir, int blocksize, int nblocks) throws IOException;
}
