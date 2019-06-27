package io.jenkins.plugins.wechat;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.LoggerFactory;

import io.jenkins.plugins.wechat.model.urlData;
import io.jenkins.plugins.wechat.model.weChatData;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class WeChatMessage {
    private CloseableHttpClient httpClient;
    private HttpPost httpPost;// 用于提交登陆数据
    private HttpGet httpGet;// 用于获得登录后的页面
    public static final String CONTENT_TYPE = "Content-Type";
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//

    private static Gson gson = new Gson();

    /**
     * 微信授权请求，GET类型，获取授权响应，用于其他方法截取token
     * 
     * @param Get_Token_Url token url
     * @return String 授权响应内容
     * @throws IOException exception
     */
    protected String toAuth(String Get_Token_Url) throws IOException {

        httpClient = HttpClients.createDefault();
        httpGet = new HttpGet(Get_Token_Url);
        CloseableHttpResponse response = httpClient.execute(httpGet);
        String resp;
        try {
            HttpEntity entity = response.getEntity();
            resp = EntityUtils.toString(entity, "utf-8");
            EntityUtils.consume(entity);
        } finally {
            response.close();
        }
        LoggerFactory.getLogger(getClass()).info(" resp:{}", resp);
        return resp;
    }

    /**
     * 获取toAuth(String Get_Token_Url)返回结果中键值对中access_token键的值
     * 
     * @param corpid 企业ID
     * @param secret 应用秘钥
     * @return token
     * @throws IOException 异常
     */
    protected String getToken(String corpid, String secret) throws IOException {
        WeChatMessage sw = new WeChatMessage();
        urlData uData = new urlData();
        uData.setGet_Token_Url(corpid, secret);
        String resp = sw.toAuth(uData.getGet_Token_Url());

        Map<String, Object> map = gson.fromJson(resp, new TypeToken<Map<String, Object>>() {
        }.getType());
        return map.get("access_token").toString();
    }

    /**
     * 创建微信发送请求post数据，markdown格式
     * 
     * @param touser   发送到人
     * @param toparty  发送到部门
     * @param totag    发送到组
     * @param agent_id 应用编号。
     * @param markdown 格式的内容
     * @return String
     */
    protected String createpostdata(int agent_id, String touser, String toparty, String totag, String markdown) {
        weChatData wcd = new weChatData();
        wcd.setAgentid(agent_id);
        wcd.setTouser(touser);
        wcd.setToparty(toparty);
        wcd.setTotag(totag);
        wcd.setText(markdown);

        return gson.toJson(wcd);
    }

    /**
     * 创建微信发送请求post实体
     * 
     * @param charset     charset
     * @param contentType contentType
     * @param url         微信消息发送请求地址
     * @param data        post数据
     * @param token       token
     * @return String
     * @throws IOException 异常
     */
    public String post(String charset, String contentType, String url, String data, String token) throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        httpPost = new HttpPost(url + token);
        httpPost.setHeader(CONTENT_TYPE, contentType);
        httpPost.setEntity(new StringEntity(data, charset));
        CloseableHttpResponse response = httpclient.execute(httpPost);
        String resp;
        try {
            HttpEntity entity = response.getEntity();
            resp = EntityUtils.toString(entity, charset);
            EntityUtils.consume(entity);
        } finally {
            response.close();
        }
        LoggerFactory.getLogger(getClass()).info("call [{}], param:{}, resp:{}", url, data, resp);
        return resp;
    }

}