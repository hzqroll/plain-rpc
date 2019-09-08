package com.roll.component.plain.register.api;

/**
 * 服务注册对象
 *
 * @author roll
 * created on 2019-09-05 16:30
 */
public class Register {
    /**
     * 应用名称
     */
    private String appName;

    /**
     * 类名
     */
    private String serviceName;

    /**
     * 版本号,默认1.0.0
     */
    private String serviceVersion = "1.0.0";

    /**
     * 地址
     */
    private String address;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceVersion() {
        return serviceVersion;
    }

    public void setServiceVersion(String serviceVersion) {
        this.serviceVersion = serviceVersion;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Register{" +
                "appName='" + appName + '\'' +
                ", serviceName='" + serviceName + '\'' +
                ", serviceVersion='" + serviceVersion + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
