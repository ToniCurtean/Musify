package com.toni.musify.domain.song.controller;


import com.toni.musify.domain.song.model.SongDTO;
import com.toni.musify.domain.song.model.SongViewDTO;
import com.toni.musify.domain.song.service.SongService;
import com.toni.musify.domain.user.model.UserRole;
import com.toni.musify.security.check.RolesAllowed;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Objects;

import static com.toni.musify.security.ApplicationConfig.getCurrentUserRole;

@RestController
@RequestMapping("/songs")
@AllArgsConstructor
public class SongController {

    private final SongService songService;

    @RolesAllowed(value={"ADMIN"})
    @PostMapping
    public SongViewDTO addSong(@RequestBody @Valid SongDTO songDTO, @RequestParam @NotEmpty List<String> artistNames){
        return songService.addSong(songDTO,artistNames);
    }

    @RolesAllowed(value={"ADMIN","REGULAR"})
    @GetMapping("/{title}")
    public List<SongViewDTO> findSongsByTitle(@PathVariable @NotBlank String title){
        return songService.findSongsByTitle(title);
    }

    @RolesAllowed(value={"ADMIN"})
    @PutMapping("/{id}")
    public SongViewDTO updateSongTitle(@PathVariable @NotNull @Positive Integer id,@RequestParam @NotBlank String newTitle){
        return songService.updateTitle(id,newTitle);
    }

    @RolesAllowed(value={"ADMIN","REGULAR"})
    @GetMapping("/artist/{artistName}")
    public List<SongViewDTO> findSongsForArtist(@PathVariable @NotBlank String artistName){
        return songService.findSongsForArtist(artistName);
    }

}
