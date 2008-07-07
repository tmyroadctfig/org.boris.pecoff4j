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

import org.boris.pecoff4j.io.DataReader;
import org.boris.pecoff4j.util.Reflection;

/**
 * The export directory table. See section 6.3.1 of the PE/COFF specification
 * v8.
 */
public class ExportDirectoryTable
{
    private long exportFlags;
    private long timeDateStamp;
    private int majorVersion;
    private int minorVersion;
    private long nameRVA;
    private long ordinalBase;
    private long addressTableEntries;
    private long numberOfNamePointers;
    private long exportAddressTableRVA;
    private long namePointerRVA;
    private long ordinalTableRVA;

    public static ExportDirectoryTable read(DataReader dis) throws IOException {
        ExportDirectoryTable edt = new ExportDirectoryTable();
        edt.exportFlags = dis.readDoubleWord();
        edt.timeDateStamp = dis.readDoubleWord();
        edt.majorVersion = dis.readWord();
        edt.minorVersion = dis.readWord();
        edt.nameRVA = dis.readDoubleWord();
        edt.ordinalBase = dis.readDoubleWord();
        edt.addressTableEntries = dis.readDoubleWord();
        edt.numberOfNamePointers = dis.readDoubleWord();
        edt.exportAddressTableRVA = dis.readDoubleWord();
        edt.namePointerRVA = dis.readDoubleWord();
        edt.ordinalTableRVA = dis.readDoubleWord();
        return edt;
    }

    public String toString() {
        return Reflection.toString(this);
    }

    public long getExportFlags() {
        return exportFlags;
    }

    public long getTimeDateStamp() {
        return timeDateStamp;
    }

    public int getMajorVersion() {
        return majorVersion;
    }

    public int getMinorVersion() {
        return minorVersion;
    }

    public long getNameRVA() {
        return nameRVA;
    }

    public long getOrdinalBase() {
        return ordinalBase;
    }

    public long getAddressTableEntries() {
        return addressTableEntries;
    }

    public long getNumberOfNamePointers() {
        return numberOfNamePointers;
    }

    public long getExportAddressTableRVA() {
        return exportAddressTableRVA;
    }

    public long getNamePointerRVA() {
        return namePointerRVA;
    }

    public long getOrdinalTableRVA() {
        return ordinalTableRVA;
    }
}
