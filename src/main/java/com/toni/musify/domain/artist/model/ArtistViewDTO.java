package com.toni.musify.domain.artist.model;

import com.toni.musify.domain.song.model.Song;
import com.toni.musify.domain.song.model.SongViewDTO;

import java.time.LocalDate;
import java.util.List;

public record ArtistViewDTO(Integer id, String presentationName, LocalDate birthday, String location,
                            String activeStartDate, String activeEndDate) {


}
