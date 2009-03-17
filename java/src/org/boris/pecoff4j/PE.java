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

public class PE
{
    private DOSHeader dosHeader;
    private DOSStub stub;
    private PESignature signature;
    private COFFHeader coffHeader;
    private OptionalHeader optionalHeader;
    private BoundImportDirectoryTable boundImports;
    private SectionTable sectionTable;

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

    public BoundImportDirectoryTable getBoundImports() {
        return boundImports;
    }

    public SectionTable getSectionTable() {
        return sectionTable;
    }

    public void setDosHeader(DOSHeader dosHeader) {
        this.dosHeader = dosHeader;
    }

    public void setStub(DOSStub stub) {
        this.stub = stub;
    }

    public void setSignature(PESignature signature) {
        this.signature = signature;
    }

    public void setCoffHeader(COFFHeader coffHeader) {
        this.coffHeader = coffHeader;
    }

    public void setOptionalHeader(OptionalHeader optionalHeader) {
        this.optionalHeader = optionalHeader;
    }

    public void setBoundImports(BoundImportDirectoryTable boundImports) {
        this.boundImports = boundImports;
    }

    public void setSectionTable(SectionTable sectionTable) {
        this.sectionTable = sectionTable;
    }
}
