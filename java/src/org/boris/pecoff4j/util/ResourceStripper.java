/*******************************************************************************
 * This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at 
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     Peter Smith
 *******************************************************************************/
package org.boris.pecoff4j.util;

import java.io.File;

import org.boris.pecoff4j.PEFile;
import org.boris.pecoff4j.io.DataWriter;

public class ResourceStripper
{
    public static void remove(File pecoff, File output) throws Exception {
        PEFile pe = PEFile.parse(pecoff);
        DataWriter dw = new DataWriter(output);
        pe.write(dw);
        dw.close();
    }
}
