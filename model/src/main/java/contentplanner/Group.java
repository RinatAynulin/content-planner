package contentplanner;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Aynulin on 13.11.2016.
 */

@Entity
@Table(name = "groups")
public class Group {
    @Id
    private int id;
    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "admin_id")
    private User admin;

    @JsonIgnore
    @OneToMany(mappedBy = "group")
    private Set<Post> posts = new HashSet<>();

    public int getId() {
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

    public Group(int id, String name, User admin) {
        this.name = name;
        this.admin = admin;
    }

    public Group() {
    }
}
