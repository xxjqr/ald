package com.xxjqr.aldfq.po;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ServeItem {
    @JsonIgnore
    private Integer index;
    private String server;
    private Integer server_port;
    private String password;
    private String method;
    private final String remarks = "";
    private final Boolean auth = false;
    private final Integer timeout = 5;
    public ServeItem(String server,Integer server_port,String password,String method){
        this.method = method;
        this.password = password;
        this.server = server;
        this.server_port = server_port;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getServer() {
        return server;
    }
    public void setServer(String server) {
        this.server = server;
    }
    public Integer getServer_port() {
        return server_port;
    }
    public void setServer_port(Integer server_port) {
        this.server_port = server_port;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getMethod() {
        return method;
    }
    public void setMethod(String method) {
        this.method = method;
    }
    public String getRemarks() {
        return remarks;
    }
    public Boolean getAuth() {
        return auth;
    }
    public Integer getTimeout() {
        return timeout;
    }


}

