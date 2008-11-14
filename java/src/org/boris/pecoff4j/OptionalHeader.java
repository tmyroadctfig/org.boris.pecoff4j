/*******************************************************************************
 * This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at 
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     Peter Smith
 *******************************************************************************/
package org.boris.pecoff4j;

import java.io.IOException;

import org.boris.pecoff4j.io.IDataReader;
import org.boris.pecoff4j.io.IDataWriter;
import org.boris.pecoff4j.util.Reflection;

public class OptionalHeader
{
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
    private ImageDataDirectory exportTable;
    private ImageDataDirectory importTable;
    private ImageDataDirectory resourceTable;
    private ImageDataDirectory exceptionTable;
    private ImageDataDirectory certificateTable;
    private ImageDataDirectory baseRolocationTable;
    private ImageDataDirectory debug;
    private ImageDataDirectory architecture;
    private ImageDataDirectory globalPtr;
    private ImageDataDirectory tlsTable;
    private ImageDataDirectory loadConfigTable;
    private ImageDataDirectory boundImport;
    private ImageDataDirectory iat;
    private ImageDataDirectory delayImportDescriptor;
    private ImageDataDirectory clrRuntimeHeader;
    private ImageDataDirectory reserved;

    public static OptionalHeader read(IDataReader dr) throws IOException {
        OptionalHeader oh = new OptionalHeader();
        oh.readFrom(dr);
        return oh;
    }

    private void readFrom(IDataReader dr) throws IOException {
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
        win32VersionValue = dr.readDoubleWord();
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
        exportTable = ImageDataDirectory.read(dr);
        importTable = ImageDataDirectory.read(dr);
        resourceTable = ImageDataDirectory.read(dr);
        exceptionTable = ImageDataDirectory.read(dr);
        certificateTable = ImageDataDirectory.read(dr);
        baseRolocationTable = ImageDataDirectory.read(dr);
        debug = ImageDataDirectory.read(dr);
        architecture = ImageDataDirectory.read(dr);
        globalPtr = ImageDataDirectory.read(dr);
        tlsTable = ImageDataDirectory.read(dr);
        loadConfigTable = ImageDataDirectory.read(dr);
        boundImport = ImageDataDirectory.read(dr);
        iat = ImageDataDirectory.read(dr);
        delayImportDescriptor = ImageDataDirectory.read(dr);
        clrRuntimeHeader = ImageDataDirectory.read(dr);
        reserved = ImageDataDirectory.read(dr);
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

    public ImageDataDirectory getExportTable() {
        return exportTable;
    }

    public ImageDataDirectory getImportTable() {
        return importTable;
    }

    public ImageDataDirectory getResourceTable() {
        return resourceTable;
    }

    public ImageDataDirectory getExceptionTable() {
        return exceptionTable;
    }

    public ImageDataDirectory getCertificateTable() {
        return certificateTable;
    }

    public ImageDataDirectory getBaseRolocationTable() {
        return baseRolocationTable;
    }

    public ImageDataDirectory getDebug() {
        return debug;
    }

    public ImageDataDirectory getArchitecture() {
        return architecture;
    }

    public ImageDataDirectory getGlobalPtr() {
        return globalPtr;
    }

    public ImageDataDirectory getTlsTable() {
        return tlsTable;
    }

    public ImageDataDirectory getLoadConfigTable() {
        return loadConfigTable;
    }

    public ImageDataDirectory getBoundImport() {
        return boundImport;
    }

    public ImageDataDirectory getIat() {
        return iat;
    }

    public ImageDataDirectory getDelayImportDescriptor() {
        return delayImportDescriptor;
    }

    public ImageDataDirectory getClrRuntimeHeader() {
        return clrRuntimeHeader;
    }

    public ImageDataDirectory getReserved() {
        return reserved;
    }

    public void write(IDataWriter dw) {
    }
}
