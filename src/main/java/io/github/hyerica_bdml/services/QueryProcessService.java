package io.github.hyerica_bdml.services;

import io.github.hyerica_bdml.indexer.*;
import io.github.hyerica_bdml.utils.*;

import java.io.IOException;

public class QueryProcessService {

    public QueryProcessService() {

    }

    public QueryProcess createNewQueryProcess() {
        return loadQueryProcess();
    }
    public QueryProcess createNewQueryProcess(String jarFilePath) {
        return loadQueryProcess(jarFilePath);
    }

    private QueryProcess loadQueryProcess() {
        try {
            Class<?> cls = Class.forName("edu.hanyang.submit.HanyangSEQueryProcess");
            return (QueryProcess) cls.newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException exc) {
            exc.printStackTrace();
            return null;
        }
    }

    private QueryProcess loadQueryProcess(String jarFileName) {

        return SubmitClassLoader.getSubmitInstance(jarFileName, "edu.hanyang.submit.HanyangSEQueryProcess");
    }
}
