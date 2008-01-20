package org.boris.pecoff4j;

import java.io.IOException;

import org.boris.pecoff4j.io.DataReader;
import org.boris.pecoff4j.io.Reflection;

public class ImageDirectoryEntry {
    private int virtualAddress;
    private int size;

    public static ImageDirectoryEntry read(DataReader dr) throws IOException {
        ImageDirectoryEntry idd = new ImageDirectoryEntry();
        idd.readFrom(dr);
        return idd;
    }

    private void readFrom(DataReader dr) throws IOException {
        virtualAddress = dr.readDoubleWord();
        size = dr.readDoubleWord();
    }

    public String toString() {
        return Reflection.toString(this);
    }

    public int getVirtualAddress() {
        return virtualAddress;
    }

    public int getSize() {
        return size;
    }
}
