package controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.extgstate.PdfExtGState;

@WebServlet("/watermark")
@MultipartConfig
public class WatermarkServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pdfUrl = request.getParameter("pdf");
        if (pdfUrl == null || pdfUrl.isEmpty()) {
            throw new IllegalArgumentException("PDF URL is required.");
        }

        File pdfDir = new File(getServletContext().getRealPath("/"));

        String baseUrl = "http://localhost:8080/pdf-watermark/pdfs";
        String relativePath = pdfUrl.replace(baseUrl, "");

        File pdfFile = new File(pdfDir + "\\" + relativePath);

        if (!pdfFile.exists() || !pdfFile.isFile()) {
            throw new FileNotFoundException("PDF file not found at path: " + pdfFile.getAbsolutePath());
        }

        String watermarkType = request.getParameter("watermark-type");
        String watermarkText = request.getParameter("watermark-text");
        Part imageFilePart = request.getPart("image-file");
        float opacityValue = Float.parseFloat(request.getParameter("wm-opacity"));
        float fontSizeValue = Float.parseFloat(request.getParameter("wm-fontsize"));

        addWatermarkToPdf(pdfFile, watermarkType, watermarkText, imageFilePart, 60, opacityValue, fontSizeValue);

        response.getWriter().write("watermark added successfully!");
    }

    private void addWatermarkToPdf(File pdfFile, String watermarkType, String watermarkText, Part imageFilePart,
            float rotateValue, float opacityValue, Float fontSizeValue) throws IOException {

        String originalFileName = pdfFile.getName();
        String newFileName = originalFileName.replace(".pdf", "(preview).pdf");
        File outputFile = new File(pdfFile.getParent(), newFileName);

        // Đảm bảo file PDF đầu vào tồn tại
        if (!pdfFile.exists() || !pdfFile.isFile()) {
            throw new FileNotFoundException("PDF file not found: " + pdfFile.getAbsolutePath());
        }

        try (PdfReader reader = new PdfReader(pdfFile);
                PdfWriter writer = new PdfWriter(outputFile);
                PdfDocument pdfDoc = new PdfDocument(reader, writer)) {

            int numberOfPages = pdfDoc.getNumberOfPages();

            for (int i = 1; i <= numberOfPages; i++) {
                PdfPage page = pdfDoc.getPage(i);
                Rectangle pageSize = page.getPageSize();
                PdfCanvas canvas = new PdfCanvas(page);

                // Text watermark
                if ("text".equalsIgnoreCase(watermarkType) && watermarkText != null && !watermarkText.isEmpty()) {
                    PdfExtGState extGState = new PdfExtGState().setFillOpacity(opacityValue);
                    PdfFont font = PdfFontFactory.createFont();
                    float fontSize = fontSizeValue;

                    float textWidth = font.getWidth(watermarkText, fontSize);
                    float textHeight = fontSize;

                    if (rotateValue == 90 || rotateValue == 270) {
                        float tmp = textWidth;
                        textWidth = textHeight;
                        textHeight = tmp;
                    }

                    if (rotateValue != 0 && rotateValue != 180 && rotateValue != 90 && rotateValue != 270) {
                        float radians = (float) Math.toRadians(rotateValue);

                        float rotatedWidth = (float) (textWidth * Math.abs(Math.cos(radians)));
                        float rotatedHeight = (float) (textWidth * Math.abs(Math.sin(radians)));

                        textWidth = rotatedWidth;
                        textHeight = rotatedHeight;
                    }

                    float centerX = pageSize.getWidth() / 2;
                    float centerY = pageSize.getHeight() / 2;

                    float textOffsetX = textWidth / 2;
                    float textOffsetY = textHeight / 2;
                    System.out.println(textOffsetX);

                    float offsetX = centerX - textOffsetX;
                    float offsetY = centerY - textOffsetY;

                    canvas.saveState()
                            .setExtGState(extGState)
                            .beginText()
                            .setFontAndSize(font, fontSize)
                            .moveText(offsetX, offsetY)
                            .setTextMatrix(
                                    (float) Math.cos(Math.toRadians(rotateValue)),
                                    (float) Math.sin(Math.toRadians(rotateValue)),
                                    (float) -Math.sin(Math.toRadians(rotateValue)),
                                    (float) Math.cos(Math.toRadians(rotateValue)),
                                    offsetX, offsetY)

                            .setFillColor(ColorConstants.GRAY)
                            .showText(watermarkText)
                            .endText()
                            .restoreState();
                }

                // Image watermark
                if ("image".equalsIgnoreCase(watermarkType) && imageFilePart != null) {
                    File tempImageFile = null;
                    try {
                        String contentDisposition = imageFilePart.getHeader("Content-Disposition");
                        String imageName = contentDisposition
                                .substring(contentDisposition.indexOf("filename=") + 10,
                                        contentDisposition.length() - 1);
                        tempImageFile = File.createTempFile("watermark_", "_" + imageName);
                        try (FileOutputStream fos = new FileOutputStream(tempImageFile)) {
                            imageFilePart.getInputStream().transferTo(fos);
                        }

                        ImageData imageData = ImageDataFactory.create(tempImageFile.getAbsolutePath());

                        PdfExtGState extGState = new PdfExtGState().setFillOpacity(opacityValue);

                        float imageWidth = imageData.getWidth();
                        float imageHeight = imageData.getHeight();

                        float crossLength = (float) Math.sqrt(Math.pow(imageWidth, 2) + Math.pow(imageHeight, 2));
                        float scaleFactor = Math.min(
                                pageSize.getWidth() / crossLength,
                                pageSize.getHeight() / crossLength);

                        imageWidth *= scaleFactor;
                        imageHeight *= scaleFactor;

                        float centerX = pageSize.getWidth() / 2;
                        float centerY = pageSize.getHeight() / 2;

                        float xPosition = centerX;
                        float yPosition = centerY;

                        float radians = 0;   
                        canvas.saveState()
                                .setExtGState(extGState)
                                .addImageWithTransformationMatrix(
                                        imageData,
                                        (float) (imageWidth * Math.cos(radians)), // a
                                        (float) (imageWidth * Math.sin(radians)), // b
                                        (float) (-imageHeight * Math.sin(radians)), // c
                                        (float) (imageHeight * Math.cos(radians)), // d
                                        xPosition - (imageWidth / 2), 
                                        yPosition - (imageHeight / 2));
                        canvas.restoreState();
                    } catch (Exception e) {
                        throw new IOException("Error processing image watermark", e);
                    } finally {
                        if (tempImageFile != null && tempImageFile.exists()) {
                            tempImageFile.delete();
                        }
                    }
                }
            }
        }

        System.out.println("Watermark added to file: " + outputFile.getAbsolutePath());
    }

}
