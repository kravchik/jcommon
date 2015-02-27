package yk.lang.yads;

import yk.jcommon.collections.YMap;
import yk.jcommon.collections.YSet;

import static yk.jcommon.collections.YHashMap.hm;
import static yk.jcommon.collections.YHashSet.hs;

public class NamespaceEntry {
    public YSet<String> packages = hs();
    public YMap<String, String> classes = hm();
}
