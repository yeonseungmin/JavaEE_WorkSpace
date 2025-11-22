package com.ch.website;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/FileDownloadServlet")
public class FileDownloadServlet extends HttpServlet {
    private String uploadPath = "C:\\Upload2"; // 실제 업로드 폴더

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {

        String fileName = req.getParameter("name");
        if(fileName == null || fileName.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "파일명이 없습니다.");
            return;
        }

        File file = new File(uploadPath, fileName);
        if(!file.exists()) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "파일이 존재하지 않습니다.");
            return;
        }

        // 브라우저가 바로 보여주도록 MIME 타입 설정
        resp.setContentType(getServletContext().getMimeType(file.getName()));
        resp.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");

        Files.copy(file.toPath(), resp.getOutputStream());
    }
}
