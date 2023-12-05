package com.toni.musify.domain.alternativetitle.service;

import com.toni.musify.domain.alternativetitle.mappers.AlternativeTitleMapper;
import com.toni.musify.domain.alternativetitle.model.AlternativeTitle;
import com.toni.musify.domain.alternativetitle.model.AlternativeTitleDTO;
import com.toni.musify.domain.alternativetitle.model.AlternativeTitleViewDTO;
import com.toni.musify.domain.alternativetitle.repository.AlternativeTitleRepository;
import com.toni.musify.domain.song.model.Song;
import com.toni.musify.domain.song.repository.SongRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class AlternativeTitleService {

    private final AlternativeTitleRepository alternativeTitleRepository;
    private final AlternativeTitleMapper alternativeTitleMapper;
    private final SongRepository songRepository;

    public AlternativeTitleViewDTO addAlternativeTitle(AlternativeTitleDTO alternativeTitleDTO) {
        try {
            Song song = songRepository.getReferenceById(alternativeTitleDTO.songId());
            AlternativeTitle toSave = alternativeTitleMapper.toEntity(alternativeTitleDTO);
            song.addAlternativeTitle(toSave);
            songRepository.save(song);
            AlternativeTitleViewDTO saved = alternativeTitleMapper.toViewDTO(toSave);
            return saved;
        } catch (RuntimeException ex) {
            log.error(ex.getMessage());
            return null;
        }
    }

    @Transactional
    public void deleteAlternativeTitleById(Integer id) {
        try {
            Optional<AlternativeTitle> alternativeTitle = alternativeTitleRepository.findById(id);
            if (alternativeTitle.isPresent()) {
                alternativeTitleRepository.delete(alternativeTitle.get());
            } else {
                throw new EntityNotFoundException("Couldn't find the specified alternative title");
            }
        } catch (RuntimeException ex) {
            log.error(ex.getMessage());
        }
    }


    @Transactional
    public AlternativeTitleViewDTO updateAlternative(Integer id,String newName){
        try{
            Optional<AlternativeTitle> alternativeTitle=alternativeTitleRepository.findById(id);
            if(alternativeTitle.isPresent()){
                AlternativeTitle toUpdate=alternativeTitle.get();
                toUpdate.setAlternativeTitle(newName);
                return alternativeTitleMapper.toViewDTO(alternativeTitleRepository.save(toUpdate));
            }else{
                throw new EntityNotFoundException("Couldn't find the specified alternative title");
            }
        }catch (RuntimeException ex){
            log.error(ex.getMessage());
            return null;
        }
    }

    public List<AlternativeTitleViewDTO> findAlternativeTitlesForSong(Integer songId) {
        try{
            Optional<Song> song=songRepository.findById(songId);
            if(song.isPresent()){
                Optional<List<AlternativeTitle>> alternativeTitles=alternativeTitleRepository.findAlternativeTitlesBySong(song.get());
                if(alternativeTitles.isPresent()){
                    return alternativeTitleMapper.map(alternativeTitles.get());
                }
                return new ArrayList<>();
            }
            else{
                throw new EntityNotFoundException("Couldn't find the specified song!");
            }
        }catch (RuntimeException ex){
            log.error(ex.getMessage());
            return  null;
        }
    }
}
