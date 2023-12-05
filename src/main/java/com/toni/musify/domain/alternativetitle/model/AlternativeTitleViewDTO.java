package com.toni.musify.domain.alternativetitle.model;

import com.toni.musify.domain.song.model.Song;
import com.toni.musify.domain.song.model.SongViewDTO;

public record AlternativeTitleViewDTO(Integer id, String alternativeTitle, String language) {
}
