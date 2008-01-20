package org.boris.pecoff4j;

import java.io.File;

import org.boris.pecoff4j.io.PEParser;

/**
 * An example demonstrating parsing an executables.
 */
public class ParseExecutableTest {
    public static void main(String[] args) throws Exception {
        PE pe = PEParser.parse(new File("c:/windows/system32/notepad.exe"));
        System.out.println(pe);
    }
}
