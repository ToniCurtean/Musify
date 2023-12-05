package com.toni.musify;

import com.toni.musify.domain.alternativetitle.mappers.AlternativeTitleMapper;
import com.toni.musify.domain.alternativetitle.model.AlternativeTitle;
import com.toni.musify.domain.artist.mappers.ArtistMapper;
import com.toni.musify.domain.artist.mappers.ArtistMapperImpl;
import com.toni.musify.domain.artist.model.Artist;
import com.toni.musify.domain.artist.model.ArtistViewDTO;
import com.toni.musify.domain.artist.repository.ArtistRepository;
import com.toni.musify.domain.artist.service.ArtistService;
import com.toni.musify.domain.song.mappers.SongMapper;
import com.toni.musify.domain.song.mappers.SongMapperImpl;
import com.toni.musify.domain.song.model.Song;
import com.toni.musify.domain.song.model.SongDTO;
import com.toni.musify.domain.song.model.SongViewDTO;
import com.toni.musify.domain.song.repository.SongRepository;
import com.toni.musify.domain.song.service.SongService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class SongTest {

    @Autowired
    SongService songService;

    @Autowired
    ArtistRepository artistRepository;

    @Autowired
    SongRepository songRepository;

    @Autowired
    SongMapper songMapper;


    @Test
    public void testAddSong(){

        List<String> artistsNames=new ArrayList<>();
        artistsNames.add("toni");
        Artist artist=new Artist("toni","curtean","toni", LocalDate.of(2023,12,21),"Cluj","26-07-2003","present",new ArrayList<>());
        artist.setId(1);
        artist.setUserId(1);
        ArtistViewDTO dto=new ArtistViewDTO(1,"toni 'toni' curtean", LocalDate.of(2023,12,21),"Cluj","26-07-2003","present");
        when(artistRepository.findArtistByStageName(any(String.class))).thenReturn(Optional.of(artist));

        SongDTO toAdd=new SongDTO(20,"craciun","2:30",LocalDate.of(2023,12,21),11);
        List<Artist> artistList=new ArrayList<>();
        artistList.add(artist);
        Song added=new Song("craciun","2:30",LocalDate.of(2023,12,21),artistList,new ArrayList<>(),new ArrayList<>());

        added.setId(20);
        added.setUserId(11);
        List<ArtistViewDTO> artistViewDTOS=new ArrayList<>();
        artistViewDTOS.add(dto);
        SongViewDTO expected=new SongViewDTO(20,"craciun","2:30",LocalDate.of(2023,12,21),artistViewDTOS,new ArrayList<>());

        when(songRepository.save(any(Song.class))).thenReturn(added);
        SongViewDTO result=songService.addSong(toAdd,artistsNames);
        assertEquals(expected,result);
    }


    @org.springframework.boot.test.context.TestConfiguration
    static class TestConfiguration{

        @MockBean
        private ArtistRepository artistRepository;

        @MockBean
        private SongRepository songRepository;

        @MockBean
        private ArtistMapper artistMapper;

        @MockBean
        private AlternativeTitleMapper alternativeTitleMapper;
        @Bean
        public SongMapper songMapper(){
            return new SongMapperImpl();
        }

        @Bean
        public SongService SongService(){
            return new SongService(songRepository,artistRepository,songMapper());
        }


    }
}
