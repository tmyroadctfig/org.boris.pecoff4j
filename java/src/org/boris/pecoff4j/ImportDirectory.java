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

import org.boris.pecoff4j.util.DataObject;

import java.util.ArrayList;
import java.util.List;

public class ImportDirectory extends DataObject {
  private List<ImportDirectoryEntry> entries = new ArrayList<ImportDirectoryEntry>();
  private List<String> names = new ArrayList<String>();
  private List<ImportDirectoryTable> nameTables = new ArrayList<ImportDirectoryTable>();
  private List<ImportDirectoryTable> addressTables = new ArrayList<ImportDirectoryTable>();

  public void add(ImportDirectoryEntry entry) {
    entries.add(entry);

    if (size() > 0x10000) {
      // What is this? A Lotus Notes exe?
      throw new IllegalStateException("Too many imports, are you sure the executable is valid?");
    }
  }

  public void add(String name, ImportDirectoryTable names,
                  ImportDirectoryTable addresses) {
    this.names.add(name);
    nameTables.add(names);
    addressTables.add(addresses);

    if (size() > 0x10000) {
      // What is this? A Lotus Notes exe?
      throw new IllegalStateException("Too many imports, are you sure the executable is valid?");
    }
  }

  public int size() {
    return entries.size();
  }

  public String getName(int index) {
    return names.get(index);
  }

  public ImportDirectoryTable getNameTable(int index) {
    return nameTables.get(index);
  }

  public ImportDirectoryTable getAddressTable(int index) {
    return addressTables.get(index);
  }

  public ImportDirectoryEntry getEntry(int index) {
    return entries.get(index);
  }
}
