package com.toni.musify.domain.user.model;

import com.toni.musify.domain.playlist.model.Playlist;

import java.util.List;

public record UserViewDTO(Integer id, String firstName, String lastName, String email, String password, String country,
                          UserRole role, List<Playlist> ownedPlaylists,List<Playlist> subscribedPlaylists) {
}
