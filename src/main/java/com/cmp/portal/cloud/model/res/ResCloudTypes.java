package com.cmp.portal.cloud.model.res;

import java.util.List;

public class ResCloudTypes {

    private List<CloudTypeEntity> cloudTypes;

    public ResCloudTypes() {
    }

    public ResCloudTypes(List<CloudTypeEntity> cloudTypes) {
        this.cloudTypes = cloudTypes;
    }

    public List<CloudTypeEntity> getCloudTypes() {
        return cloudTypes;
    }

    public void setCloudTypes(List<CloudTypeEntity> cloudTypes) {
        this.cloudTypes = cloudTypes;
    }
}
