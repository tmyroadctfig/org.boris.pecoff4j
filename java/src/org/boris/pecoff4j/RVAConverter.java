package org.boris.pecoff4j;

public class RVAConverter {
    private int[] virtualAddress;
    private int[] pointerToRawData;

    public RVAConverter(int[] virtualAddress, int[] pointerToRawData) {
        this.virtualAddress = virtualAddress;
        this.pointerToRawData = pointerToRawData;
    }

    public int convertVirtualAddressToRawDataPointer(int virtualAddress) {
        for (int i = 0; i < this.virtualAddress.length; i++) {
            if (virtualAddress < this.virtualAddress[i]) {
                if (i > 0) {
                    return this.pointerToRawData[i - 1] + virtualAddress
                            - this.virtualAddress[i - 1];
                } else {
                    return -1;
                }
            }
        }

        // Hit the last item
        return this.pointerToRawData[this.virtualAddress.length - 1] + virtualAddress
                - this.virtualAddress[this.virtualAddress.length - 1];

    }
}
