package com.cmp.portal.cloud.model.res;

import java.util.List;

public class ResClouds {

    private List<CloudEntity> clouds;

    public ResClouds() {
    }

    public ResClouds(List<CloudEntity> clouds) {
        this.clouds = clouds;
    }

    public List<CloudEntity> getClouds() {
        return clouds;
    }

    public void setClouds(List<CloudEntity> clouds) {
        this.clouds = clouds;
    }
}

