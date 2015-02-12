package yk.lang.yads;

import yk.jcommon.collections.YArrayList;

public class TestClass {
    public YArrayList someList;
    public int someInt;

    public TestClass(YArrayList someList, int someInt) {
        this.someList = someList;
        this.someInt = someInt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TestClass testClass = (TestClass) o;

        if (someInt != testClass.someInt) return false;
        if (someList != null ? !someList.equals(testClass.someList) : testClass.someList != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = someList != null ? someList.hashCode() : 0;
        result = 31 * result + someInt;
        return result;
    }
}
