package yk.lang.yads;

public class TestEnumClass {
    public TestEnum enumField;

    public TestEnumClass(TestEnum enumField) {
        this.enumField = enumField;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TestEnumClass that = (TestEnumClass) o;

        if (enumField != that.enumField) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return enumField != null ? enumField.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "TestEnumClass{" +
                "enumField=" + enumField +
                '}';
    }
}
