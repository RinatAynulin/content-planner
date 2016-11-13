package contentplanner;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Aynulin on 13.11.2016.
 */

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    private long id;

    private String username;
    @JsonIgnore
    private String password;
    private String email;
    @JsonIgnore
    private String token;

    @OneToMany(mappedBy = "admin")
    private Set<Group> groups = new HashSet<>();

    @OneToMany(mappedBy = "author")
    private Set<Post> posts = new HashSet<>();

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getToken() {
        return token;
    }

    public Set<Group> getGroups() {
        return groups;
    }

    public Set<Post> getPosts() {
        return posts;
    }

    public User() {
    }

    public User(String username, String password, String email, String token, Set<Group> groups, Set<Post> posts) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.token = token;
        this.groups = groups;
        this.posts = posts;
    }
}
