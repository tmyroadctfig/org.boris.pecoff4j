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

public class LEA extends AbstractInstruction
{
    private ModRM modrm;
    private int imm32;

    public LEA(ModRM modrm, int imm32) {
        this.modrm = modrm;
        this.imm32 = imm32;
        this.code = toCode(0x8d, modrm, imm32);
    }

    public String toIntelAssembly() {
        return "lea  " + Register.to32(modrm.reg2) + ", [" + Register.to32(modrm.reg1) + toHexString(imm32, true) + "]";
    }
}