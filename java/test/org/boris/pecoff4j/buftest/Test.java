package org.boris.pecoff4j.buftest;

import java.io.File;

import org.boris.pecoff4j.util.PEParser;

public class Test
{
    public static void main(String[] args) throws Exception {
        PEParser
                .parse(new File(
                        "F:\\program files\\Common Files\\Apple\\Mobile Device Support\\bin\\AppleMobileBackup.exe"));
    }
}
