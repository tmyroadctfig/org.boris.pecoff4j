package org.boris.pecoff4j;

import java.io.DataInputStream;
import java.io.IOException;

import com.sun.org.apache.bcel.internal.util.ByteSequence;

/**
 * The section table.
 */
public class SectionHeader {
    private String name;
    private int virtualSize;
    private int virtualAddress;
    private int sizeOfRawData;
    private int pointerToRawData;
    private int pointerToRelocations;
    private int pointerToLinenumbers;
    private int numberOfRelocations;
    private int numberOfLinenumbers;
    private int charateristics;

    public void read(DataInputStream dis) throws IOException {
        byte[] n = new byte[8];
        dis.read(n);
        ByteSequence bs = new ByteSequence(n);
        name = DataInputStream.readUTF(bs);
        virtualSize = dis.readInt();
        virtualAddress = dis.readInt();
        sizeOfRawData = dis.readInt();
        pointerToRawData = dis.readInt();
        pointerToRelocations = dis.readInt();
        pointerToLinenumbers = dis.readInt();
        numberOfRelocations = dis.readUnsignedShort();
        numberOfLinenumbers = dis.readUnsignedShort();
        charateristics = dis.readInt();
    }
}
