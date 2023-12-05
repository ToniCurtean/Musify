package com.toni.musify.domain.alternativetitle.repository;

import com.toni.musify.domain.alternativetitle.model.AlternativeTitle;
import com.toni.musify.domain.song.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlternativeTitleRepository extends JpaRepository<AlternativeTitle,Integer> {

    public Optional<List<AlternativeTitle>> findAlternativeTitlesBySong(Song song);
}
