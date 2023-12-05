package com.toni.musify.domain.alternativetitle.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.toni.musify.domain.song.model.Song;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Valid
public record AlternativeTitleDTO(
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        Integer id,

        @NotBlank
        String alternativeTitle,

        @NotBlank
        String language,

        @NotNull
        @Positive
        Integer songId,

        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        Integer userId
        ) {
}
