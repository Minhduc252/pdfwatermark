package models.bo;

import models.dao.PdfFileDAO;

public class PdfFileBO {
    public static int getCurrentFileId(){
        return PdfFileDAO.getCurrentFileId();
    }
}
