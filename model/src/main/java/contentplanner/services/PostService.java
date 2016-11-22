package contentplanner.services;

import contentplanner.datasets.Group;
import contentplanner.datasets.Post;
import contentplanner.datasets.PostPK;
import contentplanner.datasets.User;
import contentplanner.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * Created by Aynulin on 22.11.2016.
 */
@Component("postService")
@Transactional
public class PostService {
    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Collection<Post> getPosts(String username) {
        return postRepository.findByAuthorUsername(username);
    }

    public void updatePost(Post post) {
        postRepository.updatePost(post.getPostId(), post.getGroupId(), post.getMessage(), post.getAttachments(), post.getPublishDate());
    }

    public void deletePost(int groupId, int postId) {
        postRepository.delete(new PostPK(groupId, postId));
    }

    public Post addPost(Post post) {
        return postRepository.save(post);
    }
}
