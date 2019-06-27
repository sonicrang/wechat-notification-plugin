// CHECKSTYLE:OFF

package io.jenkins.plugins.wechat;

import org.jvnet.localizer.Localizable;
import org.jvnet.localizer.ResourceBundleHolder;
import org.kohsuke.accmod.Restricted;


/**
 * Generated localization support class.
 * 
 */
@SuppressWarnings({
    "",
    "PMD",
    "all"
})
@Restricted(org.kohsuke.accmod.restrictions.NoExternalUse.class)
public class Messages {

    /**
     * The resource bundle reference
     * 
     */
    private final static ResourceBundleHolder holder = ResourceBundleHolder.get(Messages.class);

    /**
     * Key {@code WeChatNotify.DescriptorImpl.DisplayName}: {@code WeChat
     * Notification Plugin}.
     * 
     * @return
     *     {@code WeChat Notification Plugin}
     */
    public static String WeChatNotify_DescriptorImpl_DisplayName() {
        return holder.format("WeChatNotify.DescriptorImpl.DisplayName");
    }

    /**
     * Key {@code WeChatNotify.DescriptorImpl.DisplayName}: {@code WeChat
     * Notification Plugin}.
     * 
     * @return
     *     {@code WeChat Notification Plugin}
     */
    public static Localizable _WeChatNotify_DescriptorImpl_DisplayName() {
        return new Localizable(holder, "WeChatNotify.DescriptorImpl.DisplayName");
    }

}
