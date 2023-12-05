package com.toni.musify.domain.artist.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.toni.musify.domain.song.model.Song;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.util.List;

@Validated
public record ArtistDTO(
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        Integer id,
        @NotBlank(message = "First name can't be empty")
        String firstName,
        @NotBlank
        String lastName,
        @NotBlank
        String stageName,
        @NotNull
        ///@Pattern(regexp = "[[1-9]{4}-[0-9]{2}-[0-9]{2}]",message="Please enter a valid date with the format yyyy-mm-dd")
        LocalDate birthday,
        String location,
        @NotBlank
        @Size(max = 10)
        String activeStartDate,
        @NotBlank
        @Size(max = 10)
        String activeEndDate,

        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        Integer userId

) {
}
