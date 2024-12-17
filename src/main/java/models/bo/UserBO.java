package models.bo;

import java.util.List;

import models.beans.PdfPreview;
import models.beans.User;
import models.dao.PdfFileDAO;
import models.dao.UserDAO;

public class UserBO {

    public static User checkLogin(String username, String password) {
        return UserDAO.checkLogin(username, password);
    }

    public static void registerUser(User user) {
        UserDAO.registerUser(user);
    }

    public static List<PdfPreview> getPdfPreviewAllByUserId(int userId) {
        return PdfFileDAO.getAllPdfPreviewByUserId(userId);
    }

}
