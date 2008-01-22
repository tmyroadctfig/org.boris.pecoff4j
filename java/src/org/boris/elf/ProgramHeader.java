package org.boris.elf;

import java.io.IOException;

import org.boris.pecoff4j.io.DataReader;
import org.boris.pecoff4j.io.Reflection;

public class ProgramHeader {
    private int name;
    private int type;
    private int flags;
    private int addr;
    private int offset;
    private int size;
    private int link;
    private int info;
    private int addralign;
    private int entsize;

    public static ProgramHeader read(DataReader dr) throws IOException {
        ProgramHeader ep = new ProgramHeader();

        ep.name = dr.readWord();
        ep.type = dr.readWord();
        ep.flags = dr.readWord();
        ep.addr = dr.readDoubleWord();
        ep.offset = dr.readDoubleWord();
        ep.size = dr.readWord();
        ep.link = dr.readWord();
        ep.info = dr.readWord();
        ep.addralign = dr.readWord();
        ep.entsize = dr.readWord();
        return ep;
    }

    public String toString() {
        return Reflection.toString(this);
    }

    public int getName() {
        return name;
    }

    public int getType() {
        return type;
    }

    public int getFlags() {
        return flags;
    }

    public int getAddr() {
        return addr;
    }

    public int getOffset() {
        return offset;
    }

    public int getSize() {
        return size;
    }

    public int getLink() {
        return link;
    }

    public int getInfo() {
        return info;
    }

    public int getAddralign() {
        return addralign;
    }

    public int getEntsize() {
        return entsize;
    }
}
