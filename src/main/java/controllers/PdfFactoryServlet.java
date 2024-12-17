package controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.beans.User;
import models.bo.ChunkFileBO;

@WebServlet("/PdfFactory")
public class PdfFactoryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        if (user == null) {
            resp.sendRedirect("Login.jsp");
            return;
        }

        String mode = req.getParameter("mode");
        if (mode == null) {
            int fileId = Integer.parseInt(req.getParameter("fileId"));
            byte[] pdf = ChunkFileBO.mergeChunkFile(fileId);

            String filePath = "pdfs/";
            File pdfDir = new File(getServletContext().getRealPath("/") + filePath);
            if (!pdfDir.exists()) {
                pdfDir.mkdirs();
            }

            String pdfFileName = "file_" + fileId + ".pdf";
            File pdfFile = new File(pdfDir, pdfFileName);

            try (FileOutputStream fos = new FileOutputStream(pdfFile)) {
                fos.write(pdf);
            }

            req.setAttribute("pdfPath", filePath + pdfFileName);
            req.setAttribute("deletePdfFile", pdfFile.getAbsolutePath());

            req.getRequestDispatcher("PdfFactory.jsp").forward(req, resp);
        }
    }

}
