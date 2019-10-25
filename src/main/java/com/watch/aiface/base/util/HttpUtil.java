package com.watch.aiface.base.util;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.CharsetUtils;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HttpUtil {
    // private static final Logger logger =
    // LogManager.getLogger(HttpUtil.class);
    private static Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    /**
     * 发送get类型的表单上数据 传输数据格式是form
     *
     * @param url    请求地址
     * @param params 参数
     * @return
     */
    public static String get(String url, Map<String, Object> params) {
        logger.info("访问的地址是：" + url + "\n 传输参数是：" + map2Str(params));
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(initUrl(url, params));
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(2000).setConnectionRequestTimeout(1000)
                .setSocketTimeout(2000).build();
        httpGet.setConfig(requestConfig);
        httpGet.setHeader("Connection", "close");
        CloseableHttpResponse httpResponse = null;
        String resStr = null;
        try {
            httpResponse = httpClient.execute(httpGet);
            if (isOK(httpResponse.getStatusLine().getStatusCode())) {
                resStr = bodyFromResponse(httpResponse);
                logger.info("请求结果：" + resStr);
            } else {
                logger.info("网络请求失败，状态码是 " + httpResponse.getStatusLine().getStatusCode());
            }

        } catch (IOException e) {
            logger.info("网络请求失败：" + e);
        } finally {
            try {
                if (httpClient != null) {
                    httpClient.close();
                }
                if (httpResponse != null) {
                    httpResponse.close();
                }

            } catch (IOException e) {
                logger.info("网络请求关闭失败：" + e);
            }
        }
        return resStr;
    }

    /**
     * 发送post类型的表单数据 传输数据格式是form
     *
     * @param url    请求地址
     * @param params 参数
     * @param files  文件
     * @return
     */
    public static String post(String url, ConcurrentHashMap<String, Object> params, ConcurrentHashMap<String, Object> files) {
        // 请求中只有数据参数的时候
        if ((files == null) || (files.size() <= 0)) {
            return post(url, params);
            // 请求中包含文件的时候
        } else {
            return postFiles(url, params, files);
        }
    }

    /**
     * 发送post类型请求 传输数据格式是json
     *
     * @param url
     * @param params
     * @return
     */
    public static String postJson(String url, Map<String, Object> params) {
        logger.info("访问的地址是：" + url + "\n 传输参数是：" + JSON.toJSONString(params));
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(5000).setConnectionRequestTimeout(1000)
                .setSocketTimeout(5000).build();
        httpPost.setConfig(requestConfig);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");
        httpPost.setHeader("Connection", "close");

        CloseableHttpResponse httpResponse = null;
        String resStr = null;
        try {
            StringEntity entity = new StringEntity(JSON.toJSONString(params), "UTF-8");
            httpPost.setEntity(entity);
            httpResponse = httpClient.execute(httpPost);
            if (isOK(httpResponse.getStatusLine().getStatusCode())) {
                resStr = bodyFromResponse(httpResponse);
                logger.info("请求结果：" + resStr);
            } else {
                logger.info("网络请求失败，状态码是 " + httpResponse.getStatusLine().getStatusCode());
            }

        } catch (IOException e) {
            logger.info("网络请求失败：" + e);
        } finally {
            try {

                if (httpClient != null) {
                    httpClient.close();
                }
                if (httpResponse != null) {
                    httpResponse.close();
                }
            } catch (IOException e) {
                logger.info("网络请求关闭失败：" + e);
            }
        }
        return resStr;
    }

    /**
     * 发送get类型获取图片信息并且绑定到filePath
     *
     * @param url      文件服务器地址
     * @param filePath 文件本地存储路径
     * @return
     */
    public static boolean downloadFile(String url, String filePath) {
        logger.info("访问的地址是：" + url + "\n 图片存储地址是：" + filePath);
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("Connection", "close");
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(5000).setConnectionRequestTimeout(1000)
                .setSocketTimeout(5000).build();
        httpGet.setConfig(requestConfig);
        CloseableHttpResponse httpResponse = null;
        boolean isDownload = false;
        try {
            httpResponse = httpClient.execute(httpGet);
            if (isOK(httpResponse.getStatusLine().getStatusCode())) {
                HttpEntity httpEntity = httpResponse.getEntity();
                long contentLength = httpEntity.getContentLength();
                InputStream is = httpEntity.getContent();
                // 根据InputStream 下载文件
                ByteArrayOutputStream output = new ByteArrayOutputStream();
                byte[] buffer = new byte[4096];
                int r = 0;
                while ((r = is.read(buffer)) > 0) {
                    output.write(buffer, 0, r);
                }
                FileOutputStream fos = new FileOutputStream(filePath);
                output.writeTo(fos);
                output.flush();
                output.close();
                fos.close();
                output.flush();
                output.close();
                EntityUtils.consume(httpEntity);
                logger.info("文件下载成功！");
                isDownload = true;
            } else {
                logger.info("网络请求失败，状态码是 " + httpResponse.getStatusLine().getStatusCode());
            }

        } catch (IOException e) {
            logger.info("网络请求失败：" + e);
        } finally {
            try {
                if (httpClient != null) {
                    httpClient.close();
                }
                if (httpClient != null) {
                    httpResponse.close();
                }
            } catch (IOException e) {
                logger.info("网络请求关闭失败：" + e);
            }
        }
        return isDownload;
    }

    /**
     * 发送post类型的表单数据
     *
     * @return
     */
    private static String post(String url, ConcurrentHashMap<String, Object> params) {
        logger.info("访问的地址是：" + url + "\n 传输参数是：" + JSON.toJSONString(params));
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        // httpPost.setHeader("Accept", "application/json");
        // httpPost.setHeader("Content-type", "application/json");
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(5000).setConnectionRequestTimeout(1000)
                .setSocketTimeout(5000).build();
        httpPost.setConfig(requestConfig);
        httpPost.setHeader("Connection", "close");
        httpPost.setHeader("applogin", new Date().getTime() + "");
        CloseableHttpResponse httpResponse = null;
        String resStr = null;
        try {
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            for (String key : params.keySet()) {
                nameValuePairs.add(new BasicNameValuePair(key, params.get(key).toString()));
            }

            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));

            httpResponse = httpClient.execute(httpPost);
            if (isOK(httpResponse.getStatusLine().getStatusCode())) {
                resStr = bodyFromResponse(httpResponse);
                logger.info("请求结果：" + resStr);
            } else {
                logger.info("网络请求失败，状态码是 " + httpResponse.getStatusLine().getStatusCode());
            }

        } catch (IOException e) {
            logger.info("网络请求失败：" + e);
        } finally {
            try {
                if (httpClient != null) {
                    httpClient.close();
                }
                if (httpResponse != null) {
                    httpResponse.close();
                }

            } catch (IOException e) {
                logger.info("网络请求关闭失败：" + e);
            }
        }
        return resStr;
    }

    /**
     * 发送post类型的表单数据
     *
     * @param params 传输参数
     * @param files  传输文件本地路径
     * @return
     */
    private static String postFiles(String url, Map<String, Object> params, ConcurrentHashMap<String, Object> files) {
        logger.info(
                "访问的地址是：" + url + "\n 传输参数是：" + JSON.toJSONString(params) + "\n 传输的图片数据是：" + JSON.toJSONString(files));
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        // httpPost.setHeader("Accept", "application/json");
        // httpPost.setHeader("Content-type", "application/json");
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(5000).setConnectionRequestTimeout(1000)
                .setSocketTimeout(5000).build();
        httpPost.setConfig(requestConfig);
        httpPost.setHeader("Connection", "close");
        httpPost.setHeader("Referer", url);
        httpPost.setHeader("applogin", new Date().getTime() + "");
        CloseableHttpResponse httpResponse = null;
        String resStr = null;
        try {
            Charset charset = CharsetUtils.get("UTF-8");
            MultipartEntityBuilder builder = MultipartEntityBuilder.create()
                    .setMode(HttpMultipartMode.BROWSER_COMPATIBLE).setCharset(charset); // 编码
            for (String key : params.keySet()) {
                Object value = params.get(key);
                StringBody stringBody = new StringBody(null == value ? "" : value.toString(),
                        ContentType.create(ContentType.TEXT_PLAIN.getMimeType(), charset)); // 编码
                builder.addPart(key, stringBody);
            }

            for (String key : files.keySet()) {
                Object value = files.get(key);
                if (value != null) {
                    File file = new File(value.toString());
                    FileBody fileBody = new FileBody(file);
                    builder.addPart(key, fileBody);
                }
            }
            HttpEntity httpEntity = builder.build();

            httpPost.setEntity(httpEntity);
            httpResponse = httpClient.execute(httpPost);
            if (isOK(httpResponse.getStatusLine().getStatusCode())) {
                resStr = bodyFromResponse(httpResponse);
                logger.info("请求结果：" + resStr);
            } else {
                logger.info("网络请求失败，状态码是 " + httpResponse.getStatusLine().getStatusCode());
            }
        } catch (IOException e) {
            logger.info("网络请求失败：" + e);
        } finally {
            try {
                if (httpClient != null) {
                    httpClient.close();
                }
                if (httpResponse != null) {
                    httpResponse.close();
                }

            } catch (IOException e) {
                logger.info("网络请求关闭失败：" + e);
            }
        }
        return resStr;
    }

    private static String initUrl(String url, Map<String, Object> params) {
        String requestUrl = "";
        requestUrl = url + "?time=" + new Date().getTime();
        if (params != null) {
            for (Map.Entry<String, Object> set : params.entrySet()) {
                requestUrl = requestUrl + "&&" + set.getKey() + "=" + set.getValue();
            }
        }
        return requestUrl;
    }

    private static String map2Str(Map<String, Object> params) {
        if ((params == null) || (params.size() <= 0)) {
            return null;
        }
        String result = "";
        for (Map.Entry<String, Object> set : params.entrySet()) {
            result = result + set.getKey() + ":" + set.getValue() + ",";
        }

        result = "{ " + result.substring(0, result.length() - 1) + " }";
        return result;
    }

    private static boolean isOK(int statusCode) {
        if (statusCode == HttpStatus.SC_OK || statusCode == HttpStatus.SC_CREATED
                || statusCode == HttpStatus.SC_ACCEPTED || statusCode == HttpStatus.SC_NON_AUTHORITATIVE_INFORMATION
                || statusCode == HttpStatus.SC_NO_CONTENT || statusCode == HttpStatus.SC_RESET_CONTENT
                || statusCode == HttpStatus.SC_PARTIAL_CONTENT || statusCode == HttpStatus.SC_MULTI_STATUS) {
            return true;
        } else {
            return false;
        }
    }

    private static String bodyFromResponse(HttpResponse response) {
        HttpEntity httpEntity = response.getEntity();
        if (httpEntity != null) {
            InputStream inputStream = null;
            InputStreamReader ir = null;
            BufferedReader br = null;
            StringBuffer stringBuffer = new StringBuffer();
            String result = null;
            try {
                inputStream = httpEntity.getContent();
                ir = new InputStreamReader(inputStream);
                br = new BufferedReader(ir);
                String str = "";
                while ((str = br.readLine()) != null) {
                    stringBuffer.append(str);
                }
                result = stringBuffer.toString();
            } catch (IOException e) {
                logger.info("读取返回值出现异常：" + e);
            } finally {
                try {
                    if (br != null) {
                        br.close();
                        br = null;
                    }
                    if (ir != null) {
                        ir.close();
                        ir = null;
                    }
                    if (inputStream != null) {
                        inputStream.close();
                        inputStream = null;
                    }
                } catch (IOException e) {
                    logger.info("关闭读取流出现异常：" + e);
                }
            }
            return result;
        } else {
            return null;
        }
    }

}

// http访问打印日志
class Http2Log {
    private final static boolean showLog = true;
    private final static boolean showWarn = true;
    private final static boolean showError = true;

    public static void log(Object object) {
        if (showLog) {
            // 根据自己的应用换成对应的日志输出
            System.out.println("[log ]" + object);
        }

    }

    public static void warn(Object object) {
        if (showWarn) {
            // 根据自己的应用换成对应的日志输出
            System.out.println("[warn ]" + object);
        }

    }

    public static void error(Object object) {
        if (showError) {
            // 根据自己的应用换成对应的日志输出
            System.out.println("[error]" + object);
        }

    }

}
