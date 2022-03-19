package edu.hanyang.services;

import edu.hanyang.indexer.BPlusTree;
import edu.hanyang.indexer.InvertedList;
import edu.hanyang.utils.SubmitClassLoader;

import java.io.*;

public class IndexService {

    public IndexService() {

    }

    public BPlusTree createNewBPlusTree(String metaFile,
                                        String treeFile,
                                        int blockSize,
                                        int nBlocks) {
        return loadBPlusTree(metaFile, treeFile, blockSize, nBlocks);
    }

    public BPlusTree createNewBPlusTree(String jarFileName,
                                        String metaFile,
                                        String treeFile,
                                        int blockSize,
                                        int nBlocks) {
        return loadBPlusTree(
                jarFileName,
                metaFile,
                treeFile,
                blockSize,
                nBlocks
        );
    }

    // Make B+Tree Posting List
    public void constructTreeFromPostingList(BPlusTree btree,
                                             String postingListFile) {

        try (RandomAccessFile raf = new RandomAccessFile(postingListFile, "r")) {

            long offset = 0;
            int currentWordID = 0;

            System.out.println("=== CONSTRUCTING POSTING LIST ===");

            while (raf.length() > raf.getFilePointer()) {
                btree.insert(currentWordID, (int) offset);
                currentWordID += 1;

                int size = raf.readInt();
                offset += 16 + size;
                raf.seek(offset);
                System.out.println("TermID: " + currentWordID);
                System.out.println("OFFSET: " + offset);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public InvertedList createNewInvertedList(BPlusTree btree,
                                              String postingListFile,
                                              String sortedTripleListFile,
                                              int blockSize,
                                              int nBlocks) {
        return new InvertedList(
                btree,
                postingListFile,
                sortedTripleListFile,
                blockSize,
                nBlocks
        );
    }

    private BPlusTree loadBPlusTree(String jarFilePath,
                                    String metaFile,
                                    String treeFile,
                                    int blockSize,
                                    int nBlocks) {

        try {
            BPlusTree btree = SubmitClassLoader.getSubmitInstance(jarFilePath, "edu.hanyang.submit.HanyangSEBPlusTree");
            btree.open(metaFile, treeFile, blockSize, nBlocks);
            return btree;
        } catch (IOException exc) {
            exc.printStackTrace();
            return null;
        }
    }

    private BPlusTree loadBPlusTree(String metaFile,
                                    String treeFile,
                                    int blockSize,
                                    int nBlocks) {
        try {
            Class<?> cls = Class.forName("edu.hanyang.submit.HanyangSEBPlusTree");
            BPlusTree btree = (BPlusTree) cls.newInstance();
            btree.open(metaFile, treeFile, blockSize, nBlocks);
            return btree;
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException exc) {
            exc.printStackTrace();
            return null;
        } catch (IOException exc) {
            exc.printStackTrace();
            return null;
        }
    }
}
