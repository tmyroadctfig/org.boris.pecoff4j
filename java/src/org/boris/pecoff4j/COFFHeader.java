package org.boris.pecoff4j;
import java.io.IOException;

import org.boris.pecoff4j.io.DataWriter;
import org.boris.pecoff4j.io.IDataReader;
import org.boris.pecoff4j.util.Reflection;

public class COFFHeader {
    private int machine;
    private int numberOfSections;
    private int timeDateStamp;
    private int pointerToSymbolTable;
    private int numberOfSymbols;
    private int sizeOfOptionalHeader;
    private int characteristics;

    public static COFFHeader read(IDataReader dr) throws IOException {
        COFFHeader h = new COFFHeader();
        h.readFrom(dr);
        return h;
    }

    public void readFrom(IDataReader dr) throws IOException {
        machine = dr.readWord();
        numberOfSections = dr.readWord();
        timeDateStamp = dr.readDoubleWord();
        pointerToSymbolTable = dr.readDoubleWord();
        numberOfSymbols = dr.readDoubleWord();
        sizeOfOptionalHeader = dr.readWord();
        characteristics = dr.readWord();
    }

    public String toString() {
        return Reflection.toString(this);
    }

    public int getMachine() {
        return machine;
    }

    public int getNumberOfSections() {
        return numberOfSections;
    }

    public int getTimeDateStamp() {
        return timeDateStamp;
    }

    public int getPointerToSymbolTable() {
        return pointerToSymbolTable;
    }

    public int getNumberOfSymbols() {
        return numberOfSymbols;
    }

    public int getSizeOfOptionalHeader() {
        return sizeOfOptionalHeader;
    }

    public int getCharacteristics() {
        return characteristics;
    }

    public void write(DataWriter dw) {
    }

}
