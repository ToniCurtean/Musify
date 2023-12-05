package com.toni.musify.domain.playlist.controller;


import com.toni.musify.domain.playlist.model.PlaylistDTO;
import com.toni.musify.domain.playlist.model.PlaylistViewDTO;
import com.toni.musify.domain.playlist.service.PlaylistService;
import com.toni.musify.domain.song.model.SongViewDTO;
import com.toni.musify.domain.user.model.UserCredentials;
import com.toni.musify.security.check.RolesAllowed;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.toni.musify.security.ApplicationConfig.getCurrentUserId;

@RestController
@RequestMapping("/playlists")
@RequiredArgsConstructor
public class PlaylistController {

    private final PlaylistService playlistService;

    @RolesAllowed(value={"ADMIN","REGULAR"})
    @PostMapping
    public PlaylistViewDTO addPlaylist(@Valid @RequestBody PlaylistDTO playlistDTO){
        return playlistService.save(playlistDTO);
    }

    @RolesAllowed(value={"ADMIN","REGULAR"})
    @PutMapping("/update/{playlistId}")
    public PlaylistViewDTO updatePlaylistName(@PathVariable @Positive Integer playlistId,@RequestParam @NotNull String newName){
        return playlistService.updateName(playlistId,newName);
    }

    @RolesAllowed(value={"ADMIN","REGULAR"})
    @DeleteMapping("/delete/{playlistId}")
    public ResponseEntity<Void> deletePlaylistById(@PathVariable @Positive Integer playlistId) {
        playlistService.deletePlaylistById(playlistId);
        return ResponseEntity.noContent().build();
    }

    @RolesAllowed(value={"ADMIN","REGULAR"})
    @GetMapping("/{name}")
    public List<PlaylistViewDTO> getPublicPlaylistsByName(@PathVariable @NotBlank String name){
        return playlistService.getPublicPlaylistsByName(name);
    }

    @RolesAllowed(value={"ADMIN","REGULAR"})
    @PutMapping("/{playlistId}/{songId}")
    public PlaylistViewDTO addSongToPlaylist(@PathVariable @NotNull @Positive Integer playlistId,@PathVariable @NotNull @Positive Integer songId){
        return playlistService.addSongToPlaylist(playlistId,songId);
    }

    @RolesAllowed(value={"ADMIN","REGULAR"})
    @DeleteMapping("/{playlistId}/{songId}")
    public ResponseEntity<Void> deleteSongFromPlaylist(@PathVariable @NotNull @Positive Integer playlistId,@PathVariable @NotNull @Positive Integer songId){
        playlistService.deleteSongFromPlaylist(playlistId,songId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/songs/{playlistId}")
    public List<SongViewDTO> getSongsForPlaylist(@PathVariable @NotNull @Positive Integer playlistId){
        return playlistService.getSongsForPlaylist(playlistId);
    }
}
