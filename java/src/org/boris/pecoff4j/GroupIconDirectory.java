package org.boris.pecoff4j;

import java.io.IOException;

import org.boris.pecoff4j.io.ByteArrayDataReader;
import org.boris.pecoff4j.io.IDataReader;
import org.boris.pecoff4j.util.Reflection;

public class GroupIconDirectory {
    private int reserved;
    private int type;
    private int count;
    private GroupIconDirectoryEntry[] entries;

    public int getReserved() {
        return reserved;
    }

    public int getType() {
        return type;
    }

    public int getCount() {
        return count;
    }

    public GroupIconDirectoryEntry getEntry(int index) {
        return entries[index];
    }

    public String toString() {
        return Reflection.toString(this);
    }

    public static GroupIconDirectory read(byte[] data) throws IOException {
        return read(new ByteArrayDataReader(data));
    }

    public static GroupIconDirectory read(IDataReader dr) throws IOException {
        GroupIconDirectory gi = new GroupIconDirectory();
        gi.reserved = dr.readWord();
        gi.type = dr.readWord();
        gi.count = dr.readWord();
        gi.entries = new GroupIconDirectoryEntry[gi.count];
        for (int i = 0; i < gi.count; i++) {
            gi.entries[i] = GroupIconDirectoryEntry.read(dr);
        }

        return gi;
    }
}
