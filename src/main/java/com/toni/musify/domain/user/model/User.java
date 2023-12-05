package com.toni.musify.domain.user.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.toni.musify.domain.BaseEntity;
import com.toni.musify.domain.UserIdListener;
import com.toni.musify.domain.playlist.model.Playlist;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "User")
@Table(name = "users")
@EntityListeners(UserIdListener.class)
public class User extends BaseEntity {

    @Size(min=2,max=40)
    @Column(name = "first_name_u", nullable = false)
    private String firstName;

    @Size(min=2,max=40)
    @Column(name = "last_name_u", nullable = false)
    private String lastName;

    @Size(min=2,max=40)
    @Column(unique = true, name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Size(min=2,max=40)
    @Column(name = "country", nullable = false)
    private String country;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private UserRole role;

    @JsonManagedReference
    @OneToMany(
            mappedBy = "owner",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Playlist> ownedPlaylists = new ArrayList<>();


    @JsonManagedReference
    @ManyToMany(
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            }
    )
    @JoinTable(name = "playlistsusers",
            joinColumns = @JoinColumn(name = "id_u"),
            inverseJoinColumns = @JoinColumn(name = "id_p")
    )
    private List<Playlist> subscribedPlaylists = new ArrayList<>();

    public void addOwnedPlaylist(Playlist playlist) {
        ownedPlaylists.add(playlist);
        playlist.setOwner(this);
    }

    public void removeOwnedPlaylist(Playlist playlist) {
        ownedPlaylists.remove(playlist);
        playlist.setOwner(null);
    }

    public void addSubscribedPlaylists(Playlist playlist){
        subscribedPlaylists.add(playlist);
        playlist.getSubscribedUsers().add(this);
    }

    public void removeSubscribedPlaylists(Playlist playlist){
        subscribedPlaylists.remove(playlist);
        playlist.getSubscribedUsers().remove(this);
    }

}
