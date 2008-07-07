/*******************************************************************************
 * This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at 
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     Peter Smith
 *******************************************************************************/
package org.boris.pecoff4j.resources;

import java.io.IOException;

import org.boris.pecoff4j.io.ByteArrayDataReader;
import org.boris.pecoff4j.io.IDataReader;
import org.boris.pecoff4j.io.IDataWriter;
import org.boris.pecoff4j.util.Reflection;

public class IconDirectory {
    private int reserved;
    private int type;
    private int count;
    private IconDirectoryEntry[] entries;

    public int getReserved() {
        return reserved;
    }

    public int getType() {
        return type;
    }

    public int getCount() {
        return count;
    }

    public IconDirectoryEntry getEntry(int index) {
        return entries[index];
    }

    public String toString() {
        return Reflection.toString(this);
    }

    public static IconDirectory read(byte[] data) throws IOException {
        return read(new ByteArrayDataReader(data));
    }

    public static IconDirectory read(IDataReader dr) throws IOException {
        IconDirectory gi = new IconDirectory();
        gi.reserved = dr.readWord();
        gi.type = dr.readWord();
        gi.count = dr.readWord();
        gi.entries = new IconDirectoryEntry[gi.count];
        for (int i = 0; i < gi.count; i++) {
            gi.entries[i] = IconDirectoryEntry.read(dr);
        }

        return gi;
    }

    public void write(IDataWriter dw) throws IOException {
        dw.writeWord(reserved);
        dw.writeWord(type);
        dw.writeWord(count);
        for (int i = 0; i < count; i++) {
            entries[i].write(dw);
        }
    }

    public void setReserved(int reserved) {
        this.reserved = reserved;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setEntries(IconDirectoryEntry[] entries) {
        this.entries = entries;
    }

    public int sizeOf() {
        return 6 + count * IconDirectoryEntry.sizeOf();
    }
}
