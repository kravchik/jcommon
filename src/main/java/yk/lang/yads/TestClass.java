package yk.lang.yads;

import yk.jcommon.collections.YArrayList;
import yk.jcommon.collections.YHashMap;
import yk.jcommon.collections.YList;

import java.util.List;

public class TestClass {
    public YArrayList someList;
    public YList someList2;
    public List someList3;
    public YHashMap someMap;
    public int someInt;

    public TestClass2 tc2;

    public boolean someBoolean;

    public TestClass(YArrayList someList, YHashMap someMap, int someInt) {
        this.someList = someList;
        this.someMap = someMap;
        this.someInt = someInt;
    }

    public TestClass(boolean someBoolean) {
        this.someBoolean = someBoolean;
    }

    public TestClass() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TestClass testClass = (TestClass) o;

        if (someBoolean != testClass.someBoolean) return false;
        if (someInt != testClass.someInt) return false;
        if (someList != null ? !someList.equals(testClass.someList) : testClass.someList != null) return false;
        if (someList2 != null ? !someList2.equals(testClass.someList2) : testClass.someList2 != null) return false;
        if (someList3 != null ? !someList3.equals(testClass.someList3) : testClass.someList3 != null) return false;
        if (someMap != null ? !someMap.equals(testClass.someMap) : testClass.someMap != null) return false;
        if (tc2 != null ? !tc2.equals(testClass.tc2) : testClass.tc2 != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = someList != null ? someList.hashCode() : 0;
        result = 31 * result + (someList2 != null ? someList2.hashCode() : 0);
        result = 31 * result + (someList3 != null ? someList3.hashCode() : 0);
        result = 31 * result + (someMap != null ? someMap.hashCode() : 0);
        result = 31 * result + someInt;
        result = 31 * result + (tc2 != null ? tc2.hashCode() : 0);
        result = 31 * result + (someBoolean ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TestClass{" +
                "someList=" + someList +
                ", someList2=" + someList2 +
                ", someList3=" + someList3 +
                ", someMap=" + someMap +
                ", someInt=" + someInt +
                ", tc2=" + tc2 +
                ", someBoolean=" + someBoolean +
                '}';
    }
}
