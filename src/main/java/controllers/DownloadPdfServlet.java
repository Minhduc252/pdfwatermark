package controllers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.bo.ChunkFileBO;
import models.bo.WatermarkBO;

@WebServlet("/DownloadPdf")
public class DownloadPdfServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final ChunkFileBO CHUNK_FILE_BO = new ChunkFileBO();
	private static final WatermarkBO WATERMARK_BO = new WatermarkBO();

	public DownloadPdfServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		byte[] data = WATERMARK_BO.AddWaterMark(1);

		if (data != null && data.length > 0) {
			response.setContentType("application/pdf");

			response.setHeader("Content-Disposition", "attachment; filename=file");
			try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
					OutputStream outputStream = response.getOutputStream()) {

				byte[] buffer = new byte[1024];
				int length;

				while ((length = byteArrayInputStream.read(buffer)) > 0) {
					outputStream.write(buffer, 0, length);
				}

				outputStream.flush();
			} catch (IOException e) {
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				response.getWriter().write("Lỗi khi tải file PDF.");
				e.printStackTrace();
			}
		} else {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			response.getWriter().write("Không tìm thấy tệp PDF.");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
