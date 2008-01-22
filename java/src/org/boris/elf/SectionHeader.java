package org.boris.elf;

import java.io.IOException;

import org.boris.pecoff4j.io.DataReader;
import org.boris.pecoff4j.io.Reflection;

public class SectionHeader {
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

    public static SectionHeader read(DataReader dr) throws IOException {
        SectionHeader sh = new SectionHeader();
        sh.name = dr.readDoubleWord();
        sh.type = dr.readDoubleWord();
        sh.flags = dr.readDoubleWord();
        sh.addr = dr.readDoubleWord();
        sh.offset = dr.readDoubleWord();
        sh.size = dr.readDoubleWord();
        sh.link = dr.readDoubleWord();
        sh.info = dr.readDoubleWord();
        sh.addralign = dr.readDoubleWord();
        sh.entsize = dr.readDoubleWord();

        return sh;
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
