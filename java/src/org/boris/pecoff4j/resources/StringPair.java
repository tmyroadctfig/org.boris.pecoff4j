/*******************************************************************************
 * This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at 
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     Peter Smith
 *******************************************************************************/
package org.boris.pecoff4j.resources;

import java.io.IOException;

import org.boris.pecoff4j.io.IDataReader;
import org.boris.pecoff4j.util.Reflection;

public class StringPair
{
    private int length;
    private int valueLength;
    private int type;
    private String key;
    private String value;
    private int padding;

    public static StringPair read(IDataReader dr) throws IOException {
        StringPair sp = new StringPair();
        sp.length = dr.readWord();
        sp.valueLength = dr.readWord();
        sp.type = dr.readWord();
        sp.key = dr.readUnicode();
        if (sp.key.length() % 2 == 0) {
            dr.readWord();
            sp.padding = 2;
        }
        sp.value = dr.readUnicode();
        return sp;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getValueLength() {
        return valueLength;
    }

    public void setValueLength(int valueLength) {
        this.valueLength = valueLength;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String toString() {
        return Reflection.toString(this);
    }

    public int sizeOf() {
        return 6 + padding + key.length() * 2 + value.length() * 2;
    }
}
