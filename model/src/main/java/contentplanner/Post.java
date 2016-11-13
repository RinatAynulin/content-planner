package contentplanner;

import javax.persistence.*;

/**
 * Created by Aynulin on 13.11.2016.
 */

@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @Column(name = "message")
    private String message;
    @Column(name = "attachments")
    private String attachments;

    @Column(name = "publish_date")
    private int publishDate; //unix time

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    public Post() {
    }

    public Post(Group group, String message, String attachments, int publishDate, User author) {
        this.group = group;
        this.message = message;
        this.attachments = attachments;
        this.publishDate = publishDate;
        this.author = author;
    }

    public long getId() {
        return id;
    }

    public Group getGroup() {
        return group;
    }

    public String getMessage() {
        return message;
    }

    public String getAttachments() {
        return attachments;
    }

    public int getPublishDate() {
        return publishDate;
    }

    public User getAuthor() {
        return author;
    }
}
