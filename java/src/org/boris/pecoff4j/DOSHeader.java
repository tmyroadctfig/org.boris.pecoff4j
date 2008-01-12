package org.boris.pecoff4j;

import java.io.IOException;

import org.boris.pecoff4j.io.DataReader;
import org.boris.pecoff4j.io.Reflection;

/**
 * The DOS header.
 */
public class DOSHeader {
    private int magic;
    private int usedBytesInLastPage;
    private int fileSizeInPages;
    private int numberOfRelocationItems;
    private int headerSizeInParagraphs;
    private int minExtraParagraphs;
    private int maxExtraParagraphs;
    private int initialRelativeSS;
    private int initialSP;
    private int checksum;
    private int initialIP;
    private int initialRelativeCS;
    private int addressOfRelocationTable;
    private int overlayNumber;
    private int reserved[] = new int[4];
    private int oemId;
    private int oemInfo;
    private int reserved2[] = new int[10];
    private long addressOfNewExeHeader;

    public static DOSHeader read(DataReader dr) throws IOException {
        DOSHeader dh = new DOSHeader();
        dh.readFrom(dr);
        return dh;
    }

    void readFrom(DataReader dr) throws IOException {
        magic = dr.readWord();
        usedBytesInLastPage = dr.readWord();
        fileSizeInPages = dr.readWord();
        numberOfRelocationItems = dr.readWord();
        headerSizeInParagraphs = dr.readWord();
        minExtraParagraphs = dr.readWord();
        maxExtraParagraphs = dr.readWord();
        initialRelativeSS = dr.readWord();
        initialSP = dr.readWord();
        checksum = dr.readWord();
        initialIP = dr.readWord();
        initialRelativeCS = dr.readWord();
        addressOfRelocationTable = dr.readWord();
        overlayNumber = dr.readWord();
        for (int i = 0; i < 4; i++)
            reserved[i] = dr.readWord();
        oemId = dr.readWord();
        oemInfo = dr.readWord();
        for (int i = 0; i < 10; i++)
            reserved2[i] = dr.readWord();
        addressOfNewExeHeader = dr.readDoubleWord();
    }

    public String toString() {
        return Reflection.toString(this);
    }

    public int getMagic() {
        return magic;
    }

    public int getUsedBytesInLastPage() {
        return usedBytesInLastPage;
    }

    public int getFileSizeInPages() {
        return fileSizeInPages;
    }

    public int getNumberOfRelocationItems() {
        return numberOfRelocationItems;
    }

    public int getHeaderSizeInParagraphs() {
        return headerSizeInParagraphs;
    }

    public int getMinExtraParagraphs() {
        return minExtraParagraphs;
    }

    public int getMaxExtraParagraphs() {
        return maxExtraParagraphs;
    }

    public int getInitialRelativeSS() {
        return initialRelativeSS;
    }

    public int getInitialSP() {
        return initialSP;
    }

    public int getChecksum() {
        return checksum;
    }

    public int getInitialIP() {
        return initialIP;
    }

    public int getInitialRelativeCS() {
        return initialRelativeCS;
    }

    public int getAddressOfRelocationTable() {
        return addressOfRelocationTable;
    }

    public int getOverlayNumber() {
        return overlayNumber;
    }

    public int[] getReserved() {
        return reserved;
    }

    public int getOemId() {
        return oemId;
    }

    public int getOemInfo() {
        return oemInfo;
    }

    public int[] getReserved2() {
        return reserved2;
    }

    public long getAddressOfNewExeHeader() {
        return addressOfNewExeHeader;
    }
}
