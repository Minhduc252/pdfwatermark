package models.bo;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;


public class WatermarkBO {

    public byte[] AddWaterMark(int _fileId) {
        byte[] pdfData = ChunkFileBO.mergeChunkFile(_fileId);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            PdfDocument pdfDoc = new PdfDocument(new PdfReader(new ByteArrayInputStream(pdfData)),
                    new PdfWriter(outputStream));
            Document document = new Document(pdfDoc);

            Image image = new Image(
                    ImageDataFactory.create("https://i.pinimg.com/736x/35/2b/03/352b03faa5630e37e1efbb9798b633e1.jpg"));

            for (int i = 1; i <= pdfDoc.getNumberOfPages(); i++) {
                Rectangle pageSize = pdfDoc.getPage(i).getPageSize();

                float x = (pageSize.getWidth() - image.getImageWidth()) / 2;
                float y = (pageSize.getHeight() - image.getImageHeight()) / 2;

                image.setFixedPosition(i, x, y);
                image.setOpacity(0.3F);

                document.add(image);
            }

            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return outputStream.toByteArray();
    }
}
