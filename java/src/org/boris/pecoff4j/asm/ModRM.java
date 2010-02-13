/*******************************************************************************
 * This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at 
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     Peter Smith
 *******************************************************************************/
package org.boris.pecoff4j.asm;

public class ModRM
{
    public final int mod;
    public final int reg1;
    public final int reg2;

    public ModRM(int value) {
        this.mod = (value >> 6) & 0xf;
        this.reg2 = (value >> 3) & 0x7;
        this.reg1 = value & 0x7;
    }

    public byte encode() {
        return (byte) (mod << 6 | reg2 << 3 | reg1);
    }
}
