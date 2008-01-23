package org.boris.pecoff4j.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.boris.pecoff4j.PE;
import org.boris.pecoff4j.io.DataReader;
import org.boris.pecoff4j.io.DataReaderImpl;

public class PEParser {
    
    public static PE parse(String filename) throws IOException {
        return parse(new File(filename));
    }
    
    public static PE parse(File file) throws IOException {
        PE pe = new PE();
        DataReader dr = new DataReaderImpl(new FileInputStream(file));
        pe.read(dr);
        return pe;
    }
}
