package org.boris.pecoff4j.resources;

import java.io.IOException;

import org.boris.pecoff4j.io.ByteArrayDataReader;
import org.boris.pecoff4j.io.IDataReader;
import org.boris.pecoff4j.util.Reflection;

public class VersionInfo {
    private int length;
    private int valueLength;
    private int type;
    private String key;
    private FixedFileInfo fixedFileInfo;
    private StringFileInfo stringFileInfo;
    private VarFileInfo varFileInfo;

    public static VersionInfo read(byte[] data) throws IOException {
        return read(new ByteArrayDataReader(data));
    }

    public static VersionInfo read(IDataReader dr) throws IOException {
        VersionInfo vi = new VersionInfo();
        vi.length = dr.readWord();
        vi.valueLength = dr.readWord();
        vi.type = dr.readWord();
        vi.key = dr.readUnicode();
        if (vi.key.length() % 2 == 1)
            dr.readWord(); // padding
        vi.fixedFileInfo = FixedFileInfo.read(dr);

        int length = dr.readWord(); // length
        dr.readWord(); // value length
        dr.readWord(); // type
        vi.stringFileInfo = StringFileInfo.read(dr, length);

        return vi;
    }

    public String toString() {
        return Reflection.toString(this);
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getValueLength() {
        return valueLength;
    }

    public void setValueLength(int valueLength) {
        this.valueLength = valueLength;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public FixedFileInfo getFixedFileInfo() {
        return fixedFileInfo;
    }

    public void setFixedFileInfo(FixedFileInfo fixedFileInfo) {
        this.fixedFileInfo = fixedFileInfo;
    }

    public StringFileInfo getStringFileInfo() {
        return stringFileInfo;
    }

    public void setStringFileInfo(StringFileInfo stringFileInfo) {
        this.stringFileInfo = stringFileInfo;
    }

    public VarFileInfo getVarFileInfo() {
        return varFileInfo;
    }

    public void setVarFileInfo(VarFileInfo varFileInfo) {
        this.varFileInfo = varFileInfo;
    }
}
