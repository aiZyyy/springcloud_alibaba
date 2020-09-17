package com.zy.apps.common.utils;

import cn.hutool.core.date.DateUtil;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.zy.apps.common.domain.entity.ObjectEnum;
import com.zy.apps.common.domain.vo.Result;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.*;

/**
 * @author ZY
 * @date 2020/9/2 9:47
 * @Description:
 * @Version 1.0
 */
@Component
public class ReHttpUtil {

    final static Base64.Encoder encoder = Base64.getEncoder();

    private static final String POST_METHOD = "POST";
    private static final String GET_METHOD = "GET";
    private static final String PUT_METHOD = "PUT";
    private static final String DELETE_METHOD = "DELETE";

    @Value("${oldRtu.address}")
    private String address;
    private static String initAddress;

    @Value("${oldRtu.username}")
    private String username;
    private static String initUsername;

    @Value("${oldRtu.password}")
    private String password;
    private static String initPassword;

    @PostConstruct
    private void init() {
        initAddress = address;
        initUsername = username;
        initPassword = password;
    }

    public static String[] UserAgent = {
            "Mozilla/5.0 (Linux; U; Android 2.2; en-us; Nexus One Build/FRF91) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.2",
            "Mozilla/5.0 (iPad; U; CPU OS 3_2_2 like Mac OS X; en-us) AppleWebKit/531.21.10 (KHTML, like Gecko) Version/4.0.4 Mobile/7B500 Safari/531.21.11",
            "Mozilla/5.0 (SymbianOS/9.4; Series60/5.0 NokiaN97-1/20.0.019; Profile/MIDP-2.1 Configuration/CLDC-1.1) AppleWebKit/525 (KHTML, like Gecko) BrowserNG/7.1.18121",
            //http://blog.csdn.net/yjflinchong
            "Nokia5700AP23.01/SymbianOS/9.1 Series60/3.0",
            "UCWEB7.0.2.37/28/998",
            "NOKIA5700/UCWEB7.0.2.37/28/977",
            "Openwave/UCWEB7.0.2.37/28/978",
            "Mozilla/4.0 (compatible; MSIE 6.0; ) Opera/UCWEB7.0.2.37/28/989"
    };

