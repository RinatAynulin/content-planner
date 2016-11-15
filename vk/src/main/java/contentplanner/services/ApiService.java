package contentplanner.services;

import com.google.gson.Gson;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import contentplanner.Post;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * Created by Aynulin on 15.11.2016.
 */

public class ApiService {
    private TransportClient transportClient;
    private VkApiClient vk;
    private UserActor userActor;

    public ApiService(Integer userId, String accessToken) {
        transportClient = HttpTransportClient.getInstance();
        vk = new VkApiClient(transportClient, new Gson());
        userActor = new UserActor(userId, accessToken);
    }

    public Integer schedulePost(Post post) throws ClientException, ApiException {
        Logger.getLogger(ApiService.class).info("I'm trying to schedule the post: " + post);
        return vk.wall().post(userActor)
                .attachments(post.getAttachments().split(","))
                .message(post.getMessage())
                .publishDate(post.getPublishDate())
                .ownerId((int) (-1 * post.getGroup().getId()))
                .execute()
                .getPostId();
    }
}