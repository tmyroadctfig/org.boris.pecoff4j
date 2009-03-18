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

import org.boris.pecoff4j.PE;
import org.boris.pecoff4j.io.PEAssembler;
import org.boris.pecoff4j.io.PEParser;
import org.boris.pecoff4j.util.Diff;
import org.boris.pecoff4j.util.IO;

public class WinRun4JTest1
{
    public static void main(String[] args) throws Exception {
        PE pe = PEParser.parse(WinRun4JTest1.class
                .getResourceAsStream("WinRun4J.exe"));

        byte[] b1 = IO.toBytes(WinRun4JTest1.class
                .getResourceAsStream("WinRun4J.exe"));
        byte[] b2 = PEAssembler.toBytes(pe);
        Diff.findDiff(b1, b2);
    }
}
