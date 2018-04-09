package com.cmp.portal.cloud.model.res;

import java.util.List;

public class ResCloudAdapters {

    private List<CloudAdapterEntity> cloudAdapters;

    public ResCloudAdapters() {
    }

    public ResCloudAdapters(List<CloudAdapterEntity> cloudAdapters) {
        this.cloudAdapters = cloudAdapters;
    }

    public List<CloudAdapterEntity> getCloudAdapters() {
        return cloudAdapters;
    }

    public void setCloudAdapters(List<CloudAdapterEntity> cloudAdapters) {
        this.cloudAdapters = cloudAdapters;
    }
}
