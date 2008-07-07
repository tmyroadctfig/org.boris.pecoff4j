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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.boris.pecoff4j.imports.ImportDirectory;
import org.boris.pecoff4j.io.ByteArrayDataReader;
import org.boris.pecoff4j.io.DataReader;
import org.boris.pecoff4j.io.DataWriter;
import org.boris.pecoff4j.io.IDataReader;
import org.boris.pecoff4j.util.Reflection;

public class PEFile
{
    private DOSHeader dosHeader;
    private DOSStub stub;
    private PESignature signature;
    private COFFHeader coffHeader;
    private OptionalHeader optionalHeader;
    private SectionTable sectionTable;
    private ResourceDirectory resourceDirectory;
    private ImportDirectory importDirectory;
    private LoadConfigDirectory loadConfigDirectory;

    public static PEFile parse(File file) throws IOException {
        return read(new DataReader(new FileInputStream(file)));
    }

    public static PEFile read(IDataReader dr) throws IOException {
        PEFile pf = new PEFile();
        pf.dosHeader = DOSHeader.read(dr);
        pf.stub = DOSStub.read(pf.dosHeader, dr);
        pf.signature = PESignature.read(dr);
        pf.coffHeader = COFFHeader.read(dr);
        pf.optionalHeader = OptionalHeader.read(dr);
        pf.sectionTable = SectionTable.read(
                pf.coffHeader.getNumberOfSections(), dr);

        // Parse resources if present
        // TODO: ensure the resource table covers the .rsrc section
        byte[] res = pf.getSectionTable().getSectionData(".rsrc");
        if (res != null &&
                pf.getOptionalHeader().getResourceTable().getSize() > 0) {
            pf.resourceDirectory = ResourceDirectory
                    .parse(res, pf.getOptionalHeader().getResourceTable()
                            .getVirtualAddress());
        }

        // Parse import directory table
        res = pf.getSectionTable().getSectionData(".rdata");
        if (res != null) {
            IDataReader drr = new ByteArrayDataReader(res);
            int itva = pf.getOptionalHeader().getImportTable()
                    .getVirtualAddress();
            int rdva = pf.getSectionTable().getSection(".rdata")
                    .getVirtualAddress();
            int offset = itva - rdva;
            // Sanity check
            if (offset > 0 && offset < res.length) {
                drr.jumpTo(itva - rdva);
                pf.importDirectory = ImportDirectory.read(drr, pf
                        .getSectionTable().getSection(".rdata")
                        .getVirtualAddress());
            }

            if (pf.getOptionalHeader().getLoadConfigTable().getSize() > 0) {
                drr.jumpTo(pf.getOptionalHeader().getLoadConfigTable()
                        .getVirtualAddress() -
                        pf.getSectionTable().getSection(".rdata")
                                .getVirtualAddress());
                pf.loadConfigDirectory = LoadConfigDirectory.read(drr);
            }
        }

        return pf;
    }

    public DOSHeader getDosHeader() {
        return dosHeader;
    }

    public DOSStub getStub() {
        return stub;
    }

    public PESignature getSignature() {
        return signature;
    }

    public COFFHeader getCoffHeader() {
        return coffHeader;
    }

    public OptionalHeader getOptionalHeader() {
        return optionalHeader;
    }

    public SectionTable getSectionTable() {
        return sectionTable;
    }

    public ResourceDirectory getResourceDirectory() {
        return this.resourceDirectory;
    }

    public ImportDirectory getImportDirectory() {
        return this.importDirectory;
    }

    public LoadConfigDirectory getLoadConfigDirectory() {
        return this.loadConfigDirectory;
    }

    public String toString() {
        return Reflection.toString(this);
    }

    public void write(DataWriter dw) {
        dosHeader.write(dw);
        stub.write(dw);
        coffHeader.write(dw);
        optionalHeader.write(dw);
        sectionTable.write(dw);
    }
}
