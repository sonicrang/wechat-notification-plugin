package io.jenkins.plugins.wechat;

import hudson.Launcher;
import hudson.Extension;
import hudson.FilePath;
import io.jenkins.plugins.wechat.model.urlData;
import hudson.model.AbstractProject;
import hudson.model.Run;
import hudson.model.TaskListener;
import hudson.tasks.Notifier;
import hudson.tasks.Publisher;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.BuildStepMonitor;

import org.kohsuke.stapler.DataBoundConstructor;

import java.io.IOException;
import java.util.Map;

import jenkins.tasks.SimpleBuildStep;
import org.jenkinsci.Symbol;

public class WeChatNotifier extends Notifier implements SimpleBuildStep {

    private final String corpid;
    private final String secret;
    private final int agentid;

    private final String touser;
    private final String toparty;
    private final String totag;
    private final String markdown;

    @DataBoundConstructor
    public WeChatNotifier(String corpid, String secret, int agentid, String touser, String toparty, String totag,
            String markdown) {
        this.corpid = corpid;
        this.secret = secret;
        this.agentid = agentid;

        this.touser = touser;
        this.toparty = toparty;
        this.totag = totag;
        this.markdown = markdown;
    }

    public String getCorpid() {
        return corpid;
    }

    public String getSecret() {
        return secret;
    }

    public int getAgentid() {
        return agentid;
    }

    public String getTouser() {
        return touser;
    }

    public String getToparty() {
        return toparty;
    }

    public String getTag() {
        return totag;
    }

    public String getMarkdown() {
        return markdown;
    }

    @Override
    public void perform(Run<?, ?> run, FilePath workspace, Launcher launcher, TaskListener listener)
            throws InterruptedException, IOException {
        WeChatMessage sw = new WeChatMessage();
        try {
            WeChatHelper singleton = WeChatHelper.getInstance();
            Map<String, Object> map = singleton.getAccessTokenAndJsapiTicket(corpid, secret);
            String token = (String) map.get("access_token");
            String postdata = sw.createpostdata(agentid, touser, toparty, totag, markdown);
            String resp = sw.post("utf-8", WeChatMessage.CONTENT_TYPE, (new urlData()).getSendMessage_Url(), postdata,
                    token);
            listener.getLogger().println("resp:" + resp);

        } catch (Exception e) {
            e.printStackTrace();
            listener.getLogger().println("err:" + e.getMessage());
        }
    }

    @Override
    public BuildStepMonitor getRequiredMonitorService() {
        return BuildStepMonitor.NONE;
    }

    @Symbol("wechat")
    @Extension
    public static final class DescriptorImpl extends BuildStepDescriptor<Publisher> {

        @Override
        public boolean isApplicable(Class<? extends AbstractProject> jobType) {
            return true;
        }

        @Override
        public String getDisplayName() {
            return "WeChat Notification Plugin";
        }
    }
}
