/*******************************************************************************
 * This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at 
 * http://www.eclipse.org/legal/cpl-v10.html
 * <p/>
 * Contributors:
 *     Peter Smith
 *******************************************************************************/
package org.boris.pecoff4j.io;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class DataReader implements IDataReader {
  private InputStream dis;
  private int position = 0;

  public DataReader(byte[] buffer) {
    this(new BufferedInputStream(new ByteArrayInputStream(buffer)));
  }

  public DataReader(byte[] buffer, int offset, int length) {
    this(new BufferedInputStream(new ByteArrayInputStream(buffer,
            offset, length)));
  }

  public DataReader(InputStream is) {
    if (is instanceof BufferedInputStream) {
      this.dis = is;
    } else {
      this.dis = new BufferedInputStream(is);
    }

    try {
      dis.mark(dis.available());
    } catch (IOException e) {
      throw new IllegalStateException("Error setting mark on stream", e);
    }
  }

  public int readByte() throws IOException {
    position += 1;
    return dis.read();
  }

  public int readWord() throws IOException {
    position += 2;
    return dis.read() | dis.read() << 8;
  }

  public long readLong() throws IOException {
    return (readDoubleWord() & 0x00000000ffffffffl) |
            ((long) readDoubleWord() << 32l);
  }

  public int readDoubleWord() throws IOException {
    position += 4;
    return dis.read() | dis.read() << 8 | dis.read() << 16 |
            dis.read() << 24;
  }

  public int getPosition() {
    return position;
  }

  public int getLength() {
    try {
      return dis.available();
    } catch (IOException e) {
      throw new IllegalStateException("Error getting stream length", e);
    }
  }

  public void jumpTo(int location) throws IOException {
    if (location > position) {
      skipBytes(location - position);
    } else {
      dis.reset();
      position = 0;
      skipBytes(location);
    }
  }

  public void skipBytes(int numBytes) throws IOException {
    position += numBytes;

    while (numBytes > 0) {
      long bytesSkipped = dis.skip(numBytes);
      numBytes -= bytesSkipped;

      if (bytesSkipped == 0 && dis.available() == 0) {
        break;
      }
    }
  }

  public void close() throws IOException {
    dis.close();
  }

  public void read(byte[] b) throws IOException {
    position += b.length;
    dis.read(b);
  }

  public String readUtf(int size) throws IOException {
    position += size;
    byte b[] = new byte[size];
    dis.read(b);
    int i = 0;
    for (; i < b.length; i++) {
      if (b[i] == 0)
        break;
    }
    return new String(b, 0, i);
  }

  public String readUtf() throws IOException {
    StringBuilder sb = new StringBuilder();
    int c = 0;
    while ((c = readByte()) != 0) {
      if (c == -1)
        throw new IOException("Unexpected end of stream");
      sb.append((char) c);
    }
    return sb.toString();
  }

  public String readUnicode() throws IOException {
    StringBuilder sb = new StringBuilder();
    char c = 0;
    while ((c = (char) readWord()) != 0) {
      sb.append(c);
    }
    if (sb.length() == 0) {
      return null;
    }
    return sb.toString();
  }

  public String readUnicode(int maxLength) throws IOException {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < maxLength; i++) {
      char c = (char) readWord();

      if (c == 0) {
        break;
      }

      sb.append(c);
    }
    return sb.toString();
  }
}