package com.andreeagrosu;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.util.HashMap;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class App {

    private static HashMap<String, Method> routeMap = new HashMap<String, Method>();

    public static void main(String[] args) throws Exception {

        routeMap.put("/test1", WebController.class.getMethod("test1"));
        routeMap.put("/test2", WebController.class.getMethod("test2"));
        routeMap.put("/test3", WebController.class.getMethod("test3"));
        routeMap.put("/test4", WebController.class.getMethod("test4"));

        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/", new MyHandler());
        server.setExecutor(null); // creates a default executor
        server.start();

    }

    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {

            WebController webController = new WebController();

            String response = "Welcome!";

            HttpMethod httpMethod = t.getRequestMethod().equals("GET") ? HttpMethod.GET : HttpMethod.POST;

            Class<WebController> aClass = WebController.class;

            for (Method method : aClass.getDeclaredMethods()) {
                if (method.isAnnotationPresent(WebRoute.class)) {
                    Annotation annotation = method.getAnnotation(WebRoute.class);
                    WebRoute webRoute = (WebRoute) annotation;
                    Method routeMethod = routeMap.get(webRoute.path());

                    if (webRoute.method().equals(httpMethod)) {

                        try {
                            routeMethod.invoke(webController);
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }


}
