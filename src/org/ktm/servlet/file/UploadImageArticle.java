package org.ktm.servlet.file;

import java.io.File;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.ktm.exception.CreateException;
import org.ktm.exception.DeleteException;
import org.ktm.scc.CRUDArticleServlet;
import org.ktm.scc.bean.ArticleBean;
import org.ktm.scc.bean.ImageBean;
import org.ktm.utils.DateUtils;
import org.ktm.utils.Localizer;

@WebServlet("/upload_image_article")
public class UploadImageArticle extends UploadServlet {

    private static final long serialVersionUID = 1L;

    @Override
    public String getBeanClass() {
        return "org.ktm.web.bean.FormBean";
    }

    protected String getImagePath(Date date, String contentName) {
        Calendar c = Calendar.getInstance(Localizer.getCurrentLocale());
        c.setTime(date);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        return String.valueOf(year) + "/" + String.valueOf(month) + "/" + contentName;
    }

    protected File getFile(HttpServletRequest request) {
        File result = getFileUploadPath();
        HttpSession session = request.getSession();
        String uuid = (String) session.getAttribute(ArticleBean.UNIQUD_ID);
        ArticleBean bean = (ArticleBean) session.getAttribute(uuid);
        if (uuid != null && bean != null) {
            try {
                boolean dirExist = true;
                File file = new File(getFileUploadPath(), getImagePath(DateUtils.formatString(bean.getDateCreated()), uuid));
                if (!file.exists()) {
                    dirExist = file.mkdirs();
                }
                if (dirExist) {
                    result = file;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    protected void removeImage(HttpServletRequest request, String filename) {
        HttpSession session = request.getSession();

        String uuid = (String) session.getAttribute(ArticleBean.UNIQUD_ID);
        ArticleBean bean = (ArticleBean) session.getAttribute(uuid);

        if (bean != null && filename != null) {
            try {
                String path = getImagePath(DateUtils.formatString(bean.getDateCreated()), bean.getIdentifier());
                String imgPath = path + "/" + filename;
                if (bean.getImages().containsKey(imgPath)) {
                    bean.getImages().remove(imgPath);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

            try {
                CRUDArticleServlet.doSaveArticle(bean, request);
            } catch (CreateException e) {
                e.printStackTrace();
            } catch (DeleteException e) {
                e.printStackTrace();
            }
        }
    }

    protected void addImage(HttpServletRequest request, FileItem item) {
        HttpSession session = request.getSession();
        String uuid = (String) session.getAttribute(ArticleBean.UNIQUD_ID);
        ArticleBean bean = (ArticleBean) session.getAttribute(uuid);

        if (bean != null && item != null) {
            try {
                ImageBean img = new ImageBean();
                String path = getImagePath(DateUtils.formatString(bean.getDateCreated()), bean.getIdentifier());
                img.setPath(path + "/" + item.getName());
                img.setSize(item.getSize());
                bean.getImages().put(img.getPath(), img);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            try {
                CRUDArticleServlet.doSaveArticle(bean, request);
            } catch (CreateException e) {
                e.printStackTrace();
            } catch (DeleteException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected String getServletPath() {
        return "upload_image_article";
    }
}
