package com.guanghe.onion;

public class commonTest {

    @org.junit.Test
    public void testDemo1() {

        String s = "    pm.expect(pm.response.responseTime).to.be.below(3000);";
        System.out.println(s.indexOf("pm.expect(pm.response.responseTime).to.be.below("));
        System.out.println(s.indexOf("to.be.below("));
        System.out.println(s.substring(s.indexOf("to.be.below(") + 12, s.lastIndexOf(");")));
    }


}
