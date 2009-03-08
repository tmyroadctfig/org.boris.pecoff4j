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

    public void setMagic(int magic) {
        this.magic = magic;
    }

    public void setMajorLinkerVersion(int majorLinkerVersion) {
        this.majorLinkerVersion = majorLinkerVersion;
    }

    public void setMinorLinkerVersion(int minorLinkerVersion) {
        this.minorLinkerVersion = minorLinkerVersion;
    }

    public void setSizeOfCode(int sizeOfCode) {
        this.sizeOfCode = sizeOfCode;
    }

    public void setSizeOfInitializedData(int sizeOfInitializedData) {
        this.sizeOfInitializedData = sizeOfInitializedData;
    }

    public void setSizeOfUninitializedData(int sizeOfUninitializedData) {
        this.sizeOfUninitializedData = sizeOfUninitializedData;
    }

    public void setAddressOfEntryPoint(int addressOfEntryPoint) {
        this.addressOfEntryPoint = addressOfEntryPoint;
    }

    public void setBaseOfCode(int baseOfCode) {
        this.baseOfCode = baseOfCode;
    }

    public void setBaseOfData(int baseOfData) {
        this.baseOfData = baseOfData;
    }

    public void setImageBase(int imageBase) {
        this.imageBase = imageBase;
    }

    public void setSectionAlignment(int sectionAlignment) {
        this.sectionAlignment = sectionAlignment;
    }

    public void setFileAlignment(int fileAlignment) {
        this.fileAlignment = fileAlignment;
    }

    public void setMajorOperatingSystemVersion(int majorOperatingSystemVersion) {
        this.majorOperatingSystemVersion = majorOperatingSystemVersion;
    }

    public void setMinorOperatingSystemVersion(int minorOperatingSystemVersion) {
        this.minorOperatingSystemVersion = minorOperatingSystemVersion;
    }

    public void setMajorImageVersion(int majorImageVersion) {
        this.majorImageVersion = majorImageVersion;
    }

    public void setMinorImageVersion(int minorImageVersion) {
        this.minorImageVersion = minorImageVersion;
    }

    public void setMajorSubsystemVersion(int majorSubsystemVersion) {
        this.majorSubsystemVersion = majorSubsystemVersion;
    }

    public void setMinorSubsystemVersion(int minorSubsystemVersion) {
        this.minorSubsystemVersion = minorSubsystemVersion;
    }

    public void setWin32VersionValue(int win32VersionValue) {
        this.win32VersionValue = win32VersionValue;
    }

    public void setSizeOfImage(int sizeOfImage) {
        this.sizeOfImage = sizeOfImage;
    }

    public void setSizeOfHeaders(int sizeOfHeaders) {
        this.sizeOfHeaders = sizeOfHeaders;
    }

    public void setCheckSum(int checkSum) {
        this.checkSum = checkSum;
    }

    public void setSubsystem(int subsystem) {
        this.subsystem = subsystem;
    }

    public void setDllCharacteristics(int dllCharacteristics) {
        this.dllCharacteristics = dllCharacteristics;
    }

    public void setSizeOfStackReserve(int sizeOfStackReserve) {
        this.sizeOfStackReserve = sizeOfStackReserve;
    }

    public void setSizeOfStackCommit(int sizeOfStackCommit) {
        this.sizeOfStackCommit = sizeOfStackCommit;
    }

    public void setSizeOfHeapReserve(int sizeOfHeapReserve) {
        this.sizeOfHeapReserve = sizeOfHeapReserve;
    }

    public void setSizeOfHeapCommit(int sizeOfHeapCommit) {
        this.sizeOfHeapCommit = sizeOfHeapCommit;
    }

    public void setLoaderFlags(int loaderFlags) {
        this.loaderFlags = loaderFlags;
    }

    public void setNumberOfRvaAndSizes(int numberOfRvaAndSizes) {
        this.numberOfRvaAndSizes = numberOfRvaAndSizes;
    }

    public void setExportTable(ImageDataDirectory exportTable) {
        this.exportTable = exportTable;
    }

    public void setImportTable(ImageDataDirectory importTable) {
        this.importTable = importTable;
    }

    public void setResourceTable(ImageDataDirectory resourceTable) {
        this.resourceTable = resourceTable;
    }

    public void setExceptionTable(ImageDataDirectory exceptionTable) {
        this.exceptionTable = exceptionTable;
    }

    public void setCertificateTable(ImageDataDirectory certificateTable) {
        this.certificateTable = certificateTable;
    }

    public void setBaseRolocationTable(ImageDataDirectory baseRolocationTable) {
        this.baseRolocationTable = baseRolocationTable;
    }

    public void setDebug(ImageDataDirectory debug) {
        this.debug = debug;
    }

    public void setArchitecture(ImageDataDirectory architecture) {
        this.architecture = architecture;
    }

    public void setGlobalPtr(ImageDataDirectory globalPtr) {
        this.globalPtr = globalPtr;
    }

    public void setTlsTable(ImageDataDirectory tlsTable) {
        this.tlsTable = tlsTable;
    }

    public void setLoadConfigTable(ImageDataDirectory loadConfigTable) {
        this.loadConfigTable = loadConfigTable;
    }

    public void setBoundImport(ImageDataDirectory boundImport) {
        this.boundImport = boundImport;
    }

    public void setIat(ImageDataDirectory iat) {
        this.iat = iat;
    }

    public void setDelayImportDescriptor(
            ImageDataDirectory delayImportDescriptor) {
        this.delayImportDescriptor = delayImportDescriptor;
    }

    public void setClrRuntimeHeader(ImageDataDirectory clrRuntimeHeader) {
        this.clrRuntimeHeader = clrRuntimeHeader;
    }

    public void setReserved(ImageDataDirectory reserved) {
        this.reserved = reserved;
    }
}
