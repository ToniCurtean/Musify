package com.toni.musify.domain.song.repository;

import com.toni.musify.domain.artist.model.Artist;
import com.toni.musify.domain.song.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SongRepository extends JpaRepository<Song,Integer> {

    @Query("SELECT s FROM Song s LEFT JOIN s.alternativeTitles at WHERE s.title LIKE CONCAT('%', :title, '%') OR at.alternativeTitle LIKE CONCAT('%', :title, '%')")
    Optional<List<Song>> findSongsByTitle(String title);

    Optional<List<Song>> findSongsByArtistsContains(Artist artist);
}
