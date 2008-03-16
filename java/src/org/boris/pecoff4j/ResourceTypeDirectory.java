package org.boris.pecoff4j;

import java.io.IOException;

import org.boris.pecoff4j.io.IDataReader;
import org.boris.pecoff4j.util.Reflection;

public class ResourceTypeDirectory {
    private ResourceDirectoryEntry entry;
    private ResourceNameDirectory[] names;

    public static ResourceTypeDirectory read(IDataReader dr, int baseAddress) throws IOException {
        ResourceTypeDirectory rt = new ResourceTypeDirectory();
        rt.entry = ResourceDirectoryEntry.read(dr);
        ResourcePointer[] pointers = rt.entry.getEntries();
        rt.names = new ResourceNameDirectory[pointers.length];
        for (int i = 0; i < pointers.length; i++) {
            dr.jumpTo(pointers[i].getOffsetToData());
            rt.names[i] = ResourceNameDirectory.read(dr, baseAddress);
        }
        return rt;

    }

    public int getNameCount() {
        return names.length;
    }

    public ResourceNameDirectory getName(int index) {
        return names[index];
    }

    public int getEntryName(int index) {
        return entry.getEntries()[index].getName();
    }

    public String toString() {
        return Reflection.toString(this);
    }
}
