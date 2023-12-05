package com.toni.musify.domain.user.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.toni.musify.domain.playlist.model.Playlist;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Valid
public record UserDTO(
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        Integer id,

        @NotBlank
        String firstName,

        @NotBlank
        String lastName,

        @NotBlank
        String email,

        @NotBlank
        String password,

        @NotBlank
        String country,

        @NotNull
        UserRole role,

        Integer userId

) {

}
