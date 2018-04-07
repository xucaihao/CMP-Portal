package com.cmp.portal.cloud.model.res;

public class ResCloud {

    private CloudEntity cloud;

    public ResCloud() {
    }

    public ResCloud(CloudEntity cloud) {
        this.cloud = cloud;
    }

    public CloudEntity getCloud() {
        return cloud;
    }

    public void setCloud(CloudEntity cloud) {
        this.cloud = cloud;
    }
}
