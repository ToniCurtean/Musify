package com.toni.musify.domain.playlist.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.toni.musify.domain.BaseEntity;
import com.toni.musify.domain.UserIdListener;
import com.toni.musify.domain.song.model.Song;
import com.toni.musify.domain.user.model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="playlists")
@EntityListeners(UserIdListener.class)
public class Playlist extends BaseEntity{

    @Size(min=1,max=50)
    @Column(name="name",nullable = false)
    private String name;

    @Column(name= "is_public")
    private Boolean isPublic;

    @Column(name="created_date_p",nullable = false)
    private LocalDate createdAt;

    @Column(name="updated_date_p",nullable = false)
    private LocalDate updatedAt;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "owner_id")
    private User owner;

    @JsonBackReference
    @ManyToMany(mappedBy="subscribedPlaylists")
    private List<User> subscribedUsers=new ArrayList<>();

    @ManyToMany(
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            }
    )
    @JoinTable(name = "playlistssongs",
            joinColumns = @JoinColumn(name = "id_p"),
            inverseJoinColumns = @JoinColumn(name = "id_s")
    )
    private List<Song> songs = new ArrayList<>();

    public void addSong(Song song){
        songs.add(song);
        song.getPlaylists().add(this);
    }

    public void removeSong(Song song){
        songs.remove(song);
        song.getPlaylists().remove(this);
    }
}