    public static String getDataWithoutLogin(String requestAddress, Map<String, String> requestParams) {
        String result = "";
        URL url;
        try {
            url = new URL(requestAddress);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setUseCaches(false);
            urlConnection.setInstanceFollowRedirects(false);
            urlConnection.setConnectTimeout(5000);
            urlConnection.setReadTimeout(5000);
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            urlConnection.setRequestProperty("Pragma", "no-cache");
            urlConnection.setRequestProperty("Cache-Control", "no-cache");
            int temp = Integer.parseInt(Math.round(Math.random() * 7) + "");
            urlConnection.setRequestProperty(
                    "User-Agent",
                    UserAgent[temp]);  // 模拟手机系统
            //只接受text/html类型，当然也可以接受图片,pdf,*/*任意，就是tomcat/conf/web里面定义那些
            urlConnection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
            urlConnection.connect();
            DataOutputStream outputStream;
            String content = "";
            if (requestParams != null) {
                outputStream = new DataOutputStream(urlConnection
                        .getOutputStream());
                for (String key : requestParams.keySet()) {
                    if (StringUtils.isNotBlank(requestParams.get(key))) {
                        content += key;
                        content += "=";
                        content += URLEncoder.encode(requestParams.get(key), "UTF-8");
                        content += "&";
                    }
                }
                outputStream.writeBytes(content);
                outputStream.flush();
                outputStream.close();
            }
            if (urlConnection.getResponseCode() == 200) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "utf-8"));
                String line;
                while ((line = reader.readLine()) != null) {
                    result += line;
                }
                reader.close();
            } else {
                result = null;
            }
            urlConnection.disconnect();

        } catch (Exception e) {
            result = null;
        }
        return result;
    }

    /**
     * 需要登入的POST接口调用
     *
     * @param loginAddress
     * @param loginNvps
     * @param requestAddress
     * @param requestNvps
     * @return
     */
    public static Result<String> postDataWithLogin(String loginAddress, List<NameValuePair> loginNvps, String requestAddress, List<NameValuePair> requestNvps) {
        Result<String> result = new Result<>();
        result.success("调用成功!");
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            if (login(loginAddress, loginNvps, result, httpclient)) return result;
            //登录完成后请求数据
            HttpPost requestPost = new HttpPost(requestAddress);
            requestPost.setEntity(new UrlEncodedFormEntity(requestNvps, "UTF-8"));
            CloseableHttpResponse requestResponse = httpclient.execute(requestPost);
            return getStringResult(result, requestResponse);
        } catch (Exception e) {
            result.error500("工具类获取异常!");
            return result;
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 需要登入的GET接口调用
     *
     * @param loginAddress
     * @param loginNvps
     * @param requestAddress
     * @return
     */
    public static Result<String> getDataWithLogin(String loginAddress, List<NameValuePair> loginNvps, String requestAddress) {
        Result<String> result = new Result<>();
        result.success("调用成功!");
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            if (login(loginAddress, loginNvps, result, httpclient)) return result;
            //登录完成后请求数据
            if (StringUtils.isBlank(requestAddress)) {
                result.error500("get请求地址转换异常!");
                return result;
            }
            HttpGet requestGet = new HttpGet(requestAddress);
            CloseableHttpResponse requestResponse = httpclient.execute(requestGet);
            return getStringResult(result, requestResponse);
        } catch (Exception e) {
            result.error500("工具类获取异常!");
            return result;
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取旧系统返回结果
     *
     * @param result
     * @param requestResponse
     * @return
     * @throws IOException
     */
    private static Result<String> getStringResult(Result<String> result, CloseableHttpResponse requestResponse) throws IOException {
        try {
            HttpEntity requestEntity = requestResponse.getEntity();
            int requestStatusCode = requestResponse.getStatusLine().getStatusCode();
            if (200 == requestStatusCode) {
                result.setResult(EntityUtils.toString(requestEntity, "UTF-8"));
                return result;

            } else {
                result.setCode(requestStatusCode);
                result.setMessage(EntityUtils.toString(requestEntity, "UTF-8"));
                return result;
            }
        } finally {
            requestResponse.close();
        }
    }

    /**
     * 登入旧系统
     *
     * @param loginAddress
     * @param loginNvps
     * @param result
     * @param httpclient
     * @return
     * @throws IOException
     */
    private static boolean login(String loginAddress, List<NameValuePair> loginNvps, Result<String> result, CloseableHttpClient httpclient) throws IOException {
        HttpPost loginPost = new HttpPost(loginAddress);
        loginPost.setEntity(new UrlEncodedFormEntity(loginNvps, "UTF-8"));
        //登录
        CloseableHttpResponse loginResponse = httpclient.execute(loginPost);
        try {
            int loginStatusCode = loginResponse.getStatusLine().getStatusCode();
            //如果请求失败;
            if (200 != loginStatusCode) {
                result.error500("登入旧前置失败!");
                return true;
            }
        } finally {
            loginResponse.close();
        }
        return false;
    }

    /**
     * 获取get请求的参数信息
     *
     * @param request
     * @return
     */
    public static Map<String, String> getParameterMap(HttpServletRequest request) {
        Map<String, String> map = new HashMap<>();
        Enumeration<String> paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = paramNames.nextElement();
            String[] paramValues = request.getParameterValues(paramName);
            if (paramValues.length == 1) {
                String paramValue = paramValues[0];
                if (paramValue.length() != 0) {
                    map.put(paramName, paramValue);
                }
            }
        }
        return map;
    }

    /**
     * @param url 接口地址(无参数)
     * @param map 拼接参数集合
     * @Description get请求URL拼接参数 & URL编码
     */
    public static String getAppendUrl(String url, Map<String, String> map) {
        StringBuffer buffer = new StringBuffer();
        if (map != null && !map.isEmpty()) {
            Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();
                if (StringUtils.isEmpty(buffer.toString())) {
                    buffer.append("?");
                } else {
                    buffer.append("&");
                }
                try {
                    buffer.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue(), "utf-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }
        return url + buffer.toString();
    }

    /**
     * 封装登入信息
     *
     * @return
     */
    public static List<NameValuePair> getLoginPairs() {
        List<NameValuePair> loginNvps = new ArrayList<>();
        byte[] textByte = new byte[0];
        try {
            textByte = initPassword.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String pwd = encoder.encodeToString(textByte);
        loginNvps.add(new BasicNameValuePair("name", initUsername));
        loginNvps.add(new BasicNameValuePair("pwd", pwd));
        return loginNvps;
    }

    /**
     * 获取登入地址
     *
     * @return
     */
    private static String getLoginAddress() {
        StringBuffer stringBuffer = new StringBuffer(initAddress);
        stringBuffer.append("/irtu/login.hd");
        return stringBuffer.toString();
    }

    /**
     * 获取请求地址
     *
     * @param questAddress
     * @return
     */
    private static String getRequestAddress(String questAddress) {
        StringBuffer stringBuffer = new StringBuffer(initAddress);
        stringBuffer.append(questAddress);
        return stringBuffer.toString();
    }

    /**
     * post请求数据
     *
     * @param queryMap
     * @param request
     * @return
     */
    public static Result<String> postDateResult(Map<String, String> queryMap, HttpServletRequest request) {
        //1.获取地址
        String loginAddress = getLoginAddress();
        String finAddress = getRequestAddress(request.getRequestURI());
        //2.封装登入信息
        List<NameValuePair> loginNvps = getLoginPairs();
        //3.封装请求信息
        List<NameValuePair> requestNvps = new ArrayList<>();
        queryMap.entrySet().forEach(a -> requestNvps.add(new BasicNameValuePair(a.getKey(), a.getValue())));
        return postDataWithLogin(loginAddress, loginNvps, finAddress, requestNvps);
    }

    /**
     * get请求数据
     *
     * @param request
     * @return
     */
    public static Result<String> getDateResult(HttpServletRequest request) {
        Map<String, String> parameterMap = getParameterMap(request);
        //装换分页参数
        if (StringUtils.isNotBlank(request.getParameter("pageSize"))) {
            parameterMap.put("rows", request.getParameter("pageSize"));
        }
        if (StringUtils.isNotBlank(request.getParameter("pageNo"))) {
            parameterMap.put("page", request.getParameter("pageNo"));
        }
        //1.获取地址
        String loginAddress = getLoginAddress();
        String questAddress = getRequestAddress(request.getRequestURI());
        //2.封装登入信息
        List<NameValuePair> loginNvps = getLoginPairs();
        //3.封装请求信息
        String finAddress = ReHttpUtil.getAppendUrl(questAddress, parameterMap);
        return getDataWithLogin(loginAddress, loginNvps, finAddress);
    }

    /**
     * 设置错误返回值
     */
    public static void setResultFasle(Result<?> result, Result<?> dataResult) {
        result.setSuccess(false);
        result.setCode(dataResult.getCode());
        result.setMessage(dataResult.getMessage());
    }

    /**
     * 判断调用get或post
     *
     * @param queryMap
     * @param request
     * @return
     */
    public static Result<String> dateResult(Map<String, String> queryMap, HttpServletRequest request) {
        Result<String> result;
        String method = request.getMethod();
        switch (method) {
            case POST_METHOD:
                result = postDateResult(queryMap, request);
                break;
            case PUT_METHOD:
                result = postDateResult(queryMap, request);
                break;
            case GET_METHOD:
                result = getDateResult(request);
                break;
            case DELETE_METHOD:
                result = getDateResult(request);
                break;
            default:
                result = postDateResult(queryMap, request);
        }
        return result;
    }

    /**
     * String转map
     *
     * @param str_json
     * @return
     */
    public static Map<Integer, Object> json2map(String str_json) {
        Map<Integer, Object> res = null;
        try {
            Gson gson = new Gson();
            res = gson.fromJson(str_json, new TypeToken<Map<Integer, Object>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
        }
        return res;
    }

    /**
     * object转list<T>
     *
     * @param obj
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> castList(Object obj, Class<T> clazz) {
        List<T> result = new ArrayList<T>();
        if (obj instanceof List<?>) {
            for (Object o : (List<?>) obj) {
                result.add(clazz.cast(o));
            }
            return result;
        }
        return null;
    }

    /**
     * 转换文件大小
     */
    public static String formatFileSize(long fileS) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        String wrongSize = "0B";
        if (fileS == 0) {
            return wrongSize;
        }
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "KB";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "MB";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "GB";
        }
        return fileSizeString;
    }


    /**
     * 根据请求反射到一个java对象
     *
     * @param obj     需要设置值的对象
     * @param request 请求参数对象
     * @return
     * @throws Exception
     */
    public static Object setObjectFieldValue(Object obj, HttpServletRequest request) throws Exception {
        return getFieldByParamName(obj, request);
    }

    /**
     * 根据请求参数反射到一个java对象
     *
     * @param obj      需要设置值的对象
     * @param paramObj 请求参数对象
     * @return
     * @throws Exception
     */
    private static Object getFieldByParamName(Object obj, Object paramObj) throws Exception {
        if (null == obj) {
            Assert.notNull(obj, "填充的对象不能为空");
        }

        Field[] fs = obj.getClass().getDeclaredFields();
        Field[] superFs = obj.getClass().getSuperclass().getDeclaredFields();
        List<Field> list = new ArrayList<Field>();
        list.addAll(Arrays.asList(fs));
        list.addAll(Arrays.asList(superFs));
        Class<?> classes = null;                            //子类类型
        String fieldName = null;                            //请求的参数名
        String fieldValue = null;                           //请求的参数值
        Object tempChildObj = null;                         //嵌套的子对象
        StringBuilder setMetodName = new StringBuilder(50); //调用设置值的方法
        Date dateTime = null;
        Map<String, Object> paramMap;
        HttpServletRequest request;
        for (Field field : list) {
            field.setAccessible(true);
            fieldName = field.getName();

            //定义请求参数值的映射方法
            setMetodName.delete(0, setMetodName.length());
            setMetodName.append("set").append(fieldName.substring(0, 1).toUpperCase()).append(fieldName.substring(1));

            classes = field.getType();
            ObjectEnum oe = ObjectEnum.findByClass(classes);
            if (null != oe) {
                //获取请求的参数值
                if (paramObj instanceof Map) {
                    paramMap = (Map<String, Object>) paramObj;
                    fieldValue = paramMap.get(fieldName) == null ? null : paramMap.get(fieldName).toString();
                } else if (paramObj instanceof HttpServletRequest) {
                    request = (HttpServletRequest) paramObj;
                    fieldValue = request.getParameter(fieldName);
                }

                if (StringUtils.isBlank(fieldValue) || "null".equals(fieldValue)) {
                    continue;
                }
                setFieldValue(obj, oe, setMetodName.toString(), fieldValue);
            } else {
                //自定义类型
                tempChildObj = getFieldByParamName(field.getType().newInstance(), paramObj);
                obj.getClass().getDeclaredMethod(setMetodName.toString(), classes).invoke(obj, tempChildObj);
            }
        }
        return obj;
    }

    /**
     * 设置对象单个字段属性
     *
     * @param obj          原始对象
     * @param objectEnum   对象枚举
     * @param setMetodName 字段set方法
     * @param fieldValue   字段值
     * @throws Exception
     */
    private static void setFieldValue(Object obj, ObjectEnum objectEnum, String setMetodName, String fieldValue) throws Exception {
        switch (objectEnum) {
            case String:
                obj.getClass().getDeclaredMethod(setMetodName, String.class).
                        invoke(obj, fieldValue);
                break;
            case Long:
                obj.getClass().getDeclaredMethod(setMetodName, Long.class).
                        invoke(obj, Long.parseLong(fieldValue));
                break;
            case Integer:
                obj.getClass().getDeclaredMethod(setMetodName, Integer.class).
                        invoke(obj, Integer.parseInt(fieldValue));
                break;
            case Boolean:
                obj.getClass().getDeclaredMethod(setMetodName, Boolean.class).
                        invoke(obj, Boolean.parseBoolean(fieldValue));
                break;
            case Short:
                obj.getClass().getDeclaredMethod(setMetodName, Short.class).
                        invoke(obj, Short.parseShort(fieldValue));
                break;
            case Double:
                obj.getClass().getDeclaredMethod(setMetodName, Double.class).
                        invoke(obj, Double.parseDouble(fieldValue));
                break;

            case Float:
                obj.getClass().getDeclaredMethod(setMetodName, Float.class).
                        invoke(obj, Float.parseFloat(fieldValue));
                break;

            case Date:
                int dateStrLength = fieldValue.length();
                Date dateTime = null;
                switch (dateStrLength) {
                    case 19://"yyyy-MM-dd HH:mm:ss"//19
                        dateTime = DateUtil.parse(fieldValue, "yyyy-MM-dd HH:mm:ss");
                        break;

                    case 10://"yyyy-MM-dd"//10
                        dateTime = DateUtil.parse(fieldValue, "yyyy-MM-dd");
                        break;

                    case 8://"HH:mm:ss"//8
                        dateTime = DateUtil.parse(fieldValue, "HH:mm:ss");
                        break;

                    default:
                        break;
                }
                if (null != dateTime) {
                    obj.getClass().getDeclaredMethod(setMetodName, Date.class).invoke(obj, dateTime);
                }
                break;
            case List:
                break;
            default:
                break;
        }
    }
}
