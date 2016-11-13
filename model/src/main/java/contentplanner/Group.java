package contentplanner;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Aynulin on 13.11.2016.
 */

@Entity
@Table(name = "users")
public class Group {
    @Id
    @GeneratedValue
    private long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "admin_id")
    private User admin;

    @OneToMany(mappedBy = "group")
    private Set<Post> posts = new HashSet<>();

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public User getAdmin() {
        return admin;
    }

    public Set<Post> getPosts() {
        return posts;
    }

    public Group(String name, User admin, Set<Post> posts) {
        this.name = name;
        this.admin = admin;
        this.posts = posts;
    }

    public Group() {
    }
}
