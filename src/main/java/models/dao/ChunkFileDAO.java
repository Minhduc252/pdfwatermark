package models.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.beans.ChunkFile;

public class ChunkFileDAO {
    public static void insertNewChunkFile(ChunkFile _chunkFile) {
		String sql = "INSERT INTO chunk_files (fileId, chunkIndex, totalChunks, data) VALUES (?, ?, ?, ?)";
		Connection conn = DBConnection.getConnection();
		PreparedStatement stmt;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, _chunkFile.getFileId());
			stmt.setInt(2, _chunkFile.getChunkIndex());
			stmt.setInt(3, _chunkFile.getTotalChunks());
			stmt.setBytes(4, _chunkFile.getData());
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	public static List<ChunkFile> getAllChunkFileByFileId(int fileId) {
		String sql = "SELECT fileId, chunkIndex, totalChunks, data FROM chunk_files WHERE fileId = ?";
		List<ChunkFile> chunkFiles = new ArrayList<>();
		Connection conn = DBConnection.getConnection();
		try ( PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, fileId);

			int total = 0;
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					ChunkFile chunkFile = new ChunkFile();
					chunkFile.setFileId(rs.getInt("fileId"));
					chunkFile.setChunkIndex(rs.getInt("chunkIndex"));
					chunkFile.setTotalChunks(rs.getInt("totalChunks"));
					chunkFile.setData(rs.getBytes("data"));

					total = chunkFile.getTotalChunks();
					chunkFiles.add(chunkFile);
				}
				if (chunkFiles.size() != total) {
					deleteAllChunkFileByFileId(fileId);
					return null;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return chunkFiles;
	}

	public static void deleteAllChunkFileByFileId(int fileId) {
		String sql = "DELETE FROM chunk_files WHERE fileId = ?";
		Connection conn = DBConnection.getConnection();
		try ( PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, fileId);
			int rowsDeleted = stmt.executeUpdate();
			System.out.println("Đã xóa " + rowsDeleted + " chunk files với fileId: " + fileId);
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("Lỗi khi xóa chunk files với fileId: " + fileId);
		}
	}

    
}
