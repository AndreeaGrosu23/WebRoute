package com.andreeagrosu;

import static com.andreeagrosu.HttpMethod.GET;
import static com.andreeagrosu.HttpMethod.POST;

public class WebController {

    @WebRoute(method = GET, path = "/test1")
    public String test1() {
        return "test 1";
    }

    @WebRoute(method = GET, path = "/test2")
    public String test2() {
        return "test 2";
    }

    @WebRoute(method = POST, path = "/test1")
    public String test1Post() {
        return "test 1 post";
    }

    @WebRoute(method = POST, path = "/test2")
    public String test2Post() {
        return "test 2 post";
    }

}


