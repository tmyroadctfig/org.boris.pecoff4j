package org.boris.pecoff4j;

import java.io.IOException;

import org.boris.pecoff4j.io.IDataReader;
import org.boris.pecoff4j.util.Reflection;

public class LoadConfigDirectory {
    private int characteristics;
    private int timeDateStamp;
    private int majorVersion;
    private int minorVersion;
    private int globalFlagsClear;
    private int globalFlagsSet;
    private int criticalSectionDefaultTimeout;
    private long deCommitFreeBlockThreshold;
    private long deCommitTotalFreeThreshold;
    private long lockPrefixTable;
    private long maximumAllocationSize;
    private long virtualMemoryThreshold;
    private long processAffinityMask;
    private int processHeapFlags;
    private int csdVersion;
    private int reserved;
    private long editList;
    private int securityCookie;
    private int seHandlerTable;
    private int seHandlerCount;

    public static LoadConfigDirectory read(IDataReader dr) throws IOException {
        LoadConfigDirectory lcd = new LoadConfigDirectory();
        lcd.characteristics = dr.readDoubleWord();
        lcd.timeDateStamp = dr.readDoubleWord();
        lcd.majorVersion = dr.readWord();
        lcd.minorVersion = dr.readWord();
        lcd.globalFlagsClear = dr.readDoubleWord();
        lcd.globalFlagsSet = dr.readDoubleWord();
        lcd.criticalSectionDefaultTimeout = dr.readDoubleWord();
        lcd.deCommitFreeBlockThreshold = dr.readLong();
        lcd.deCommitTotalFreeThreshold = dr.readLong();
        lcd.lockPrefixTable = dr.readLong();
        lcd.maximumAllocationSize = dr.readLong();
        lcd.virtualMemoryThreshold = dr.readLong();
        lcd.processAffinityMask = dr.readLong();
        lcd.processHeapFlags = dr.readDoubleWord();
        lcd.csdVersion = dr.readWord();
        lcd.reserved = dr.readWord();
        lcd.editList = dr.readLong();
        lcd.securityCookie = dr.readDoubleWord();
        lcd.seHandlerTable = dr.readDoubleWord();
        lcd.seHandlerCount = dr.readDoubleWord();

        return lcd;
    }

    public String toString() {
        return Reflection.toString(this);
    }

    public int getCharacteristics() {
        return characteristics;
    }

    public int getTimeDateStamp() {
        return timeDateStamp;
    }

    public int getMajorVersion() {
        return majorVersion;
    }

    public int getMinorVersion() {
        return minorVersion;
    }

    public int getGlobalFlagsClear() {
        return globalFlagsClear;
    }

    public int getGlobalFlagsSet() {
        return globalFlagsSet;
    }

    public int getCriticalSectionDefaultTimeout() {
        return criticalSectionDefaultTimeout;
    }

    public long getDeCommitFreeBlockThreshold() {
        return deCommitFreeBlockThreshold;
    }

    public long getDeCommitTotalFreeThreshold() {
        return deCommitTotalFreeThreshold;
    }

    public long getLockPrefixTable() {
        return lockPrefixTable;
    }

    public long getMaximumAllocationSize() {
        return maximumAllocationSize;
    }

    public long getVirtualMemoryThreshold() {
        return virtualMemoryThreshold;
    }

    public long getProcessAffinityMask() {
        return processAffinityMask;
    }

    public int getProcessHeapFlags() {
        return processHeapFlags;
    }

    public int getCsdVersion() {
        return csdVersion;
    }

    public int getReserved() {
        return reserved;
    }

    public long getEditList() {
        return editList;
    }

    public int getSecurityCookie() {
        return securityCookie;
    }

    public int getSeHandlerTable() {
        return seHandlerTable;
    }

    public int getSeHandlerCount() {
        return seHandlerCount;
    }
}
