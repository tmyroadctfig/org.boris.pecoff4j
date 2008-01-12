package org.boris.pecoff4j;

import java.io.IOException;

import org.boris.pecoff4j.io.DataReader;
import org.boris.pecoff4j.io.OldDataReader;

/**
 * Encapsulates the optional header.
 */
public class OptionalHeader {
    public static final int MAGIC_PE32 = 0x10b;
    public static final int MAGIC_PE32_PLUS = 0x20b;
    private int magic;
    private int majorLinkerVersion;
    private int minorLinkerVersio;
    private long sizeOfCode;
    private long sizeOfInitializedData;
    private long sizeOfUninitializedData;
    private long addressOfEntryPoint;
    private long baseOfCode;
    private long baseOfData;

    // Windows specific fields
    boolean containsWindowsSpecificFields = false;
    private long imageBase;
    private long sectionAlignment;
    private long fileAlignment;
    private int majorOperatingSystem;
    private int minorOperatingSystem;
    private int majorImageVersion;
    private int minorImageVersion;
    private int majorSubsystemVersion;
    private int minorSubsystemVersion;
    private long win32VersionValue;
    private long sizeOfImage;
    private long sizeOfHeaders;
    private long checkSum;
    private int subsystem;
    private int dllCharacteristics;
    private long sizeOfStackReserve;
    private long sizeOfStackCommit;
    private long sizeOfHeapReserve;
    private long sizeOfHeapCommit;
    private long loaderFlags;
    private long numberOfRvaAndSizes;

    public static OptionalHeader read(DataReader dr) throws IOException {
        return null;
    }

    void readFrom(DataReader dis) throws IOException {
        magic = dis.readWord();
        majorLinkerVersion = dis.readByte();
        minorLinkerVersio = dis.readByte();
        sizeOfCode = dis.readDoubleWord();
        sizeOfInitializedData = dis.readDoubleWord();
        sizeOfUninitializedData = dis.readDoubleWord();
        addressOfEntryPoint = dis.readDoubleWord();
        baseOfCode = dis.readDoubleWord();
        if (magic == MAGIC_PE32)
            baseOfData = dis.readDoubleWord();
    }

    void readWindowsSpecificFields(OldDataReader dis) throws IOException {
        if (magic == MAGIC_PE32)
            imageBase = dis.readDoubleWord();
        else
            imageBase = dis.readLong();
        sectionAlignment = dis.readDoubleWord();
        fileAlignment = dis.readDoubleWord();
        majorOperatingSystem = dis.readWord();
        minorOperatingSystem = dis.readWord();
        majorImageVersion = dis.readWord();
        minorImageVersion = dis.readWord();
        majorSubsystemVersion = dis.readWord();
        minorSubsystemVersion = dis.readWord();
        win32VersionValue = dis.readDoubleWord();
        sizeOfImage = dis.readDoubleWord();
        sizeOfHeaders = dis.readDoubleWord();
        checkSum = dis.readDoubleWord();
        subsystem = dis.readWord();
        dllCharacteristics = dis.readWord();
        if (magic == MAGIC_PE32) {
            sizeOfStackReserve = dis.readDoubleWord();
            sizeOfStackCommit = dis.readDoubleWord();
            sizeOfHeapReserve = dis.readDoubleWord();
            sizeOfHeapCommit = dis.readDoubleWord();
        } else {
            sizeOfStackReserve = dis.readLong();
            sizeOfStackCommit = dis.readLong();
            sizeOfHeapReserve = dis.readLong();
            sizeOfHeapCommit = dis.readLong();
        }
        loaderFlags = dis.readDoubleWord();
        numberOfRvaAndSizes = dis.readDoubleWord();
        containsWindowsSpecificFields = true;
    }
}
