package com.andreeagrosu;

public class Routes {

    @WebRoute(path = "/test1")
    public String test1() {
        return "test 1";
    }

    @WebRoute(path = "/test2")
    public String test2() {
        return "test 2";
    }

}


