package yk.lang.yads;

import static yk.jcommon.collections.YArrayList.al;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 05/02/15
 * Time: 08:59
 */
public class Test {
    public static void main(String[] args) {
        System.out.println(YADSParser.parseList("hello world"));
        System.out.println(YADSParser.parseClass("XY(10 20)"));
        System.out.println(YADSParser.parseClass("HBox(pos : 10, 20 VBox(size: 50, 50))"));
        System.out.println(HBox.class.getName());
        System.out.println(YADSSerializer.parseClass(null, YADSParser.parseClass("HBox(pos : 10, 20)")).toString());

        System.out.println(YADSSerializer.parseList(YADSParser.parseList("import: yk.lang.yads HBox(pos : 10, 20)")).toString());

        //TODO convert with respect to method call arguments types!
        //TODO map or YAD if class not defined and unknown
    }


}
