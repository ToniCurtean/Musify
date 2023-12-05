package com.toni.musify.domain.artist.service;

import com.toni.musify.domain.artist.mappers.ArtistMapper;
import com.toni.musify.domain.artist.model.Artist;
import com.toni.musify.domain.artist.model.ArtistDTO;
import com.toni.musify.domain.artist.model.ArtistViewDTO;
import com.toni.musify.domain.artist.repository.ArtistRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ArtistService {

    private final ArtistRepository artistRepository;

    private final ArtistMapper artistMapper;


    @Transactional
    public ArtistViewDTO addArtist(ArtistDTO artistDTO) {
        Artist newArtist = artistMapper.toEntity(artistDTO);
        Artist addedArtist = artistRepository.save(newArtist);
        return artistMapper.toViewDTO(addedArtist);
    }

    public List<ArtistViewDTO> getArtistsByName(String name){
        try{
            Optional<List<Artist>> artists=artistRepository.findArtistsByStageNameIsContainingOrFirstNameIsContainingOrLastNameIsContaining(name);
            if(artists.isPresent()){
                List<ArtistViewDTO> artistViewDTOS=artistMapper.map(artists.get());
                return artistViewDTOS;
            }
            return new ArrayList<>();
        } catch (RuntimeException ex ) {
            log.error(ex.getMessage());
            return null;
        }
    }

    public ArtistViewDTO getArtistById(Integer id) {
        try{
            Optional<Artist> toFind=artistRepository.findById(id);
            return toFind.map(artistMapper::toViewDTO).orElse(null);
        } catch (RuntimeException ex ) {
            log.error(ex.getMessage());
            return null;
        }
    }

    @Transactional
    public void deleteArtistById(Integer id) {
        try{
            artistRepository.deleteById(id);
        }catch(RuntimeException ex ){
            log.error(ex.getMessage());
        }

    }

    @Transactional
    public ArtistViewDTO updateArtist(Integer id, ArtistDTO artistDTO) {
        try{
            Optional<Artist> toFind=artistRepository.findById(id);
            if(toFind.isPresent()){
                Artist found=toFind.get();
                found.setFirstName(artistDTO.firstName());
                found.setLastName(artistDTO.lastName());
                found.setStageName(artistDTO.stageName());
                found.setLocation(artistDTO.location());
                found.setActiveStartDate(artistDTO.activeStartDate());
                found.setActiveEndDate(artistDTO.activeEndDate());
                return artistMapper.toViewDTO(artistRepository.save(found));
            }
            return null;
            ///Artist updated=artistRepository.
            ////return artistMapper.toDTO(updated);
        }catch(RuntimeException ex){
            log.error(ex.getMessage());
            return null;
        }
    }
}
