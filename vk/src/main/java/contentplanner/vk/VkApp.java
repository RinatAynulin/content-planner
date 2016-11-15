package contentplanner.vk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * Created by Aynulin on 14.11.2016.
 */

public class VkApp {
    Environment env;

    public String appId = env.getProperty("vk.appId");
    public String appName = env.getProperty("vk.appName");

    public String getAppId() {
        return appId;
    }

    public String getAppName() {
        return appName;
    }

    @Autowired
    public VkApp(Environment env) {
        this.env = env;
    }
}
