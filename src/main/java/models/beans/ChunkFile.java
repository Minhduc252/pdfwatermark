package models.beans;

import java.io.Serializable;
import java.util.Arrays;

public class ChunkFile implements Serializable {
    private static final long serialVersionUID = 1L;
    private int fileId;
    private int chunkIndex;
    private int totalChunks;
    private byte[] data;

    public ChunkFile() {
        super();
    }

    public ChunkFile(int fileId, int chunkIndex, int totalChunks, byte[] data) {
        super();
        this.fileId = fileId;
        this.chunkIndex = chunkIndex;
        this.totalChunks = totalChunks;
        this.data = data;
    }

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    public int getChunkIndex() {
        return chunkIndex;
    }

    public void setChunkIndex(int chunkIndex) {
        this.chunkIndex = chunkIndex;
    }

    public int getTotalChunks() {
        return totalChunks;
    }

    public void setTotalChunks(int totalChunks) {
        this.totalChunks = totalChunks;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ChunkFile [fileId=" + fileId + ", chunkIndex=" + chunkIndex + ", totalChunks=" + totalChunks
                + ", data=" + Arrays.toString(data) + "]";
    }

}
