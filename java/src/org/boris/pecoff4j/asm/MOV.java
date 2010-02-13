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

public class MOV extends AbstractInstruction
{
    private ModRM modrm;
    private int disp32;
    private int imm32;

    public MOV(ModRM modrm) {
        this.modrm = modrm;
        this.code = toCode(0x8b, modrm);
    }

    public MOV(ModRM modrm, byte imm8) {
        this.modrm = modrm;
        this.imm32 = imm8;
        this.code = toCode(0x8b, modrm, imm8);
    }

    public MOV(ModRM modrm, int disp32, int imm32) {
        this.modrm = modrm;
        this.disp32 = disp32;
        this.imm32 = imm32;
        this.code = toCode(0xc7, modrm, disp32, imm32);
    }

    public String toIntelAssembly() {
        switch (((int) code[0]) & 0xff) {
        case 0x8b:
            switch (modrm.mod) {
            case 0:
                return "mov  " + Register.to32(modrm.reg2) + ", " + Register.to32(modrm.reg1);
            case 1:
                return "mov  " + Register.to32(modrm.reg2) + ", [" + Register.to32(modrm.reg1)
                        + toHexString((byte) imm32, true) + "]";
            }
        case 0xc7:
            return "mov  dword ptr [" + Register.to32(modrm.reg1) + toHexString(disp32, true) + "], "
                    + toHexString(imm32, false);
        }

        return "MOV: UNKNOWN";
    }
}
