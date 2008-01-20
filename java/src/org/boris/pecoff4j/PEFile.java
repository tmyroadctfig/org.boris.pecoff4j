package org.boris.pecoff4j;

import java.io.IOException;

import org.boris.pecoff4j.io.DataReader;

/**
 * Encapsulates a portable executable file.
 */
public class PEFile {
    private DOSHeader dosHeader;
    private DOSStub stub;
    private NTHeader ntHeader;
    private SectionTable sectionTable;

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
