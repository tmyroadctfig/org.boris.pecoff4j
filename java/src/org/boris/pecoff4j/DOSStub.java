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

import org.boris.pecoff4j.io.DataWriter;
import org.boris.pecoff4j.io.IDataReader;
import org.boris.pecoff4j.util.Reflection;

/**
 * Used to store the stub program.
 */
public class DOSStub
{
    private byte[] stub;

    public static DOSStub read(DOSHeader header, IDataReader dr)
            throws IOException {
        DOSStub ds = new DOSStub();
        ds.readFrom(header, dr);
        return ds;
    }

    private void readFrom(DOSHeader header, IDataReader dr) throws IOException {
        int pos = dr.getPosition();
        int add = header.getAddressOfNewExeHeader();
        stub = new byte[add - pos];
        dr.read(stub);
    }

    public String toString() {
        return Reflection.toString(this);
    }

    public void write(DataWriter dw) {
    }
}
