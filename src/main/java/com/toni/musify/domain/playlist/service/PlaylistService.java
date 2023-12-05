package com.toni.musify.domain.playlist.service;


import com.toni.musify.domain.exceptions.BusinessValidationException;
import com.toni.musify.domain.playlist.mappers.PlaylistMapper;
import com.toni.musify.domain.playlist.model.Playlist;
import com.toni.musify.domain.playlist.model.PlaylistDTO;
import com.toni.musify.domain.playlist.model.PlaylistViewDTO;
import com.toni.musify.domain.playlist.repository.PlaylistRepository;
import com.toni.musify.domain.song.mappers.SongMapper;
import com.toni.musify.domain.song.model.Song;
import com.toni.musify.domain.song.model.SongViewDTO;
import com.toni.musify.domain.song.repository.SongRepository;
import com.toni.musify.domain.user.model.User;
import com.toni.musify.domain.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class PlaylistService {

    private final PlaylistRepository playlistRepository;

    private final UserRepository userRepository;

    private final SongRepository songRepository;

    private final PlaylistMapper playlistMapper;

    private final SongMapper songMapper;


    @Transactional
    public PlaylistViewDTO save(PlaylistDTO playlistDTO) {
        try {
            Playlist toSave = playlistMapper.toEntity(playlistDTO);
            Playlist saved=playlistRepository.save(toSave);
            saved.setOwner(userRepository.getReferenceById(saved.getUserId()));
            return playlistMapper.toViewDTO(playlistRepository.save(saved));
        } catch (RuntimeException ex) {
            log.error(ex.getMessage());
            return null;
        }
    }


    @Transactional
    public PlaylistViewDTO updateName(Integer playlistId, String newName) {
        try {
                Optional<Playlist> toUpdate = playlistRepository.findById(playlistId);
                if (toUpdate.isPresent()) {
                    Playlist found = toUpdate.get();
                    found.setUpdatedAt(LocalDate.now());
                    found.setName(newName);
                    return playlistMapper.toViewDTO(playlistRepository.save(found));
                } else {
                    throw new EntityNotFoundException("Could not find the wanted playlist");
                }
        } catch (RuntimeException ex) {
            log.error(ex.getMessage());
            return null;
        }
    }

    @Transactional
    public void deletePlaylistById(Integer playlistId) {
        try {
                Optional<Playlist> playlistToFind = playlistRepository.findById(playlistId);
                if (playlistToFind.isPresent()) {
                    playlistRepository.delete(playlistToFind.get());
                } else {
                    throw new EntityNotFoundException("Couldn't find the given playlist");
                }
        } catch (RuntimeException ex) {
            log.error(ex.getMessage());
        }
    }


    public List<PlaylistViewDTO> getPublicPlaylistsByName(String name) {
        try {
            Optional<List<Playlist>> playlists = playlistRepository.findPlaylistsByNameIsContainingAndAndIsPublicTrue(name);
            if (playlists.isPresent()) {
                List<PlaylistViewDTO> playlistViewDTOS = playlistMapper.map(playlists.get());
                return playlistViewDTOS;
            }
            return new ArrayList<>();
        } catch (RuntimeException ex) {
            log.error(ex.getMessage());
            return null;
        }
    }

    @Transactional
    public PlaylistViewDTO addSongToPlaylist(Integer playlistId, Integer songId) {
        try{
            Optional<Playlist> playlist=playlistRepository.findById(playlistId);
            if(playlist.isPresent()){
                Optional<Song> song=songRepository.findById(songId);
                if(song.isPresent()){
                    Playlist foundPlaylist=playlist.get();
                    Song foundSong=song.get();
                    foundPlaylist.addSong(foundSong);
                    return playlistMapper.toViewDTO(playlistRepository.save(foundPlaylist));
                }
                else{
                    throw new EntityNotFoundException("Couldn't find the specified song");
                }
            }else{
                throw new EntityNotFoundException("Couldn't find the specified playlist");
            }
        }catch(RuntimeException ex){
            log.error(ex.getMessage());
            return null;
        }
    }

    @Transactional
    public void deleteSongFromPlaylist(Integer playlistId, Integer songId) {
        try{
            Optional<Playlist> playlist=playlistRepository.findById(playlistId);
            if(playlist.isPresent()){
                Optional<Song> song=songRepository.findById(songId);
                if(song.isPresent()){
                    Playlist foundPlaylist=playlist.get();
                    Song foundSong=song.get();
                    foundPlaylist.removeSong(foundSong);
                    playlistRepository.save(foundPlaylist);
                }
                else{
                    throw new EntityNotFoundException("Couldn't find the specified song");
                }
            }else{
                throw new EntityNotFoundException("Couldn't find the specified playlist");
            }
        }catch(RuntimeException ex){
            log.error(ex.getMessage());
        }
    }

    public List<SongViewDTO> getSongsForPlaylist(Integer playlistId) {
        try{
            Optional<Playlist> playlist=playlistRepository.findById(playlistId);
            if(playlist.isPresent()){
                Playlist foundPlaylist=playlist.get();
                List<SongViewDTO> songViewDTOS=songMapper.map(foundPlaylist.getSongs());
                return songViewDTOS;
            }else{
                throw new EntityNotFoundException("Couldn't find the specified playlist");
            }
        }catch (RuntimeException ex){
            log.error(ex.getMessage());
            return null;
        }
    }
}
