package models.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import models.beans.PdfPreview;

public class PdfFileDAO {
    public static String getFileNameByFileId(String fileId) {
        String sql = "SELECT * FROM pdf_files WHERE fileId = ?";
        String fileName = null;
        Connection conn = DBConnection.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, fileId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    fileName = rs.getString("fileId");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fileName;
    }

    public static List<PdfPreview> getAllPdfPreviewByUserId(int userId) {
        String sql = "SELECT * FROM pdf_files WHERE userId = ?";
        Connection conn = DBConnection.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                List<PdfPreview> pdfPreviews = new ArrayList<>();
                while (rs.next()) {
                    PdfPreview pdfPreview = new PdfPreview();
                    pdfPreview.setFileName(rs.getString("fileName"));
                    pdfPreview.setFileId(rs.getString("id"));
                    pdfPreview.setLastModified(rs.getDate("lastModified").toString());
                    pdfPreviews.add(pdfPreview);
                }
                return pdfPreviews;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int getCurrentFileId() {
        String sql = "SELECT * FROM temppdfids Where id = ?";
        Connection conn = DBConnection.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, 1);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("fileId");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 1;
    }

    public static void updateIndex() {
        String sql = "UPDATE temppdfids SET fileId = ? WHERE id = ?";
        Connection conn = DBConnection.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, getCurrentFileId() + 1);
            stmt.setInt(2, 1);
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Cập nhật thành công fileId");
            } else {
                System.out.println("Không tìm thấy bản ghi để cập nhật.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertPdfFile(int _userId, String _fileName, int _fileId) {
        String sql = "INSERT INTO pdf_files (id,fileName, userId, lastModified) VALUES (?,?, ?, ?)";
        Connection conn = DBConnection.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, _fileId);
            stmt.setString(2, _fileName);
            stmt.setInt( 3, _userId);
            stmt.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Đã thêm file pdf thành công");
            } else {
                System.out.println("Không thêm được file pdf");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
