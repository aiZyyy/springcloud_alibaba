package com.zy.base.routeservice.service;

import com.zy.base.routeservice.domain.form.RouteSkipAddForm;
import com.zy.base.routeservice.domain.form.RouteSkipDelForm;

/**
 * @Author: ZY
 * @Date: 2019/10/15 17:23
 * @Version 1.0
 * @Description:
 */

public interface SkipRouteService {

    void skipRouteAdd(RouteSkipAddForm routeSkipForm);

    void skipRouteDel(RouteSkipDelForm routeSkipDelForm);

    void skipRouteRefresh();

}
