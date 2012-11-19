package org.ktm.servlet.file;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.imageio.ImageIO;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.imgscalr.Scalr;
import org.ktm.authen.AuthenticatorFactory;
import org.ktm.scc.bean.UploadImageBean;
import org.ktm.servlet.DispatchServlet;
import org.ktm.utils.DateUtils;
import org.ktm.utils.Localizer;

public abstract class UploadServlet extends DispatchServlet {

	private static final long	serialVersionUID	= 1L;

	private static Logger		logger				= Logger.getLogger( UploadServlet.class );

	public UploadServlet() {
		super();
	}

	protected File	fileUploadPath;

	protected abstract void removeImage(	HttpServletRequest request,
											File filename );

	protected abstract void addImage(	HttpServletRequest request,
										Vector<FileItem> items );

	protected abstract String getServletPath();

	protected abstract String getUUID( HttpSession session );

	@Override
	public void init( ServletConfig config ) {
		ServletContext context = config.getServletContext();
		String upload_path = context.getInitParameter( "upload_path" );
		fileUploadPath = new File( upload_path );
	}

	protected void
			doGet( HttpServletRequest request, HttpServletResponse response )	throws ServletException,
																				IOException {
		synchronized ( this ) {
			if ( AuthenticatorFactory.isUserLoggedIn( request ) ) {
				if ( request.getParameter( "getfile" ) != null && !request.getParameter( "getfile" )
								.isEmpty() ) {
					File file = new File( getFilePath( request ),
							request.getParameter( "getfile" ) );
					if ( file.exists() ) {
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
				} else if ( request.getParameter( "delfile" ) != null && !request.getParameter( "delfile" )
									.isEmpty() ) {
					String filename = request.getParameter( "delfile" );
					File file = new File( getFilePath( request ), filename );

					logger.debug( "Delete file : " + file );

					if ( file.exists() ) {
						file.delete();
					} else {

						logger.debug( "Delete file not exist : " + file );

					}

					removeImage( request, file );

				} else if ( request.getParameter( "getthumb" ) != null && !request.getParameter( "getthumb" )
									.isEmpty() ) {
					File file = new File( getFilePath( request ),
							request.getParameter( "getthumb" ) );
					if ( file.exists() ) {
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
										"inline; filename=\"" + file.getName()
												+ "\"" );
								os.writeTo( srvos );
								srvos.flush();
								srvos.close();
							}
						}
					} // TODO: check and report success
				} else {
					PrintWriter writer = response.getWriter();
					writer.write( "call POST with multipart form data" );
				}
			}
		}
	}

	protected void
			doPost( HttpServletRequest request, HttpServletResponse response )	throws ServletException,
																				IOException {
		synchronized ( this ) {
			if ( AuthenticatorFactory.isUserLoggedIn( request ) ) {

				if ( !ServletFileUpload.isMultipartContent( request ) ) {
					throw new IllegalArgumentException( "Request is not multipart, please 'multipart/form-data' enctype for your form." );
				}

				logger.debug( ">>> doPost image upload... [thread_id=" + Thread.currentThread()
										.getId()
								+ "]" );

				ServletFileUpload uploadHandler = new ServletFileUpload( new DiskFileItemFactory() );

				PrintWriter writer = response.getWriter();
				response.setContentType( "application/json" );

				Vector<FileItem> fileUploaded = new Vector<FileItem>();

				JSONArray json = new JSONArray();

				try {
					List<FileItem> items = uploadHandler.parseRequest( request );
					for ( FileItem item : items ) {
						if ( !item.isFormField() ) {
							File file = new File( getFilePath( request ),
									item.getName() );
							item.write( file );
							JSONObject jsono = new JSONObject();
							jsono.put( "name", item.getName() );
							jsono.put( "size", item.getSize() );
							jsono.put( "url", getServletPath() + "?getfile="
												+ item.getName() );
							jsono.put( "thumbnail_url",
									getServletPath() + "?getthumb="
											+ item.getName() );
							jsono.put( "delete_url",
									getServletPath() + "?delfile="
											+ item.getName() );
							jsono.put( "delete_type", "GET" );
							json.add( jsono );

							logger.debug( "Adding image location  [thread_id=" + Thread.currentThread()
													.getId()
											+ "] : "
											+ file );

							fileUploaded.add( item );
						}
					}

					if ( fileUploaded != null && fileUploaded.size() > 0 ) {
						addImage( request, fileUploaded );
					}
				}
				catch ( FileUploadException e ) {
					logger.fatal( "FileUploadException: " + "Can't get file uploaded" );
					throw new RuntimeException( e );
				}
				catch ( Exception e ) {
					logger.fatal( "FileUploadException: " + "Unknow error, Can't get file uploaded" );
					throw new RuntimeException( e );
				}
				finally {

					logger.debug( ">>> doPost image upload end " );

					writer.write( json.toString() );
					writer.close();
				}
			}
		}
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
		System.out.println( "mimetype: " + mimetype );
		return mimetype;
	}

	private String getSuffix( String filename ) {
		String suffix = "";
		int pos = filename.lastIndexOf( '.' );
		if ( pos > 0 && pos < filename.length() - 1 ) {
			suffix = filename.substring( pos + 1 );
		}
		System.out.println( "suffix: " + suffix );
		return suffix;
	}

	protected File getFilePath( HttpServletRequest request ) {
		File result = fileUploadPath;
		HttpSession session = request.getSession();

		String uuid = getUUID( session );

		UploadImageBean bean = (UploadImageBean) session.getAttribute( uuid );

		if ( bean != null ) {

			result = getFilePath( fileUploadPath, bean );

		} else {
			logger.error( "Error bean and uuid of file is not exist: uuit=" + uuid
							+ ", bean="
							+ bean );
		}

		return result;
	}

	public static File getFilePath( File rootPath, UploadImageBean bean ) {
		File result = null;

		if ( bean != null ) {
			boolean dirExist = true;
			File file = new File( rootPath, getImageUploadPath( bean ) );
			if ( !file.exists() ) {
				dirExist = file.mkdirs();
			}
			if ( dirExist ) {
				result = file;
			}
		} else {
			logger.error( "Error bean and uuid of file is not exist, bean=" + bean );
		}

		return result;
	}

	protected static String getImageUploadPath( UploadImageBean bean ) {
		try {
			Date date = DateUtils.formatString( bean.getDateCreated() );

			Calendar c = Calendar.getInstance( Localizer.getCurrentLocale() );
			c.setTime( date );

			int year = c.get( Calendar.YEAR );
			int month = c.get( Calendar.MONTH );

			return String.valueOf( year ) + "/"
					+ String.valueOf( month )
					+ "/"
					+ bean.getIdentifier();
		}
		catch ( Exception e ) {
			e.printStackTrace();
		}
		return "";
	}
}
