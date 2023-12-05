package com.toni.musify.domain.playlist.repository;

import com.toni.musify.domain.playlist.model.Playlist;
import com.toni.musify.domain.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist,Integer> {

    Optional<Playlist> findPlaylistByIdAndIsPublicIsTrue(Integer id);

    Optional<Playlist> findPlaylistByIdAndUserId(Integer id,Integer userId);

    Optional<Playlist> findPlaylistByIdAndSubscribedUsersContains(Integer id,User subscribedUser);

    Optional<List<Playlist>> findPlaylistsByUserIdOrSubscribedUsersContains(Integer userid,User user);

    Optional<List<Playlist>> findPlaylistsByNameIsContainingAndAndIsPublicTrue(String name);
}
