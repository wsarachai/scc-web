package org.ktm.servlet.file;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.imageio.ImageIO;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.imgscalr.Scalr;
import org.ktm.scc.bean.ArticleBean;
import org.ktm.scc.bean.ImageBean;
import org.ktm.utils.DateUtils;
import org.ktm.utils.Localizer;

@WebServlet("/upload")
public class UploadServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private File              fileUploadPath;

    @Override
    public void init(ServletConfig config) {
        ServletContext context = config.getServletContext();
        String upload_path = context.getInitParameter("upload_path");
        fileUploadPath = new File(upload_path);
    }

    private String getImagePath(Date date, String uuid) {
        Calendar c = Calendar.getInstance(Localizer.getCurrentLocale());
        c.setTime(date);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        return String.valueOf(year) + "/" + String.valueOf(month) + "/" + uuid;
    }

    private File getFile(HttpServletRequest request) {
        File result = fileUploadPath;
        HttpSession session = request.getSession();
        String uuid = (String) session.getAttribute(ArticleBean.UNIQUD_ID);
        ArticleBean bean = (ArticleBean) session.getAttribute(uuid);
        if (uuid != null && bean != null) {
            try {
                boolean dirExist = true;
                File file = new File(fileUploadPath, getImagePath(DateUtils.formatString(bean.getDateCreated()), uuid));
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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // if (AuthenticatorFactory.isUserLoggedIn(request)) {
        HttpSession session = request.getSession();

        String uuid = (String) session.getAttribute(ArticleBean.UNIQUD_ID);
        ArticleBean bean = (ArticleBean) session.getAttribute(uuid);

        if (request.getParameter("getfile") != null && !request.getParameter("getfile").isEmpty()) {
            File file = new File(getFile(request), request.getParameter("getfile"));
            if (file.exists()) {
                int bytes = 0;
                ServletOutputStream op = response.getOutputStream();

                response.setContentType(getMimeType(file));
                response.setContentLength((int) file.length());
                response.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");

                byte[] bbuf = new byte[1024];
                DataInputStream in = new DataInputStream(new FileInputStream(file));

                while ((in != null) && ((bytes = in.read(bbuf)) != -1)) {
                    op.write(bbuf, 0, bytes);
                }

                in.close();
                op.flush();
                op.close();
            }
        } else if (request.getParameter("delfile") != null && !request.getParameter("delfile").isEmpty()) {
            String filename = request.getParameter("delfile");
            File file = new File(getFile(request), filename);
            if (file.exists()) {
                file.delete(); // TODO:check and report success
                removeImage(bean, filename);
            }
        } else if (request.getParameter("getthumb") != null && !request.getParameter("getthumb").isEmpty()) {
            File file = new File(getFile(request), request.getParameter("getthumb"));
            if (file.exists()) {
                String mimetype = getMimeType(file);
                if (mimetype.endsWith("png") || mimetype.endsWith("jpeg") || mimetype.endsWith("gif")) {
                    BufferedImage im = ImageIO.read(file);
                    if (im != null) {
                        BufferedImage thumb = Scalr.resize(im, 75);
                        ByteArrayOutputStream os = new ByteArrayOutputStream();
                        if (mimetype.endsWith("png")) {
                            ImageIO.write(thumb, "PNG", os);
                            response.setContentType("image/png");
                        } else if (mimetype.endsWith("jpeg")) {
                            ImageIO.write(thumb, "jpg", os);
                            response.setContentType("image/jpeg");
                        } else {
                            ImageIO.write(thumb, "GIF", os);
                            response.setContentType("image/gif");
                        }
                        ServletOutputStream srvos = response.getOutputStream();
                        response.setContentLength(os.size());
                        response.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");
                        os.writeTo(srvos);
                        srvos.flush();
                        srvos.close();
                    }
                }
            } // TODO: check and report success
        } else {
            PrintWriter writer = response.getWriter();
            writer.write("call POST with multipart form data");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (!ServletFileUpload.isMultipartContent(request)) { throw new IllegalArgumentException("Request is not multipart, please 'multipart/form-data' enctype for your form."); }

        String uuid = (String) session.getAttribute(ArticleBean.UNIQUD_ID);
        ArticleBean bean = (ArticleBean) session.getAttribute(uuid);

        if (bean != null) {
            ServletFileUpload uploadHandler = new ServletFileUpload(new DiskFileItemFactory());
            PrintWriter writer = response.getWriter();
            response.setContentType("application/json");
            JSONArray json = new JSONArray();
            try {
                List<FileItem> items = uploadHandler.parseRequest(request);
                for (FileItem item : items) {
                    if (!item.isFormField()) {
                        File file = new File(getFile(request), item.getName());
                        item.write(file);
                        addImage(bean, item);
                        JSONObject jsono = new JSONObject();
                        jsono.put("name", item.getName());
                        jsono.put("size", item.getSize());
                        jsono.put("url", "upload?getfile=" + item.getName());
                        jsono.put("thumbnail_url", "upload?getthumb=" + item.getName());
                        jsono.put("delete_url", "upload?delfile=" + item.getName());
                        jsono.put("delete_type", "GET");
                        json.add(jsono);
                    }
                }
            } catch (FileUploadException e) {
                throw new RuntimeException(e);
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                writer.write(json.toString());
                writer.close();
            }
        }
    }

    private void removeImage(ArticleBean bean, String filename) {
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
        }
    }

    private void addImage(ArticleBean bean, FileItem item) {
        if (bean != null && item != null) {
            try {
                ImageBean img = new ImageBean();
                String path = getImagePath(DateUtils.formatString(bean.getDateCreated()), bean.getIdentifier());
                img.setPath(path + "/" + item.getName());
                bean.getImages().put(img.getPath(), img);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    private String getMimeType(File file) {
        String mimetype = "";
        if (file.exists()) {
            // URLConnection uc = new URL("file://" +
            // file.getAbsolutePath()).openConnection();
            // String mimetype = uc.getContentType();
            // MimetypesFIleTypeMap gives PNG as application/octet-stream, but
            // it seems so does URLConnection
            // have to make dirty workaround
            if (getSuffix(file.getName()).equalsIgnoreCase("png")) {
                mimetype = "image/png";
            } else {
                javax.activation.MimetypesFileTypeMap mtMap = new javax.activation.MimetypesFileTypeMap();
                mimetype = mtMap.getContentType(file);
            }
        }
        System.out.println("mimetype: " + mimetype);
        return mimetype;
    }

    private String getSuffix(String filename) {
        String suffix = "";
        int pos = filename.lastIndexOf('.');
        if (pos > 0 && pos < filename.length() - 1) {
            suffix = filename.substring(pos + 1);
        }
        System.out.println("suffix: " + suffix);
        return suffix;
    }
}
