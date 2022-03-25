package io.github.hyerica_bdml.utils;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

public class SubmitClassLoader {

    public static <T> T getSubmitInstance(final String submitJarPath, final String className) {
        File submitJarFile = new File(submitJarPath);

        if (!submitJarFile.exists()) {
            System.out.println("Submit jar file is not found!");
            return null;
        }

        try {
            System.out.println(submitJarFile.toURI());
            System.out.println(submitJarFile.toURI().toURL());
            URLClassLoader classLoader = new URLClassLoader(new URL[] {submitJarFile.toURI().toURL()}, Thread.currentThread().getContextClassLoader());
            for (URL url : classLoader.getURLs()) {
                System.out.println(url);
            }

            // Class<T> cls = (Class<T>) classLoader.loadClass(className);
            Class<T> cls = (Class<T>) Class.forName(className, true, classLoader);
            classLoader.close();

            return cls.newInstance();
        } catch (Exception exc) {
            exc.printStackTrace();
            return null;
        }
    }

    private SubmitClassLoader() {

    }
}