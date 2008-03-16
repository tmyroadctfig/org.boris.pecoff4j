package org.boris.pecoff4j;

import java.io.IOException;

import org.boris.pecoff4j.io.IDataReader;
import org.boris.pecoff4j.util.Reflection;

public class ResourceLanguageDirectory {
    private ResourceDataEntry entry;
    private byte[] data;

    public static ResourceLanguageDirectory read(IDataReader dr, int baseAddress)
            throws IOException {
        ResourceLanguageDirectory rl = new ResourceLanguageDirectory();
        rl.entry = ResourceDataEntry.read(dr);
        int offset = rl.entry.getOffsetToData();
        int size = rl.entry.getSize();
        if (offset != 0 && size != 0) {
            rl.data = new byte[size];
            dr.jumpTo(offset - baseAddress);
            dr.read(rl.data);
        }
        return rl;
    }

    public ResourceDataEntry getEntry() {
        return entry;
    }

    public byte[] getData() {
        return data;
    }

    public String toString() {
        return Reflection.toString(this);
    }
}
