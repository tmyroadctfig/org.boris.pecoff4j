package org.boris.pecoff4j;

import java.io.IOException;

import org.boris.pecoff4j.header.COFFHeader;
import org.boris.pecoff4j.header.DOSHeader;
import org.boris.pecoff4j.header.DOSStub;
import org.boris.pecoff4j.header.NTHeader;
import org.boris.pecoff4j.header.OptionalHeader;
import org.boris.pecoff4j.io.DataReader;

/**
 * Encapsulates a portable executable file.
 */
public class PE {
    private DOSHeader dosHeader;
    private DOSStub stub;
    private NTHeader ntHeader;
    private SectionTable sectionTable;

    public DOSHeader getDosHeader() {
        return dosHeader;
    }

    public DOSStub getStub() {
        return stub;
    }

    public NTHeader getNtHeader() {
        return ntHeader;
    }

    public SectionTable getSectionTable() {
        return sectionTable;
    }

    public void read(DataReader dr) throws IOException {
        dosHeader = DOSHeader.read(dr);
        stub = DOSStub.read(dosHeader, dr);
        ntHeader = new NTHeader();
        ntHeader.setSignature(dr.readDoubleWord());
        ntHeader.setFileHeader(COFFHeader.read(dr));
        ntHeader.setOptionalHeader(OptionalHeader.read(dr));
        sectionTable = SectionTable.read(ntHeader.getFileHeader()
                .getNumberOfSections(), dr);
    }
}
