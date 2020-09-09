package com.zy.apps.common.convert.resolver;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zy.apps.common.convert.annotation.PageAttr;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * Created with IntelliJ IDEA
 *
 * @author ZY
 * Created on 2017/6/12 19:40.
 */
@Component
public class PageArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return Page.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        int num = 1;
        int size = 20;
        try {
            num = Integer.parseInt(webRequest.getParameter("pageNum"));
        } catch (Exception ignored) {
        }
        try {
            size = Integer.parseInt(webRequest.getParameter("pageSize"));
        } catch (Exception ignored) {
        }
        Page<Object> page = PageHelper.startPage(num, size);
        PageAttr attr = parameter.getParameterAnnotation(PageAttr.class);
        if (attr != null) {
            page.setOrderBy(attr.order());
        }
        return page;
    }

}
