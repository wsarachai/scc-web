package org.ktm.servlet.file;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.imgscalr.Scalr;
import org.ktm.dao.KTMEMDaoFactory;
import org.ktm.dao.gallery.ImageDao;
import org.ktm.domain.gallery.Image;

@WebServlet( name = "GetFileServlet",
				urlPatterns = { "/GetImage", "/RGB7-backoffice-v4/GetImage" } )
public class GeneralGetFileServlet extends HttpServlet {

	private static final long	serialVersionUID	= 1L;

	public GeneralGetFileServlet() {
		super();
	}

	protected void
			doGet( HttpServletRequest request, HttpServletResponse response )	throws ServletException,
																				IOException {
		doPost( request, response );
	}

	protected void
			doPost( HttpServletRequest request, HttpServletResponse response )	throws ServletException,
																				IOException {
		if ( request.getParameter( "getfile" ) != null ) {
			File file = getFile( request );
			if ( file != null ) {
				int bytes = 0;
				ServletOutputStream op = response.getOutputStream();

				response.setContentType( getMimeType( file ) );
				response.setContentLength( (int) file.length() );
				response.setHeader( "Content-Disposition",
						"inline; filename=\"" + file.getName() + "\"" );

				byte [] bbuf = new byte[ 1024 ];
				DataInputStream in = new DataInputStream( new FileInputStream( file ) );

				while ( ( in != null ) && ( ( bytes = in.read( bbuf ) ) != -1 ) ) {
					op.write( bbuf, 0, bytes );
				}

				in.close();
				op.flush();
				op.close();
			}
		} else if ( request.getParameter( "getthumb" ) != null ) {
			File file = getFile( request );
			if ( file != null ) {
				String mimetype = getMimeType( file );
				if ( mimetype.endsWith( "png" ) || mimetype.endsWith( "jpeg" )
						|| mimetype.endsWith( "gif" ) ) {
					BufferedImage im = ImageIO.read( file );
					if ( im != null ) {
						BufferedImage thumb = Scalr.resize( im, 75 );
						ByteArrayOutputStream os = new ByteArrayOutputStream();
						if ( mimetype.endsWith( "png" ) ) {
							ImageIO.write( thumb, "PNG", os );
							response.setContentType( "image/png" );
						} else if ( mimetype.endsWith( "jpeg" ) ) {
							ImageIO.write( thumb, "jpg", os );
							response.setContentType( "image/jpeg" );
						} else {
							ImageIO.write( thumb, "GIF", os );
							response.setContentType( "image/gif" );
						}
						ServletOutputStream srvos = response.getOutputStream();
						response.setContentLength( os.size() );
						response.setHeader( "Content-Disposition",
								"inline; filename=\"" + file.getName() + "\"" );
						os.writeTo( srvos );
						srvos.flush();
						srvos.close();
					}
				}
			} // TODO: check and report success
		} else {
			PrintWriter writer = response.getWriter();
			writer.write( "no file found" );
		}
	}

	private File getFileUploadPath( HttpServletRequest request ) {
		ServletContext context = request.getServletContext();
		String upload_path = context.getInitParameter( "upload_path" );
		return new File( upload_path );
	}

	protected File getFile( HttpServletRequest request ) {
		String uuid = request.getParameter( "uuid" );
		ImageDao imageDao = KTMEMDaoFactory.getInstance().getImageDao();
		Image image = (Image) imageDao.get( Integer.parseInt( uuid ) );
		if ( image != null ) {
			File file = new File( getFileUploadPath( request ), image.getPath() );
			if ( file.exists() ) {
				return file;
			}
		}
		return null;
	}

	private String getMimeType( File file ) {
		String mimetype = "";
		if ( file.exists() ) {
			// URLConnection uc = new URL("file://" +
			// file.getAbsolutePath()).openConnection();
			// String mimetype = uc.getContentType();
			// MimetypesFIleTypeMap gives PNG as application/octet-stream, but
			// it seems so does URLConnection
			// have to make dirty workaround
			if ( getSuffix( file.getName() ).equalsIgnoreCase( "png" ) ) {
				mimetype = "image/png";
			} else {
				javax.activation.MimetypesFileTypeMap mtMap = new javax.activation.MimetypesFileTypeMap();
				mimetype = mtMap.getContentType( file );
			}
		}
		//System.out.println( "mimetype: " + mimetype );
		return mimetype;
	}

	private String getSuffix( String filename ) {
		String suffix = "";
		int pos = filename.lastIndexOf( '.' );
		if ( pos > 0 && pos < filename.length() - 1 ) {
			suffix = filename.substring( pos + 1 );
		}
		//System.out.println( "suffix: " + suffix );
		return suffix;
	}
}
