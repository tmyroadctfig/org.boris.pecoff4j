package org.boris.elf;

import java.io.IOException;

import org.boris.pecoff4j.io.DataReader;
import org.boris.pecoff4j.io.Reflection;

public class Header {
    private int type;
    private int offset;
    private int vaddr;
    private int paddr;
    private int filesz;
    private int memsz;
    private int flags;
    private int align;

    public static Header read(DataReader dr) throws IOException {
        Header eh = new Header();

        eh.type = dr.readDoubleWord();
        eh.offset = dr.readWord();
        eh.vaddr = dr.readDoubleWord();
        eh.paddr = dr.readDoubleWord();
        eh.filesz = dr.readDoubleWord();
        eh.memsz = dr.readDoubleWord();
        eh.flags = dr.readDoubleWord();
        eh.align = dr.readByte();

        return eh;
    }

    public String toString() {
        return Reflection.toString(this);
    }

    public int getType() {
        return type;
    }

    public int getOffset() {
        return offset;
    }

    public int getVaddr() {
        return vaddr;
    }

    public int getPaddr() {
        return paddr;
    }

    public int getFilesz() {
        return filesz;
    }

    public int getMemsz() {
        return memsz;
    }

    public int getFlags() {
        return flags;
    }

    public int getAlign() {
        return align;
    }
}
