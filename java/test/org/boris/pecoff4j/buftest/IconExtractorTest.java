package org.boris.pecoff4j.buftest;

import java.io.File;
import java.io.IOException;

import org.boris.pecoff4j.util.IconExtractor;

public class IconExtractorTest
{
    private static int limit = 1000;
    private static File outdir = new File("F:/Development/icons");

    public static void main(String[] args) throws Exception {
        //IconExtractor.extract(new File("F:\\program files\\FlashGet\\gt.exe"), outdir);
        recurse(new File("F:/program files/"));
    }

    public static void recurse(File dir) {
        if (limit == 0)
            return;
        File[] f = dir.listFiles();
        for (File fs : f) {
            if (fs.isDirectory()) {
                recurse(fs);
            } else if (fs.getName().endsWith(".exe")) {
                try {
                    System.out.println(fs);
                    IconExtractor.extract(fs, outdir);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                limit--;
            }
        }
    }
}
