package com.toni.musify.domain.playlist.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.toni.musify.domain.song.model.Song;
import com.toni.musify.domain.user.model.User;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;


@Valid
public record PlaylistDTO(

        @JsonProperty(access=JsonProperty.Access.READ_ONLY)
        Integer id,

        @NotBlank
        String name,

        @NotNull
        Boolean isPublic,

        @NotNull
        LocalDate createdAt,

        @NotNull
        LocalDate updatedAt,

        @JsonProperty(access=JsonProperty.Access.READ_ONLY)
        Integer userId) {
}
