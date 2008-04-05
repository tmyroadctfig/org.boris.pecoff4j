package org.boris.pecoff4j;
import java.io.IOException;

import org.boris.pecoff4j.io.DataWriter;
import org.boris.pecoff4j.io.IDataReader;
import org.boris.pecoff4j.util.Reflection;

public class DOSHeader {
    private int magic;
    private int usedBytesInLastPage;
    private int fileSizeInPages;
    private int numRelocationItems;
    private int headerSizeInParagraphs;
    private int minExtraParagraphs;
    private int maxExtraParagraphs;
    private int initialSS;
    private int initialSP;
    private int checksum;
    private int initialIP;
    private int initialRelativeCS;
    private int addressOfRelocationTable;
    private int overlayNumber;
    private int[] reserverd;
    private int[] reserved2;
    private int oemId;
    private int oemInfo;
    private int addressOfNewExeHeader;
    private int stubSize;

    public static DOSHeader read(IDataReader dr) throws IOException {
        DOSHeader dh = new DOSHeader();
        dh.readFrom(dr);
        return dh;
    }

    private void readFrom(IDataReader dis) throws IOException {
        magic = dis.readWord(); // Magic number
        usedBytesInLastPage = dis.readWord(); // Bytes on last page of file
        fileSizeInPages = dis.readWord(); // Pages in file
        numRelocationItems = dis.readWord(); // Relocations
        headerSizeInParagraphs = dis.readWord(); // Size of header in paragraphs
        minExtraParagraphs = dis.readWord(); // Minimum extra paragraphs needed
        maxExtraParagraphs = dis.readWord(); // Maximum extra paragraphs needed
        initialSS = dis.readWord(); // Initial (relative) SS value
        initialSP = dis.readWord(); // Initial SP value
        checksum = dis.readWord(); // Checksum
        initialIP = dis.readWord(); // Initial IP value
        initialRelativeCS = dis.readWord(); // Initial (relative) CS value
        addressOfRelocationTable = dis.readWord(); // File address of relocation table
        overlayNumber = dis.readWord(); // Overlay number
        reserverd = new int[4];
        for (int i = 0; i < reserverd.length; i++) {
            reserverd[i] = dis.readWord(); // Reserved words
        }
        oemId = dis.readWord(); // OEM identifier (for e_oeminfo)
        oemInfo = dis.readWord(); // OEM information; e_oemid specific
        reserved2 = new int[10];
        for (int i = 0; i < reserved2.length; i++) {
            reserved2[i] = dis.readWord(); // Reserved words
        }
        addressOfNewExeHeader = dis.readDoubleWord(); // File address of new exe header

        // calc stub size
        stubSize = fileSizeInPages * 512 - (512 - usedBytesInLastPage);
        if (stubSize > addressOfNewExeHeader)
            stubSize = addressOfNewExeHeader;
        stubSize -= headerSizeInParagraphs * 16;
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

    public int getNumRelocationItems() {
        return numRelocationItems;
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

    public int getInitialSS() {
        return initialSS;
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

    public int getOemId() {
        return oemId;
    }

    public int getOemInfo() {
        return oemInfo;
    }

    public int getAddressOfNewExeHeader() {
        return addressOfNewExeHeader;
    }

    public int[] getReserverd() {
        return reserverd;
    }

    public int[] getReserved2() {
        return reserved2;
    }

    public int getStubSize() {
        return stubSize;
    }

    public void write(DataWriter dw) {
    }
}
