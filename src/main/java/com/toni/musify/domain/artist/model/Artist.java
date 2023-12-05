package com.toni.musify.domain.artist.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.toni.musify.domain.BaseEntity;
import com.toni.musify.domain.UserIdListener;
import com.toni.musify.domain.song.model.Song;
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
@Entity(name = "Artist")
@Table(name = "artists")
@EntityListeners(UserIdListener.class)
public class Artist extends BaseEntity {

    @Size(min = 1, max = 50)
    @Column(name = "first_name_a", nullable = false)
    private String firstName;

    @Size(min = 1, max = 50)
    @Column(name = "last_name_a", nullable = false)
    private String lastName;

    @Size(min = 2, max = 50)
    @Column(name = "stage_name", nullable = false)
    private String stageName;

    @Column(name = "birthday", nullable = false)
    private LocalDate birthday;

    @Size(min = 5, max = 20)
    @Column(name = "location", nullable = false)
    private String location;

    @Size(min = 1, max = 20)
    @Column(name = "active_start_date", nullable = false)
    private String activeStartDate;

    @Size(min = 1, max = 20)
    @Column(name = "active_end_date", nullable = false)
    private String activeEndDate;

    @JsonManagedReference
    @ManyToMany(
            cascade = {CascadeType.MERGE,
                    CascadeType.PERSIST
            }
    )
    @JoinTable(name = "artistssongs",
            joinColumns = @JoinColumn(name = "id_a"),
            inverseJoinColumns = @JoinColumn(name = "id_s")
    )
    private List<Song> songs = new ArrayList<>();

    public void addSong(Song song){
        songs.add(song);
        song.getArtists().add(this);
    }

    public void removeSong(Song song){
        songs.remove(song);
        song.getArtists().remove(this);
    }

}
