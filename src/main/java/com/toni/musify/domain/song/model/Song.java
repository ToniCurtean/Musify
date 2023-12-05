package com.toni.musify.domain.song.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.toni.musify.domain.BaseEntity;
import com.toni.musify.domain.UserIdListener;
import com.toni.musify.domain.alternativetitle.model.AlternativeTitle;
import com.toni.musify.domain.artist.model.Artist;
import com.toni.musify.domain.playlist.model.Playlist;
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
@Entity(name="Song")
@Table(name="songs")
@EntityListeners(UserIdListener.class)
public class Song extends BaseEntity {

    @Size(min=1,max=30)
    @Column(name="title_s",nullable = false)
    private String title;

    @Size(min=4,max=10)
    @Column(name="duration_s")
    private String duration;

    @Column(name="created_date_s",nullable = false)
    private LocalDate createdAt;

    @JsonBackReference
    @ManyToMany(mappedBy = "songs")
    private List<Artist> artists=new ArrayList<>();

    @JsonManagedReference
    @OneToMany(
            mappedBy = "song",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<AlternativeTitle> alternativeTitles = new ArrayList<>();

    @JsonBackReference
    @ManyToMany(mappedBy="songs")
    private List<Playlist> playlists=new ArrayList<>();

    public void addAlternativeTitle(AlternativeTitle alternativeTitle){
        alternativeTitles.add(alternativeTitle);
        alternativeTitle.setSong(this);
    }

    public void removeAlternativeTitle(AlternativeTitle alternativeTitle){
        alternativeTitles.remove(alternativeTitle);
        alternativeTitle.setSong(null);
    }


    public void addArtist(Artist artist){
        artists.add(artist);
        artist.getSongs().add(this);
    }

    public void removeArtist(Artist artist){
        artists.remove(artist);
        artist.getSongs().remove(this);
    }

}
