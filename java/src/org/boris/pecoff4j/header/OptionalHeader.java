package org.boris.pecoff4j.header;

import java.io.IOException;

import org.boris.pecoff4j.ImageDirectoryEntry;
import org.boris.pecoff4j.io.DataReader;
import org.boris.pecoff4j.io.Reflection;

public class OptionalHeader {
    private int magic;
    private int majorLinkerVersion;
    private int minorLinkerVersion;
    private int sizeOfCode;
    private int sizeOfInitializedData;
    private int sizeOfUninitializedData;
    private int addressOfEntryPoint;
    private int baseOfCode;
    private int baseOfData;
    private int imageBase;
    private int sectionAlignment;
    private int fileAlignment;
    private int majorOperatingSystemVersion;
    private int minorOperatingSystemVersion;
    private int majorImageVersion;
    private int minorImageVersion;
    private int majorSubsystemVersion;
    private int minorSubsystemVersion;
    private int win32VersionValue;
    private int sizeOfImage;
    private int sizeOfHeaders;
    private int checkSum;
    private int subsystem;
    private int dllCharacteristics;
    private int sizeOfStackReserve;
    private int sizeOfStackCommit;
    private int sizeOfHeapReserve;
    private int sizeOfHeapCommit;
    private int loaderFlags;
    private int numberOfRvaAndSizes;

    // Data directories
    private ImageDirectoryEntry exportTable;
    private ImageDirectoryEntry importTable;
    private ImageDirectoryEntry resourceTable;
    private ImageDirectoryEntry exceptionTable;
    private ImageDirectoryEntry certificateTable;
    private ImageDirectoryEntry baseRolocationTable;
    private ImageDirectoryEntry debug;
    private ImageDirectoryEntry architecture;
    private ImageDirectoryEntry globalPtr;
    private ImageDirectoryEntry tlsTable;
    private ImageDirectoryEntry loadConfigTable;
    private ImageDirectoryEntry boundImport;
    private ImageDirectoryEntry iat;
    private ImageDirectoryEntry delayImportDescriptor;
    private ImageDirectoryEntry clrRuntimeHeader;
    private ImageDirectoryEntry reserved;

    public static OptionalHeader read(DataReader dr) throws IOException {
        OptionalHeader oh = new OptionalHeader();
        oh.readFrom(dr);
        return oh;
    }

    private void readFrom(DataReader dr) throws IOException {
        magic = dr.readWord();
        majorLinkerVersion = dr.readByte();
        minorLinkerVersion = dr.readByte();
        sizeOfCode = dr.readDoubleWord();
        sizeOfInitializedData = dr.readDoubleWord();
        sizeOfUninitializedData = dr.readDoubleWord();
        addressOfEntryPoint = dr.readDoubleWord();
        baseOfCode = dr.readDoubleWord();
        baseOfData = dr.readDoubleWord();
        // NT additional fields.
        imageBase = dr.readDoubleWord();
        sectionAlignment = dr.readDoubleWord();
        fileAlignment = dr.readDoubleWord();
        majorOperatingSystemVersion = dr.readWord();
        minorOperatingSystemVersion = dr.readWord();
        majorImageVersion = dr.readWord();
        minorImageVersion = dr.readWord();
        majorSubsystemVersion = dr.readWord();
        minorSubsystemVersion = dr.readWord();
        win32VersionValue = dr.readWord();
        sizeOfImage = dr.readDoubleWord();
        sizeOfHeaders = dr.readDoubleWord();
        checkSum = dr.readDoubleWord();
        subsystem = dr.readWord();
        dllCharacteristics = dr.readWord();
        sizeOfStackReserve = dr.readDoubleWord();
        sizeOfStackCommit = dr.readDoubleWord();
        sizeOfHeapReserve = dr.readDoubleWord();
        sizeOfHeapCommit = dr.readDoubleWord();
        loaderFlags = dr.readDoubleWord();
        numberOfRvaAndSizes = dr.readDoubleWord();
        // Data directories
        exportTable = ImageDirectoryEntry.read(dr);
        importTable = ImageDirectoryEntry.read(dr);
        resourceTable = ImageDirectoryEntry.read(dr);
        exceptionTable = ImageDirectoryEntry.read(dr);
        certificateTable = ImageDirectoryEntry.read(dr);
        baseRolocationTable = ImageDirectoryEntry.read(dr);
        debug = ImageDirectoryEntry.read(dr);
        architecture = ImageDirectoryEntry.read(dr);
        globalPtr = ImageDirectoryEntry.read(dr);
        tlsTable = ImageDirectoryEntry.read(dr);
        loadConfigTable = ImageDirectoryEntry.read(dr);
        boundImport = ImageDirectoryEntry.read(dr);
        iat = ImageDirectoryEntry.read(dr);
        delayImportDescriptor = ImageDirectoryEntry.read(dr);
        clrRuntimeHeader = ImageDirectoryEntry.read(dr);
        reserved = ImageDirectoryEntry.read(dr);
    }

