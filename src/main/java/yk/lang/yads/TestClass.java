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

        if (someInt != testClass.someInt) return false;
        if (someList != null ? !someList.equals(testClass.someList) : testClass.someList != null) return false;
        if (someMap != null ? !someMap.equals(testClass.someMap) : testClass.someMap != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = someList != null ? someList.hashCode() : 0;
        result = 31 * result + (someMap != null ? someMap.hashCode() : 0);
        result = 31 * result + someInt;
        return result;
    }

    @Override
    public String toString() {
        return "TestClass{" +
                "someList=" + someList +
                ", someMap=" + someMap +
                ", someInt=" + someInt +
                '}';
    }
}
