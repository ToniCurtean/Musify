package com.toni.musify.domain.artist.repository;

import com.toni.musify.domain.artist.model.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArtistRepository extends JpaRepository<Artist,Integer> {
    public Optional<Artist> findArtistByStageName(String name);

    @Query("select a from Artist a where a.stageName like CONCAT('%', :name, '%')  or a.firstName like CONCAT('%', :name, '%') or a.lastName like CONCAT('%', :name, '%')")
    public Optional<List<Artist>> findArtistsByStageNameIsContainingOrFirstNameIsContainingOrLastNameIsContaining(String name);
}
