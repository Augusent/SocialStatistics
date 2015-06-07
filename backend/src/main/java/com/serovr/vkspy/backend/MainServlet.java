package com.serovr.vkspy.backend;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * Created by Ruslan on 24.12.2014.
 */
public class MainServlet extends HttpServlet{
    private static  double myhello = 0.0;

    public void init() throws ServletException
    {
        System.out.println("----------");
        System.out.println("---------- Initialized ----------");
        System.out.println("----------");
        myhello = 234.5;
    }

    public static double getHello(){
        return myhello;
    }


}
