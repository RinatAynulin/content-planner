package contentplanner;

import javax.persistence.*;

/**
 * Created by Aynulin on 13.11.2016.
 */

@Entity
@Table(name = "users")
public class Post {
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    private String message;
    private String attachments;

    private int publishDate; //unix time

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;
}
