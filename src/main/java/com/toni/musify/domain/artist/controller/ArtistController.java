package com.toni.musify.domain.artist.controller;


import com.toni.musify.domain.artist.model.ArtistDTO;
import com.toni.musify.domain.artist.model.ArtistViewDTO;
import com.toni.musify.domain.artist.service.ArtistService;
import com.toni.musify.domain.user.model.UserRole;
import com.toni.musify.security.check.RolesAllowed;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Objects;

import static com.toni.musify.security.ApplicationConfig.getCurrentUserRole;

@RestController
@RequestMapping("/artists")
@RequiredArgsConstructor
public class ArtistController {

    private final ArtistService artistService;


    @RolesAllowed(value = {"ADMIN"})
    @PostMapping
    public ArtistViewDTO addArtist(@Valid @RequestBody ArtistDTO artistDTO) {
        return artistService.addArtist(artistDTO);
    }

    @RolesAllowed(value = {"ADMIN", "REGULAR"})
    @GetMapping("/name")
    public ResponseEntity<List<ArtistViewDTO>> getArtistsByName(@RequestParam @NotBlank String name) {
        return ResponseEntity.ok(artistService.getArtistsByName(name));
    }

    @RolesAllowed(value = {"ADMIN", "REGULAR"})
    @GetMapping("/{id}")
    public ResponseEntity<ArtistViewDTO> getArtistById(@PathVariable @Positive Integer id) {
        return ResponseEntity.ok(artistService.getArtistById(id));
    }

    @RolesAllowed(value = {"ADMIN"})
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteArtistById(@PathVariable @Positive Integer id) {
        artistService.deleteArtistById(id);
        return ResponseEntity.noContent().build();
    }

    @RolesAllowed(value = {"ADMIN"})
    @PutMapping("/update/{id}")
    public ResponseEntity<ArtistViewDTO> updateArtist(@PathVariable @Positive Integer id, @RequestBody @Valid ArtistDTO artistDTO) {
        return ResponseEntity.ok(artistService.updateArtist(id, artistDTO));
    }


}
