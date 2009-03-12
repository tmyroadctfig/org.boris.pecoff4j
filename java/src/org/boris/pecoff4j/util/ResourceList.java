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

import java.util.ArrayList;
import java.util.List;

import org.boris.pecoff4j.PE;
import org.boris.pecoff4j.ResourceDirectory;
import org.boris.pecoff4j.ResourceLanguageDirectory;
import org.boris.pecoff4j.ResourceNameDirectory;
import org.boris.pecoff4j.ResourceTypeDirectory;
import org.boris.pecoff4j.constant.ResourceType;

public class ResourceList
{
    private List<String> types = new ArrayList();
    private List<String> names = new ArrayList();
    private List<String> langIds = new ArrayList();
    private List<byte[]> datas = new ArrayList();

    public static ResourceList extract(PE pe) {
        ResourceList rl = new ResourceList();
        ResourceDirectory rd = null; // FIXME
        if (rd == null) {
            return rl;
        }

        for (int i = 0; i < rd.size(); i++) {
            int tni = rd.getEntryName(i);
            String tn = ResourceType.toString(tni);
            if (tn == null)
                tn = Integer.toString(tni);
            ResourceTypeDirectory rtd = rd.getType(i);
            for (int j = 0; j < rtd.size(); j++) {
                int nni = rtd.getEntryName(j);
                String nn = Integer.toString(nni);
                ResourceNameDirectory rnd = rtd.getName(j);
                for (int k = 0; k < rnd.size(); k++) {
                    int lni = rnd.getEntryName(k);
                    String ln = Integer.toString(lni);
                    ResourceLanguageDirectory rld = rnd.getLanguage(k);
                    rl.add(tn, nn, ln, rld.getData());
                }
            }
        }

        return rl;
    }

    public void add(String type, String name, String languageId, byte[] data) {
        types.add(type);
        names.add(name);
        langIds.add(languageId);
        datas.add(data);
    }

    public int size() {
        return types.size();
    }

    public String getType(int index) {
        return types.get(index);
    }

    public String getName(int index) {
        return names.get(index);
    }

    public String getLanguageId(int index) {
        return langIds.get(index);
    }

    public byte[] getData(int index) {
        return datas.get(index);
    }
}
