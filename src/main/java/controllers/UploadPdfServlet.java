package controllers;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import models.beans.User;
import models.bo.ChunkFileBO;
import models.bo.PdfFileBO;

@WebServlet("/UploadPdf")
@MultipartConfig
public class UploadPdfServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        }

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Content-Disposition, Accept");

        Part filePart = request.getPart("file");
        int fileId = PdfFileBO.getCurrentFileId();
        System.out.println(fileId);
        int chunkIndex = Integer.parseInt(request.getParameter("chunkIndex"));
        int totalChunks = Integer.parseInt(request.getParameter("totalChunks"));
        String fileName = request.getParameter("fileName");

        if (filePart == null || filePart.getSize() == 0) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Không có tệp nào được tải lên.\"}");
            return;
        }
        try {
            ChunkFileBO.processFileChunk(filePart, fileId, chunkIndex, totalChunks, fileName, user.getId());

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            try (PrintWriter out = response.getWriter()) {
                if (chunkIndex == totalChunks - 1) {
                    String jsonResponse = "{\"status\":\"success\", \"fileId\":\"" + fileId + "\"}";
                    out.write(jsonResponse);
                } else {
                    out.write("{\"status\":\"uploading\"}");
                }
            } catch (IOException e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("{\"error\": \"Lỗi khi phản hồi dữ liệu.\"" + e.getMessage() + "}");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Đã xảy ra lỗi trong quá trình tải lên. Vui lòng thử lại.\"}");
        }

    }
}
