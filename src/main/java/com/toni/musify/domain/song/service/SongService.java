package com.toni.musify.domain.song.service;

import com.toni.musify.domain.artist.model.Artist;
import com.toni.musify.domain.artist.repository.ArtistRepository;
import com.toni.musify.domain.song.mappers.SongMapper;
import com.toni.musify.domain.song.model.Song;
import com.toni.musify.domain.song.model.SongDTO;
import com.toni.musify.domain.song.model.SongViewDTO;
import com.toni.musify.domain.song.repository.SongRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class SongService {

    private final SongRepository songRepository;
    private final ArtistRepository artistRepository;
    private final SongMapper songMapper;

    @Transactional
    public SongViewDTO addSong(SongDTO songDTO, List<String> artistsNames){
        List<Optional<Artist>> artists= artistsNames.stream()
                .map(artistRepository::findArtistByStageName).toList();
        try{
            List<Artist> artistList= artists.stream().flatMap(Optional::stream).toList();
            if(artistList.size()!= artists.size())
                throw new EntityNotFoundException("Not all artists were found");
            Song toAdd=songMapper.toEntity(songDTO);
            artistList.forEach(toAdd::addArtist);
            Song added=songRepository.save(toAdd);
            return songMapper.toViewDTO(added);
        }catch(RuntimeException ex){
            log.error(ex.getMessage());
            return null;
        }
    }

    public List<SongViewDTO> findSongsByTitle(String title) {
        try{
            Optional<List<Song>> songs=songRepository.findSongsByTitle(title);
            if(songs.isPresent()){
                List<SongViewDTO> songsViewDTOS=songMapper.map(songs.get());
                return songsViewDTOS;
            }
            return new ArrayList<>();
        }catch (RuntimeException ex){
            log.error(ex.getMessage());
            return null;
        }
    }

    @Transactional
    public SongViewDTO updateTitle(Integer id, String newTitle) {
        try{
            Optional<Song> song=songRepository.findById(id);
            if(song.isPresent()){
                Song toUpdate=song.get();
                toUpdate.setTitle(newTitle);
                return songMapper.toViewDTO(songRepository.save(toUpdate));
            }else{
                throw new EntityNotFoundException("Couldn't find the song!");
            }
        }catch (RuntimeException ex){
            log.error(ex.getMessage());
            return null;
        }
    }

    public List<SongViewDTO> findSongsForArtist(String artistName) {
        try{
            Optional<Artist> artist=artistRepository.findArtistByStageName(artistName);
            if(artist.isPresent()){
                Optional<List<Song>> songs=songRepository.findSongsByArtistsContains(artist.get());
                if(songs.isPresent()){
                    List<SongViewDTO> songViewDTOS=songMapper.map(songs.get());
                    return songViewDTOS;
                }
                return new ArrayList<>();
            }
            else{
                throw new EntityNotFoundException("Couldn't find the specified artist!");
            }
        }catch (RuntimeException ex){
            log.error(ex.getMessage());
            return null;
        }
    }
}
