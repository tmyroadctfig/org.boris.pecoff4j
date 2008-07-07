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

public class StringFileInfo
{
    private String key;

    public static StringFileInfo read(IDataReader dr, int length)
            throws IOException {
        StringFileInfo sfi = new StringFileInfo();

        return sfi;
    }

    public String toString() {
        return Reflection.toString(this);
    }
}
