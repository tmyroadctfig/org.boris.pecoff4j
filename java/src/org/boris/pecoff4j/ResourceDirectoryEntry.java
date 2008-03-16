package org.boris.pecoff4j;

import java.io.IOException;

import org.boris.pecoff4j.io.IDataReader;
import org.boris.pecoff4j.util.Reflection;

public class ResourceDirectoryEntry {
    private int characteristics;
    private int timeDateStamp;
    private int majorVersion;
    private int minorVersion;
    private int numNamedEntries;
    private int numIdEntries;
    private ResourcePointer[] entries;

    public static ResourceDirectoryEntry read(IDataReader dr) throws IOException {
        ResourceDirectoryEntry rd = new ResourceDirectoryEntry();
        rd.characteristics = dr.readDoubleWord();
        rd.timeDateStamp = dr.readDoubleWord();
        rd.majorVersion = dr.readWord();
        rd.minorVersion = dr.readWord();
        rd.numNamedEntries = dr.readWord();
        rd.numIdEntries = dr.readWord();
        rd.entries = new ResourcePointer[rd.numIdEntries];
        for (int i = 0; i < rd.entries.length; i++) {
            rd.entries[i] = ResourcePointer.read(dr);
        }

        return rd;
    }

    public int getCharacteristics() {
        return characteristics;
    }

    public int getTimeDateStamp() {
        return timeDateStamp;
    }

    public int getMajorVersion() {
        return majorVersion;
    }

    public int getMinorVersion() {
        return minorVersion;
    }

    public int getNumNamedEntries() {
        return numNamedEntries;
    }

    public int getNumIdEntries() {
        return numIdEntries;
    }

    public ResourcePointer[] getEntries() {
        return entries;
    }

    public String toString() {
        return Reflection.toString(this);
    }
}
