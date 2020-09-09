package com.zy.base.api.gateway.factory;

import com.zy.base.api.gateway.filter.AuthGatewayFilter;
import com.zy.base.api.gateway.services.AuthBodyServices;
import com.zy.base.api.gateway.services.CheckSignServices;
import com.zy.base.api.gateway.services.EncapsulationServices;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

/**
 * @Author: ZY
 * @Date: 2019/10/16 18:06
 * @Version 1.0
 * @Description:
 */
@Component
public class AuthGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {

    private final CheckSignServices checkSignServices;

    private final EncapsulationServices encapsulationServices;

    private final AuthBodyServices authBodyServices;

    /**
     * constructor
     */
    public AuthGatewayFilterFactory(CheckSignServices checkSignServices, AuthBodyServices authBodyServices, EncapsulationServices encapsulationServices) {
        this.checkSignServices = checkSignServices;
        this.authBodyServices = authBodyServices;
        this.encapsulationServices = encapsulationServices;
    }


    @Override
    public GatewayFilter apply(Object config) {
        return new AuthGatewayFilter(checkSignServices, authBodyServices, encapsulationServices);
    }

}
