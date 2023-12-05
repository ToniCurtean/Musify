package com.toni.musify.domain.song.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.toni.musify.domain.alternativetitle.model.AlternativeTitle;
import com.toni.musify.domain.artist.model.Artist;
import com.toni.musify.domain.playlist.model.Playlist;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

@Valid
public record SongDTO(
        @JsonProperty(access= JsonProperty.Access.READ_ONLY)
        Integer id,

        @NotBlank
        String title,

        @NotBlank
        String duration,

        @NotNull
        LocalDate createdAt,

        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        Integer userId) {
}
