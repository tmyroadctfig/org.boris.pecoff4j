/*******************************************************************************
 * This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at 
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     Peter Smith
 *******************************************************************************/
package org.boris.pecoff4j.io;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class DataWriter implements IDataWriter
{
    private BufferedOutputStream out;

    public DataWriter(File output) throws FileNotFoundException {
        this(new FileOutputStream(output));
    }

    public DataWriter(OutputStream out) {
        this.out = new BufferedOutputStream(out);
    }

    public void writeByte(int b) throws IOException {
        out.write(b);
    }

    public void writeBytes(byte[] b) throws IOException {
        out.write(b);
    }

    public void writeDoubleWord(int dw) throws IOException {
        out.write(dw & 0xff);
        out.write(dw >> 8 & 0xff);
        out.write(dw >> 16 & 0xff);
        out.write(dw >> 24 & 0xff);
    }

    public void writeWord(int w) throws IOException {
        out.write(w & 0xff);
        out.write(w >> 8 & 0xff);
    }

    public void close() throws IOException {
        out.flush();
        out.close();
    }
}
