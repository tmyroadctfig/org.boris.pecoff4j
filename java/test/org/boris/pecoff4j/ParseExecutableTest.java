package org.boris.pecoff4j;

import org.boris.pecoff4j.parser.PEParser;

/**
 * An example demonstrating parsing an executables.
 */
public class ParseExecutableTest {
    public static void main(String[] args) throws Exception {
        PE pe = PEParser.parse("c:/windows/system32/notepad.exe");
        System.out.println(pe);
    }
}
