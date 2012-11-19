package org.ktm.dao.image_upload;

import org.ktm.dao.Dao;
import org.ktm.domain.upload.ImageUpload;

public interface ImageUploadDao extends Dao {

	public ImageUpload findByIdentifier( String identifier );

}
