package models.beans;

public class PdfPreview {
    private String fileId;
    private String fileName;
    private String lastModified;

    public PdfPreview() {
    }

    public PdfPreview(String fileId, String fileName, String lastModified) {
        this.fileId = fileId;
        this.fileName = fileName;
        this.lastModified = lastModified;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getLastModified() {
        return lastModified;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    @Override
    public String toString() {
        return "PdfPreview [fileId=" + fileId + ", fileName=" + fileName + ", lastModified=" + lastModified + "]";
    }

}
