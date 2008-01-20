package org.boris.pecoff4j;

public class NTHeader {
    private long signature;
    private COFFHeader fileHeader;
    private OptionalHeader optionalHeader;

    public long getSignature() {
        return signature;
    }

    public void setSignature(long signature) {
        this.signature = signature;
    }

    public COFFHeader getFileHeader() {
        return fileHeader;
    }

    public void setFileHeader(COFFHeader fileHeader) {
        this.fileHeader = fileHeader;
    }

    public OptionalHeader getOptionalHeader() {
        return optionalHeader;
    }

    public void setOptionalHeader(OptionalHeader optionalHeader) {
        this.optionalHeader = optionalHeader;
    }
}