    public String toString() {
        return Reflection.toString(this);
    }

    public int getMagic() {
        return magic;
    }

    public int getMajorLinkerVersion() {
        return majorLinkerVersion;
    }

    public int getMinorLinkerVersion() {
        return minorLinkerVersion;
    }

    public int getSizeOfCode() {
        return sizeOfCode;
    }

    public int getSizeOfInitializedData() {
        return sizeOfInitializedData;
    }

    public int getSizeOfUninitializedData() {
        return sizeOfUninitializedData;
    }

    public int getAddressOfEntryPoint() {
        return addressOfEntryPoint;
    }

    public int getBaseOfCode() {
        return baseOfCode;
    }

    public int getBaseOfData() {
        return baseOfData;
    }

    public int getImageBase() {
        return imageBase;
    }

    public int getSectionAlignment() {
        return sectionAlignment;
    }

    public int getFileAlignment() {
        return fileAlignment;
    }

    public int getMajorOperatingSystemVersion() {
        return majorOperatingSystemVersion;
    }

    public int getMinorOperatingSystemVersion() {
        return minorOperatingSystemVersion;
    }

    public int getMajorImageVersion() {
        return majorImageVersion;
    }

    public int getMinorImageVersion() {
        return minorImageVersion;
    }

    public int getMajorSubsystemVersion() {
        return majorSubsystemVersion;
    }

    public int getMinorSubsystemVersion() {
        return minorSubsystemVersion;
    }

    public int getWin32VersionValue() {
        return win32VersionValue;
    }

    public int getSizeOfImage() {
        return sizeOfImage;
    }

    public int getSizeOfHeaders() {
        return sizeOfHeaders;
    }

    public int getCheckSum() {
        return checkSum;
    }

    public int getSubsystem() {
        return subsystem;
    }

    public int getDllCharacteristics() {
        return dllCharacteristics;
    }

    public int getSizeOfStackReserve() {
        return sizeOfStackReserve;
    }

    public int getSizeOfStackCommit() {
        return sizeOfStackCommit;
    }

    public int getSizeOfHeapReserve() {
        return sizeOfHeapReserve;
    }

    public int getSizeOfHeapCommit() {
        return sizeOfHeapCommit;
    }

    public int getLoaderFlags() {
        return loaderFlags;
    }

    public int getNumberOfRvaAndSizes() {
        return numberOfRvaAndSizes;
    }

    public ImageDirectoryEntry getExportTable() {
        return exportTable;
    }

    public ImageDirectoryEntry getImportTable() {
        return importTable;
    }

    public ImageDirectoryEntry getResourceTable() {
        return resourceTable;
    }

    public ImageDirectoryEntry getExceptionTable() {
        return exceptionTable;
    }

    public ImageDirectoryEntry getCertificateTable() {
        return certificateTable;
    }

    public ImageDirectoryEntry getBaseRolocationTable() {
        return baseRolocationTable;
    }

    public ImageDirectoryEntry getDebug() {
        return debug;
    }

    public ImageDirectoryEntry getArchitecture() {
        return architecture;
    }

    public ImageDirectoryEntry getGlobalPtr() {
        return globalPtr;
    }

    public ImageDirectoryEntry getTlsTable() {
        return tlsTable;
    }

    public ImageDirectoryEntry getLoadConfigTable() {
        return loadConfigTable;
    }

    public ImageDirectoryEntry getBoundImport() {
        return boundImport;
    }

    public ImageDirectoryEntry getIat() {
        return iat;
    }

    public ImageDirectoryEntry getDelayImportDescriptor() {
        return delayImportDescriptor;
    }

    public ImageDirectoryEntry getClrRuntimeHeader() {
        return clrRuntimeHeader;
    }

    public ImageDirectoryEntry getReserved() {
        return reserved;
    }
}
