package org.boris.pecoff4j;

import java.io.IOException;

import org.boris.pecoff4j.io.DataReader;
import org.boris.pecoff4j.io.Reflection;

/**
 * Encapsulates the COFF header.
 */
public class COFFHeader {
    private int machine;
    private int numberOfSections;
    private long timeDateStamp;
    private long pointToSymbolTable;
    private long numberOfSymbols;
    private int sizeOfOptionalHeader;
    private int charateristics;

    public int getMachine() {
        return machine;
    }

    public int getNumberOfSections() {
        return numberOfSections;
    }

    public long getTimeDateStamp() {
        return timeDateStamp;
    }

    public long getPointToSymbolTable() {
        return pointToSymbolTable;
    }

    public long getNumberOfSymbols() {
        return numberOfSymbols;
    }

    public int getSizeOfOptionalHeader() {
        return sizeOfOptionalHeader;
    }

    public int getCharateristics() {
        return charateristics;
    }

    public static COFFHeader read(DataReader dr) throws IOException {
        COFFHeader ch = new COFFHeader();
        ch.readFrom(dr);
        return ch;
    }

    void readFrom(DataReader dis) throws IOException {
        machine = dis.readWord();
        numberOfSections = dis.readWord();
        timeDateStamp = dis.readDoubleWord();
        pointToSymbolTable = dis.readDoubleWord();
        numberOfSymbols = dis.readDoubleWord();
        sizeOfOptionalHeader = dis.readWord();
        charateristics = dis.readWord();
    }

    public String toString() {
        return Reflection.toString(this);
    }
}
