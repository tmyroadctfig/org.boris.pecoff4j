/*******************************************************************************
 * This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at 
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     Peter Smith
 *******************************************************************************/
package org.boris.pecoff4j.buftest;

import java.io.File;

import org.boris.pecoff4j.Executable;
import org.boris.pecoff4j.util.HexDump;
import org.boris.pecoff4j.util.PEParser;
import org.boris.pecoff4j.util.ResourceList;

public class Test1
{
    private static String E1 = "F:\\Development\\test\\eclipse.exe";

    public static void main(String[] args) throws Exception {
        Executable pe = PEParser.parse(new File(E1));
        ResourceList rl = ResourceList.extract(pe);
        for (int i = 0; i < rl.size(); i++) {
            System.out.println(rl.getType(i));
            System.out.println(rl.getName(i));
            System.out.println(rl.getLanguageId(i));
            HexDump.dump(rl.getData(i), 0, 10);
        }
    }
}
