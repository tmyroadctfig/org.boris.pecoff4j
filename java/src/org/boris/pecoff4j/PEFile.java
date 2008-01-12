package org.boris.pecoff4j;

import java.io.IOException;

import org.boris.pecoff4j.io.DataReader;

/**
 * Encapsulates a portable executable file.
 */
public class PEFile {
    private DOSHeader dosHeader;
    private DOSStub stub;
    private PESignature signature;
    private COFFHeader coffHeader;
    private OptionalHeader optionalHeader;
    private SectionTable sectionTable;

    public void read(DataReader dr) throws IOException {
        dosHeader = DOSHeader.read(dr);
        stub = DOSStub.read(dr);
        signature = PESignature.read(dr);
        coffHeader = COFFHeader.read(dr);
        optionalHeader = OptionalHeader.read(dr);
        sectionTable = SectionTable.read(dr);
    }
}
