package contentplanner.services;

import com.google.gson.Gson;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import contentplanner.datasets.Post;
import org.apache.log4j.Logger;

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
                .ownerId(-1 * post.getGroup().getId())
                .execute()
                .getPostId();
    }

    public void editPost(Post post, int groupId) throws ClientException, ApiException {
        Logger.getLogger(ApiService.class).info("I'm trying to change the post to: " + post);
        vk.wall().edit(userActor, post.getId())
                .ownerId(-1 * groupId)
                .attachments(post.getAttachments())
                .message(post.getMessage())
                .publishDate(post.getPublishDate())
                .execute();
    }

    public void unschedulePost(int postId, int groupId) throws ClientException, ApiException {
        Logger.getLogger(ApiService.class).info("I'm trying to unschedule post with id " + postId);
        vk.wall().delete(userActor)
                .ownerId(-1 * groupId)
                .postId(postId)
                .execute();
    }
}