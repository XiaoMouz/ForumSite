package com.mou.gameforum.utils;

import com.mou.gameforum.entity.vo.RequestResult;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class ResponseUtils {
    public static void responseJson(HttpServletResponse response, RequestResult result) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter pw = response.getWriter();
        pw.write(result.toString());
        pw.flush();
    }
}
