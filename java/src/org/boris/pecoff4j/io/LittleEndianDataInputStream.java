package org.boris.pecoff4j.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * Little endian data input stream.
 *
 * @author $Author: poida_smith $
 * @version $Revision: 1.1 $
  */
public class LittleEndianDataInputStream extends InputStream {
    private InputStream is;

    /**
     * Creates a new LittleEndianDataInputStream object.
     *
     * @param is.
     */
    public LittleEndianDataInputStream(InputStream is) {
        this.is = is;
    }

    /* (non-Javadoc)
     * @see java.io.InputStream#read()
     */
    @Override
    public int read() throws IOException {
        return is.read();
    }

    /**
     * Read an integer.
     *
     * @return int.
     *
     * @throws IOException.
     */
    public int readInt() throws IOException {
        return is.read() | (is.read() << 8) | (is.read() << 16) | (is.read() << 24);
    }

    /**
     * Read a string.
     *
     * @param length.
     *
     * @return String.
     *
     * @throws IOException. 
     */
    public String readString(int length) throws IOException {
        return readString(new byte[length]);
    }

    /**
     * Read a string.
     *
     * @param buf.
     *
     * @return String.
     *
     * @throws IOException.
     */
    public String readString(byte[] buf) throws IOException {
        return readString(buf, 0, buf.length);
    }

    /**
     * Read a string.
     *
     * @param buf.
     * @param offset.
     * @param length.
     *
     * @return String.
     *
     * @throws IOException.
     */
    public String readString(byte[] buf, int offset, int length)
        throws IOException {
        is.read(buf, offset, length);

        return new String(buf, offset, length);
    }
}
