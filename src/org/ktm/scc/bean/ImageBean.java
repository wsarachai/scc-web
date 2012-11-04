package org.ktm.scc.bean;

import org.ktm.domain.KTMEntity;
import org.ktm.domain.gallery.Image;
import org.ktm.web.bean.FormBean;
import org.ktm.web.tags.Functions;

public class ImageBean extends FormBean {
    private String path;
    private Long   size;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        if (!Functions.isEmpty(path)) {
            int idx = path.lastIndexOf("/");
            return path.substring(idx + 1);
        }
        return "";
    }

    @Override
    public void syncToEntity(KTMEntity entity) {
        if (entity != null && entity instanceof Image) {
            super.syncToEntity(entity);

            Image image = (Image) entity;
            image.setPath(this.getPath());
            image.setSize(size);
        }
    }

    @Override
    public void loadToForm(KTMEntity entity) {
        if (entity != null && entity instanceof Image) {
            super.loadToForm(entity);

            Image image = (Image) entity;
            this.setPath(image.getPath());
            this.setSize(image.getSize());
        }
    }

    public String getSize() {
        return Functions.humanReadableByteCount(size, true);
    }

    public void setSize(long size) {
        this.size = size;
    }

}
