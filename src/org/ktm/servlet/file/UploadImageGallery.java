package org.ktm.servlet.file;

import java.io.File;
import java.util.Date;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import org.apache.tomcat.util.http.fileupload.FileItem;

@WebServlet("/upload_image_gallery")
public class UploadImageGallery extends UploadServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected String getImagePath(Date date, String contentName) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected File getFile(HttpServletRequest request) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected void removeImage(HttpServletRequest request, String filename) {
        // TODO Auto-generated method stub

    }

    @Override
    protected void addImage(HttpServletRequest request, FileItem item) {
        // TODO Auto-generated method stub

    }

    @Override
    protected String getServletPath() {
        return "upload_image_gallery";
    }

}
