package com.toni.musify.domain.user.controller;

import com.toni.musify.domain.playlist.model.PlaylistDTO;
import com.toni.musify.domain.playlist.model.PlaylistViewDTO;
import com.toni.musify.domain.playlist.service.PlaylistService;
import com.toni.musify.domain.user.service.UserService;
import com.toni.musify.domain.user.model.UserDTO;
import com.toni.musify.domain.user.model.UserViewDTO;
import com.toni.musify.security.check.RolesAllowed;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.toni.musify.security.ApplicationConfig.getCurrentUserId;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @RolesAllowed(value={"REGULAR"})
    @PostMapping("/subscribe/{playlistId}")
    public ResponseEntity<UserViewDTO> subscribeToPlaylist(@PathVariable @NotNull Integer playlistId){
        return ResponseEntity.ok(userService.subscribeToPlaylist(getCurrentUserId(),playlistId));
    }

    @RolesAllowed(value={"REGULAR"})
    @DeleteMapping("/unsubscribe/{playlistId}")
    public ResponseEntity<Void> unsubscribeToPlaylist(@PathVariable @NotNull Integer playlistId){
        userService.unsubscribeToPlaylist(getCurrentUserId(),playlistId);
        return ResponseEntity.noContent().build();
    }

    @RolesAllowed(value={"ADMIN","REGULAR"})
    @GetMapping("/playlists")
    public List<PlaylistViewDTO> getPlaylistsByUserId(){
        return userService.getPlaylistsByUserId(getCurrentUserId());
    }

}
