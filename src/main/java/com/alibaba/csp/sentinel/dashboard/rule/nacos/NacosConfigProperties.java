package com.alibaba.csp.sentinel.dashboard.rule.nacos;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * NacosConfigProperties
 *
 * @author cxd 2020-05-26
 * @version 1.0
 */
@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "nacos.server")
public class NacosConfigProperties{

    private String serverAddr;

    private String namespace;

    private String groupId;

    public String getServerAddr(){
        return serverAddr;
    }

    public void setServerAddr(String serverAddr){
        this.serverAddr = serverAddr;
    }

    public String getNamespace(){
        return namespace;
    }

    public void setNamespace(String namespace){
        this.namespace = namespace;
    }

    public String getGroupId(){
        return groupId;
    }

    public void setGroupId(String groupId){
        this.groupId = groupId;
    }

    @Override
    public String toString(){
        return "NacosConfigProperties [serverAddr=" + serverAddr + ", namespace=" + namespace + ", groupId=" + groupId + "]";
    }

}