package contentplanner.datasets;

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

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "group_administration", joinColumns = {
            @JoinColumn(name = "group_id", nullable = false, updatable = false)},
            inverseJoinColumns = {@JoinColumn(name = "admin_id",
                    nullable = false, updatable = false)})
    private Set<User> admins;

    @JsonIgnore
    @OneToMany(mappedBy = "group")
    private Set<Post> posts = new HashSet<>();

    public Group(int id, String name, Set<User> admins) {
        this.id = id;
        this.name = name;
        this.admins = admins;
    }

    public Group(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Group() {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<User> getAdmin() {
        return admins;
    }

    public Set<Post> getPosts() {
        return posts;
    }

    public void setAdmins(Set<User> admins) {
        this.admins = admins;
    }
}
