package com.toni.musify.domain.song.model;

import com.toni.musify.domain.alternativetitle.model.AlternativeTitle;
import com.toni.musify.domain.alternativetitle.model.AlternativeTitleViewDTO;
import com.toni.musify.domain.artist.model.Artist;
import com.toni.musify.domain.artist.model.ArtistViewDTO;
import com.toni.musify.domain.playlist.model.Playlist;
import com.toni.musify.domain.playlist.model.PlaylistViewDTO;

import java.time.LocalDate;
import java.util.List;

public record SongViewDTO(Integer id, String title, String duration, LocalDate createdAt, List<ArtistViewDTO> artists,
                          List<AlternativeTitleViewDTO> alternativeTitles) {
}
