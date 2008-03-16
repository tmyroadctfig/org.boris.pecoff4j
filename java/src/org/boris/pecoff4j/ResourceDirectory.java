package org.boris.pecoff4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.boris.pecoff4j.io.ByteArrayDataReader;
import org.boris.pecoff4j.io.IDataReader;
import org.boris.pecoff4j.util.Reflection;

public class ResourceDirectory {
    private ResourceDirectoryEntry entry;
    private ResourceTypeDirectory[] types;

    public static ResourceDirectory parse(byte[] data, int baseAddress) throws IOException {
        ResourceDirectory rd = new ResourceDirectory();
        IDataReader dr = new ByteArrayDataReader(data);
        rd.entry = ResourceDirectoryEntry.read(dr);

        ResourcePointer[] pointers = rd.entry.getEntries();
        rd.types = new ResourceTypeDirectory[pointers.length];
        for (int i = 0; i < pointers.length; i++) {
            dr.jumpTo(pointers[i].getOffsetToData());
            rd.types[i] = ResourceTypeDirectory.read(dr, baseAddress);
        }

        return rd;
    }

    public ResourceEntry[] findResources(int type) {
        return findResources(type, -1);
    }

    public ResourceEntry[] findResources(int type, int name) {
        List<ResourceEntry> entries = new ArrayList();
        for (int i = 0; i < types.length; i++) {
            if (getEntryName(i) == type) {
                for (int j = 0; j < types[i].getNameCount(); j++) {
                    ResourceNameDirectory rn = types[i].getName(j);
                    int nameId = types[i].getEntryName(j);
                    if (name == -1 || name == nameId) {
                        for (int k = 0; k < rn.getLanguageCount(); k++) {
                            int language = types[i].getName(j).getEntryName(k);
                            byte[] data = rn.getLanguage(k).getData();
                            entries.add(new ResourceEntry(data, nameId, language));
                        }
                    }
                }
            }
        }

        return entries.toArray(new ResourceEntry[0]);
    }

    public int getTypeCount() {
        return types.length;
    }

    public ResourceTypeDirectory getType(int index) {
        return types[index];
    }

    public int getEntryName(int index) {
        return entry.getEntries()[index].getName();
    }

    public String toString() {
        return Reflection.toString(this);
    }
}
