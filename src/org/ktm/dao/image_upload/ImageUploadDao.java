package org.ktm.dao.image_upload;

import java.util.Collection;
import org.ktm.dao.Dao;
import org.ktm.domain.upload.ImageUpload;

public interface ImageUploadDao extends Dao {

	public ImageUpload findByIdentifier( String identifier );

	public Collection<?> findByDivision( Integer division_id, int i );

	public long getCountByDivision( Integer division_id );

}
