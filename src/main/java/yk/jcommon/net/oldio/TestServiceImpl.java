package yk.jcommon.net.oldio;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 9/28/12
 * Time: 12:41 AM
 */
public class TestServiceImpl implements TestService {

    public TestServiceImpl() {
    }

    @Override
    public Object mul2(Integer value) {
        System.out.println("called mul2 with " + value);
        return value * 2;
    }
}
