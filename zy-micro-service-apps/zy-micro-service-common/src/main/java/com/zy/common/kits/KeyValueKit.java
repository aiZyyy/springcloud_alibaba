package com.zy.common.kits;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.zy.common.utils.AssertUtil;
import com.zy.common.utils.WebUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * 键值对处理类
 *
 * @since 2017/5/27
 */
public class KeyValueKit extends HashMap<String, Object> {
    public static final String STATUS = "status";
    public static final String MSG = "msg";
    public static final String DATA = "data";
    public static final ThreadLocal<String> cacheResult = ThreadLocal.withInitial(() -> null);
    private static final long serialVersionUID = 1L;

    private static KeyValueKit EMPTY = new KeyValueKit();

    /**
     * 分页数据
     *
     * @return {@link KeyValueKit}
     */
    public static KeyValueKit page(String pageNum, String pageSize) {
        return new KeyValueKit().add("pageNum", pageNum).add("pageSize", pageSize);
    }

    /**
     * 空数据
     *
     * @return {@link KeyValueKit}
     */
    public static KeyValueKit empty() {
        return (KeyValueKit) EMPTY.clone();
    }

    /**
     * 正确返回
     *
     * @return {@link KeyValueKit}
     */
    public static KeyValueKit ok() {
        return rd(HttpStatus.OK.value());
    }

    /**
     * 正确返回
     *
     * @param msg 返回消息
     * @return {@link KeyValueKit}
     */
    public static KeyValueKit ok(String msg) {
        return rd(HttpStatus.OK.value(), msg);
    }

    /**
     * 正确返回
     *
     * @param data 数据
     * @return {@link KeyValueKit}
     */
    public static KeyValueKit ok(Object data) {
        return rd(HttpStatus.OK.value(), data);
    }

    /**
     * 正确返回
     *
     * @param msg  返回消息
     * @param data 数据
     * @return {@link KeyValueKit}
     */
    public static KeyValueKit ok(String msg, Object data) {
        return rd(HttpStatus.OK.value(), msg, data);
    }

    /**
     * 拒绝访问
     *
     * @param msg 提示消息
     * @return {@link KeyValueKit}
     */
    public static KeyValueKit bad_request(String msg) {
        return rd(HttpStatus.BAD_REQUEST.value(), msg);
    }

    /**
     * 拒绝访问
     *
     * @param msg  提示消息
     * @param data 错误数据
     * @return {@link KeyValueKit}
     */
    public static KeyValueKit bad_request(String msg, Object data) {
        return rd(HttpStatus.BAD_REQUEST.value(), msg, data);
    }

    /**
     * 拒绝访问
     *
     * @param msg 提示消息
     * @return {@link KeyValueKit}
     */
    public static KeyValueKit forbidden(String msg) {
        return rd(HttpStatus.FORBIDDEN.value(), msg);
    }

    /**
     * 拒绝访问
     *
     * @param msg  提示消息
     * @param data 错误数据
     * @return {@link KeyValueKit}
     */
    public static KeyValueKit forbidden(String msg, Object data) {
        return rd(HttpStatus.FORBIDDEN.value(), msg, data);
    }

    /**
     * 数据返回
     *
     * @param status 状态
     * @return {@link KeyValueKit}
     */
    public static KeyValueKit rd(int status) {
        return new KeyValueKit(STATUS, status);
    }

    /**
     * 结果返回
     *
     * @param result  判断结果
     * @param success 成功提示
     * @param error   失败提示
     * @return {@link KeyValueKit}
     */
    public static KeyValueKit result(boolean result, String success, String error) {
        return result ? ok(success) : forbidden(error);
    }

    /**
     * 数据返回
     *
     * @param msg 返回消息
     * @return {@link KeyValueKit}
     */
    public static KeyValueKit rd(int status, String msg) {
        WebUtil.getResponse().setStatus(status);
        return new KeyValueKit(STATUS, status).add(MSG, msg);
    }

    /**
     * 数据返回
     *
     * @param status 状态
     * @param data   数据
     * @return {@link KeyValueKit}
     */
    public static KeyValueKit rd(int status, Object data) {
        WebUtil.getResponse().setStatus(status);
        return new KeyValueKit(STATUS, status).add(DATA, data);
    }

    /**
     * 数据返回
     *
     * @param msg  返回消息
     * @param data 数据
     * @return {@link KeyValueKit}
     */
    public static KeyValueKit rd(int status, String msg, Object data) {
        WebUtil.getResponse().setStatus(status);
        return new KeyValueKit(STATUS, status).add(MSG, msg).add(DATA, data);
    }

    public KeyValueKit() {
    }

    public KeyValueKit(String key, Object value) {
        put(key, value);
    }

    public KeyValueKit add(String key, Object value) {
        put(key, value);
        return this;
    }

    public KeyValueKit addExtraData(Map<? extends String, ?> map) {
        getData().putAll(map);
        return this;
    }

    public KeyValueKit addExtraData(String key, Object value) {
        getData().put(key, value);
        return this;
    }

    public KeyValueKit del(String key) {
        remove(key);
        return this;
    }

    public <T> T getAttr(String key) {
        return (T) get(key);
    }

    /**
     * @param list
     * @return
     */
    public KeyValueKit page(Collection<?> list) {
        AssertUtil.require(list instanceof Page, "分页结果无法转换为Page对象 请检查是否为Mapper返回的原始对象(服务器内部错误)");
        return page(list, (Page) list);
    }

    public KeyValueKit page(Collection<?> list, Page page) {
        AssertUtil.requireNonNull(list, "分页结果不得为Null(服务器内部错误)");
        KeyValueKit kv = new KeyValueKit();
        kv.put("list", list);
        kv.setPage(page);
        put(DATA, kv);
        return this;
    }

    private void setPage(Page page) {
        AssertUtil.requireNonNull(page, "分页信息不得为Null(服务器内部错误)");
        put("num", page.getPageNum());
        put("size", page.getPageSize());
        put("count", page.getTotal());
    }

    public String getMsg() {
        return getAttr(MSG);
    }

    public KeyValueKit getData() {
        return Optional.ofNullable((KeyValueKit) getAttr(DATA)).orElseGet(() -> add(DATA, new KeyValueKit()).getAttr(DATA));
    }

    public <T extends Map> T toMap() {
        return (T) this;
    }

    public <T extends MultiValueMap> T toMultiValueMap() {
        LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        forEach((s, o) -> {
            LinkedList<Object> list = new LinkedList<>();
            list.add(o);
            map.put(s, list);
        });
        return (T) map;
    }

    public void write(HttpServletResponse response) throws IOException {
        // 设置编码 防止乱码
        response.setHeader("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE);
        cacheResult.set(toJson());
        response.getOutputStream().write(cacheResult.get().getBytes(StandardCharsets.UTF_8));
    }

    public KeyValueKit cache() {
        cacheResult.set(toJson());
        return this;
    }

    public String toJson() {
        return JSON.toJSONString(this);
    }

    @Override
    public String toString() {
        return toJson();
    }
}
