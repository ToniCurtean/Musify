package com.toni.musify.domain.playlist.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.toni.musify.domain.song.model.Song;
import com.toni.musify.domain.song.model.SongViewDTO;
import com.toni.musify.domain.user.model.User;
import com.toni.musify.domain.user.model.UserViewDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record PlaylistViewDTO(
        Integer id,

        Integer userId,

        String name,

        Boolean isPublic,

        LocalDate createdAt,

        LocalDate updatedAt,

        UserViewDTO owner,
        List<SongViewDTO> songs) {
}
