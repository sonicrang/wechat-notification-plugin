package io.jenkins.plugins.wechat.model;

public class weChatData {
    String touser;
    String toparty;
    String totag;
    String msgtype = "markdown";
    int agentid;
    Text markdown;// 实际接收Map类型数据

    public Text getText() {
        return markdown;
    }

    public void setText(String content) {
        Text markdown = new Text();
        markdown.content = content;
        this.markdown = markdown;
    }

    public String getMsgtype() {
        return msgtype;
    }

    public int getAgentid() {
        return agentid;
    }

    public void setAgentid(int agentid) {
        this.agentid = agentid;
    }

    public String getTouser() {
        return touser;
    }

    public void setTouser(String touser) {
        this.touser = touser;
    }

    public String getToparty() {
        return toparty;
    }

    public void setToparty(String toparty) {
        this.toparty = toparty;
    }

    public String getTotag() {
        return totag;
    }

    public void setTotag(String totag) {
        this.totag = totag;
    }
}

class Text {
    String content;
}