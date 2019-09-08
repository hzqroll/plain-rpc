package com.roll.component.plain.register.zk;

import com.roll.component.plain.register.api.Register;
import com.roll.component.plain.register.api.RegisterService;
import com.roll.component.plain.rpc.common.register.RegisterConstant;
import org.I0Itec.zkclient.ZkClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * zk注册中心实现
 *
 * @author roll
 * created on 2019-09-05 15:27
 */
public class ZkRegisterService implements RegisterService {

    Logger logger = LoggerFactory.getLogger("zkLog");

    /**
     * zkClient客户端
     */
    private final ZkClient zkClient;

    public ZkRegisterService(String zkAddress) {
        this.zkClient = new ZkClient(zkAddress);
        // 新增根持久节点
        if (!zkClient.exists(RegisterConstant.SERVICE_ROOT)) {
            logger.info("zkClient create root. root path: {}.", RegisterConstant.SERVICE_ROOT);
            zkClient.createPersistent(RegisterConstant.SERVICE_ROOT);
        }
        logger.info("zkClient init. zkAddress: {}.", zkAddress);
    }


    public static void main(String[] args) {
        ZkRegisterService zkRegisterService = new ZkRegisterService("127.0.0.1:2181");
    }

    public boolean register(Register register) {
        try {
            if (!zkClient.exists(getAppPath(register))) {
                logger.info("zkClient init appPath, register: {}", register);
                zkClient.createPersistent(getAppPath(register));
            }

            zkClient.createEphemeral(getServicePath(register), register.getAddress());
            logger.info("zkClient register success, register: {}", register);
        } catch (Exception e) {
            logger.error("zkClient register failed, register: {}.", register, e);
            return false;
        }

        return true;
    }

    public boolean unRegister(Register register) {
        try {
            return zkClient.delete(getServicePath(register));
        } catch (Exception e) {
            logger.error("zkClient unRegister failed, register: {}.", register, e);
            return false;
        }
    }

    public String lookup(Register register) {
        try {
            if (!zkClient.exists(getServicePath(register))) {
                return null;
            }

            String address = zkClient.readData(getServicePath(register));
            logger.info("zkClient get address: {}", address);
            return address;
        } catch (Exception e) {
            logger.error("zkClient lookup failed, register: {}.", register, e);
        }
        return null;
    }

    /**
     * 获取注册全路径
     */
    private String getServicePath(Register register) {
        return getAppPath(register) + "/" + register.getServiceName() + register.getServiceVersion();
    }

    /**
     * 获取应用注册路径
     */
    private String getAppPath(Register register) {
        return RegisterConstant.SERVICE_ROOT + "/" + register.getAppName();
    }
}
