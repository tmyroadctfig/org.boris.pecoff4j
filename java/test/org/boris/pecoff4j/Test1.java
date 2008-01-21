package org.boris.pecoff4j;

import java.io.File;

import org.boris.pecoff4j.io.ByteArrayDataReader;
import org.boris.pecoff4j.io.DataReader;

public class Test1 {
    public static void main(String[] args) throws Exception {
        testExe("C:/development/releases/winrun4j/bin/winrun4j.exe");
        // for (int i = 0; i < 10; i++) {
        // testExe(FoundFiles.EXE_FILES[i]);
        // }
    }

    public static void testExe(String file) throws Exception {
        DataReader dr = ByteArrayDataReader.create(new File(file));
    }
}
