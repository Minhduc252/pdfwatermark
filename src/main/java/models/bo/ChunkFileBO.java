package models.bo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.Part;

import models.beans.ChunkFile;
import models.dao.ChunkFileDAO;
import models.dao.PdfFileDAO;

public class ChunkFileBO {

	public static void processFileChunk(Part _chunkFile, int _fileId, int _chunkIndex, int _totalChunks, String _fileName, int _userId) {
		ChunkFile chunkFile = new ChunkFile();
		chunkFile.setChunkIndex(_chunkIndex);
		chunkFile.setFileId(_fileId);
		chunkFile.setTotalChunks(_totalChunks);

		byte[] data;
		try (InputStream inputStream = _chunkFile.getInputStream();
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
			byte[] buffer = new byte[1024];
			int bytesRead;
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
			}
			data = outputStream.toByteArray();
			if (data != null) {
				chunkFile.setData(data);
				ChunkFileDAO.insertNewChunkFile(chunkFile);
				if (chunkFile.getChunkIndex() == chunkFile.getTotalChunks() - 1) {
					PdfFileDAO.insertPdfFile(_userId,_fileName, _fileId);
					PdfFileDAO.updateIndex();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static byte[] mergeChunkFile(int _fileId) {
		try {
			List<ChunkFile> chunks = ChunkFileDAO.getAllChunkFileByFileId(_fileId);
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			for (ChunkFile chunk : chunks) {
				byteArrayOutputStream.write(chunk.getData());
			}
			byte[] mergedData = byteArrayOutputStream.toByteArray();
			return mergedData;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
