package com.cmp.portal.cloud.model.res;

public class CloudAdapterEntity {

    private String id;

    private String adapterName;

    private String adapterProtocol;

    private String adapterIp;

    private String adapterPort;

    private String description;

    private String cloudType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAdapterName() {
        return adapterName;
    }

    public void setAdapterName(String adapterName) {
        this.adapterName = adapterName;
    }

    public String getAdapterProtocol() {
        return adapterProtocol;
    }

    public void setAdapterProtocol(String adapterProtocol) {
        this.adapterProtocol = adapterProtocol;
    }

    public String getAdapterIp() {
        return adapterIp;
    }

    public void setAdapterIp(String adapterIp) {
        this.adapterIp = adapterIp;
    }

    public String getAdapterPort() {
        return adapterPort;
    }

    public void setAdapterPort(String adapterPort) {
        this.adapterPort = adapterPort;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCloudType() {
        return cloudType;
    }

    public void setCloudType(String cloudType) {
        this.cloudType = cloudType;
    }
}
