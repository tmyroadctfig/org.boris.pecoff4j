package org.boris.pecoff4j;

import java.io.File;
import java.io.FilenameFilter;
import java.util.HashSet;
import java.util.Set;

public class FindExe {

    public static void main(String[] args) throws Exception {
        File[] files = findFiles(new File("C:/windows/system32"),
                new FilenameFilter() {
                    public boolean accept(File dir, String name) {
                        return name.endsWith(".dll");
                    }
                });
        for (File f : files) {
            System.out.println(f);
        }
    }

    public static File[] findFiles(File dir, FilenameFilter filter) {
        Set<File> files = new HashSet();
        findFiles(dir, filter, files);
        return files.toArray(new File[0]);
    }

    private static void findFiles(File dir, FilenameFilter filter,
            Set<File> files) {
        File[] f = dir.listFiles();
        for (File ff : f) {
            if (ff.isDirectory()) {
                findFiles(ff, filter, files);
            } else {
                if (filter.accept(ff.getParentFile(), ff.getName()))
                    files.add(ff);
            }
        }
    }
}
