package org.boris.pecoff4j;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * Encapsulates the Debug Directory (Image Only). Section 6.1.1 of the PE/COFF
 * spec v8.
 */
public class DebugDirectory {
    private int characteristics;
    private int timeDateStamp;
    private int majorVersion;
    private int type;
    private int sizeOfData;
    private int addressOfRawData;
    private int pointerToRawData;

    public void read(DataInputStream dis) throws IOException {
        characteristics = dis.readInt();
        timeDateStamp = dis.readInt();
        majorVersion = dis.readUnsignedShort();
        majorVersion = dis.readUnsignedShort();
        type = dis.readInt();
        sizeOfData = dis.readInt();
        addressOfRawData = dis.readInt();
        pointerToRawData = dis.readInt();
    }
}
