package com.xxjqr.aldfq.po;

import com.xxjqr.aldfq.po.ServeItem;

import java.util.List;

public class JsonFile {
    private List<ServeItem> configs;
    private Integer index;
    private Boolean global; //全局模式 false == pac
    private Boolean enabled;    //是否使用
    private final Boolean useOnlinePac = false; //使用在线pac
    private final Boolean secureLocalPac = true; //保护本地pac
    private final Boolean autoCheckUpdate = false; //自动更新
    private final Integer localPort = 1080; //本地端口
    private final Boolean isDefault = false;    //是否开机启动

    public JsonFile(List<ServeItem> configs){
        this.configs= configs;
        this.index = 0;
        this.global = false;
        this.enabled = true;
    }

    public Boolean getDefault() {
        return isDefault;
    }

    public List<ServeItem> getConfigs() {
        return configs;
    }

    public void setConfigs(List<ServeItem> configs) {
        this.configs = configs;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Boolean getGlobal() {
        return global;
    }

    public void setGlobal(Boolean global) {
        this.global = global;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean getUseOnlinePac() {
        return useOnlinePac;
    }

    public Boolean getSecureLocalPac() {
        return secureLocalPac;
    }

    public Boolean getAutoCheckUpdate() {
        return autoCheckUpdate;
    }

    public Integer getLocalPort() {
        return localPort;
    }
}
