package io.jenkins.plugins.wechat;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class WeChatHelper {
    // 缓存accessToken 的Map,map中包含 一个accessToken 和 缓存的时间戳
    private Map<String, String> map = new HashMap<String, String>();

    private WeChatHelper() {

    }

    private static WeChatHelper single = null;

    // 静态工厂方法
    public static WeChatHelper getInstance() {
        if (single == null) {
            single = new WeChatHelper();
        }
        return single;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    public static WeChatHelper getSingle() {
        return single;
    }

    public static void setSingle(WeChatHelper single) {
        WeChatHelper.single = single;
    }

    /**
     * 获取 accessToken Jsapi_ticket 已加入缓存机制
     * 
     * @param corpid 企业ID
     * @param secret 密钥
     * @return token
     */
    public Map<String, Object> getAccessTokenAndJsapiTicket(String corpid, String secret) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            WeChatHelper singleton = WeChatHelper.getInstance();
            Map<String, String> map = singleton.getMap();
            String time = map.get("time");// 从缓存中拿数据
            String accessToken = map.get("access_token");// 从缓存中拿数据
            Long nowDate = new Date().getTime();
            // 这里设置过期时间 5400 * 1000就好了 一个半小时
            if (accessToken != null && time != null && nowDate - Long.parseLong(time) < 5400 * 1000) {
                System.out.println("-----从缓存读取access_token：" + accessToken);
                // 从缓存中拿数据为返回结果赋值
                result.put("access_token", accessToken);
            } else {
                WeChatMessage sw = new WeChatMessage();
                String token = sw.getToken(corpid, secret);
                // 将信息放置缓存中
                map.remove("time");// 将key删掉
                map.put("time", nowDate + "");// 重新添加time
                map.put("access_token", token);
                // 为返回结果赋值
                result.put("access_token", token);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}