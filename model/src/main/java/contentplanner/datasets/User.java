package contentplanner.datasets;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

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
    @Column(name = "id") // fixme now it's vk id, change to our id and add field vkId
    private int id;
    @Column(name = "username")
    private String username;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "password")
    private String password;
    @Column(name = "email")
    private String email;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "token")
    private String token;

    @JsonIgnore
    @ManyToMany(mappedBy = "admins")
    private Set<Group> groups = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "author")
    private Set<Post> posts = new HashSet<>();

    public int getId() {
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

    public User(int id, String username, String password, String email, String token) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.token = token;
    }
}
