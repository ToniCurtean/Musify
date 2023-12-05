package com.toni.musify.domain.user.service;


import com.toni.musify.domain.playlist.mappers.PlaylistMapper;
import com.toni.musify.domain.playlist.model.Playlist;
import com.toni.musify.domain.playlist.model.PlaylistViewDTO;
import com.toni.musify.domain.playlist.repository.PlaylistRepository;
import com.toni.musify.domain.user.mappers.UserMapper;
import com.toni.musify.domain.user.model.User;
import com.toni.musify.domain.user.model.UserDTO;
import com.toni.musify.domain.user.model.UserViewDTO;
import com.toni.musify.domain.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;

    private final PlaylistRepository playlistRepository;
    private final UserMapper userMapper;

    private final PlaylistMapper playlistMapper;

    @Transactional
    public UserViewDTO save(UserDTO userDTO) {
        User toAdd = userMapper.toEntity(userDTO);
        UserViewDTO added = userMapper.toViewDTO(userRepository.save(toAdd));
        return added;
    }

    @Transactional
    public UserViewDTO subscribeToPlaylist(Integer userId, Integer playlistId) {
        try {
                Optional<Playlist> playlistToFind = playlistRepository.findPlaylistByIdAndIsPublicIsTrue(playlistId);
                if (playlistToFind.isPresent()) {
                    User currentUser=userRepository.getReferenceById(userId);
                    Playlist foundPlaylist = playlistToFind.get();
                    currentUser.addSubscribedPlaylists(foundPlaylist);
                    return userMapper.toViewDTO(userRepository.save(currentUser));
                } else {
                    throw new EntityNotFoundException("Couldn't find the specified playlist");
                }
        } catch (RuntimeException ex) {
            log.error(ex.getMessage());
            return null;
        }
    }

    @Transactional
    public void unsubscribeToPlaylist(Integer userId, Integer playlistId) {
        try {
                User currentUser = userRepository.getReferenceById(userId);
                Optional<Playlist> playlistToFind = playlistRepository.findPlaylistByIdAndSubscribedUsersContains(playlistId, currentUser);
                if (playlistToFind.isPresent()) {
                    Playlist foundPlaylist = playlistToFind.get();
                    currentUser.removeSubscribedPlaylists(foundPlaylist);
                    userRepository.save(currentUser);
                } else {
                    throw new EntityNotFoundException("Couldn't find the given playlist");
                }
        } catch (RuntimeException ex) {
            log.error(ex.getMessage());
        }
    }

    public List<PlaylistViewDTO> getPlaylistsByUserId(Integer userId) {
        try{
            Optional<User> userToFind=userRepository.findById(userId);
            if(userToFind.isPresent()){
                User found=userToFind.get();
                List<PlaylistViewDTO> allPlaylists=new ArrayList<>();

                List<PlaylistViewDTO> ownedPlaylists=playlistMapper.map(found.getOwnedPlaylists());
                allPlaylists.addAll(ownedPlaylists);

                List<PlaylistViewDTO> subscribedPlaylists=playlistMapper.map(found.getSubscribedPlaylists());
                allPlaylists.addAll(subscribedPlaylists);
                return allPlaylists;
            }else{
                throw new EntityNotFoundException("Couldn't find the specified user");
            }
        }catch (RuntimeException ex){
            log.error(ex.getMessage());
            return null;
        }
    }
}
