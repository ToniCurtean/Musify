package com.toni.musify;

import com.toni.musify.domain.artist.mappers.ArtistMapper;
import com.toni.musify.domain.artist.mappers.ArtistMapperImpl;
import com.toni.musify.domain.artist.model.Artist;
import com.toni.musify.domain.artist.model.ArtistDTO;
import com.toni.musify.domain.artist.model.ArtistViewDTO;
import com.toni.musify.domain.artist.repository.ArtistRepository;
import com.toni.musify.domain.artist.service.ArtistService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class ArtistTest {

    @Autowired
    ArtistService artistService;

    @Autowired
    ArtistRepository artistRepository;

    @Autowired
    ArtistMapper artistMapper;

    @Test
    public void testAddArtist(){
        ArtistDTO toAdd=new ArtistDTO(11,"toni","curtean","toni", LocalDate.of(2023,12,21),"Cluj","26-07-2003","present",12);
        ArtistViewDTO expected=new ArtistViewDTO(11,"toni 'toni' curtean", LocalDate.of(2023,12,21),"Cluj","26-07-2003","present");

        Artist addedEntity=new Artist("toni","curtean","toni", LocalDate.of(2023,12,21),"Cluj","26-07-2003","present",new ArrayList<>());

        addedEntity.setId(11);
        addedEntity.setUserId(12);

        when(artistRepository.save(any(Artist.class))).thenReturn(addedEntity);
        ArtistViewDTO result= artistService.addArtist(toAdd);
        assertEquals(expected,result);
    }

    @Test
    public void testUpdateArtist(){
        Artist toUpdate=new Artist("toni","curtean","toni", LocalDate.of(2023,12,21),"Cluj","26-07-2003","present",new ArrayList<>());
        toUpdate.setId(11);
        toUpdate.setUserId(12);
        ArtistDTO updatedFields=new ArtistDTO(11,"darius","curtean","darius", LocalDate.of(2023,12,21),"Baia Mare","26-07-2003","present",12);

        Artist updatedEntity=new Artist("darius","curtean","darius", LocalDate.of(2023,12,21),"Baia Mare","26-07-2003","present",new ArrayList<>());
        updatedEntity.setUserId(12);
        updatedEntity.setId(11);

        when(artistRepository.findById(11)).thenReturn(Optional.of(toUpdate));
        when(artistRepository.save(any(Artist.class))).thenReturn(updatedEntity);

        ArtistViewDTO expected=new ArtistViewDTO(11,"darius 'darius' curtean", LocalDate.of(2023,12,21),"Baia Mare","26-07-2003","present");

        ArtistViewDTO result= artistService.updateArtist(11,updatedFields);
        assertEquals(expected,result);
    }

    @Test
    public void testFindArtist_Successful(){
        Artist toFind=new Artist("toni","curtean","toni", LocalDate.of(2023,12,21),"Cluj","26-07-2003","present",new ArrayList<>());
        toFind.setId(11);
        toFind.setUserId(12);

        when(artistRepository.findById(11)).thenReturn(Optional.of(toFind));
        ArtistViewDTO expected=new ArtistViewDTO(11,"toni 'toni' curtean", LocalDate.of(2023,12,21),"Cluj","26-07-2003","present");

        ArtistViewDTO result=artistService.getArtistById(11);
        assertEquals(expected,result);
    }

    @Test
    public void testFindArtist_Failed(){
        Integer id=1;

        when(artistRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,()->{
            artistService.getArtistById(id);
        });
    }


    @org.springframework.boot.test.context.TestConfiguration
    static class TestConfiguration{

        @MockBean
        private ArtistRepository artistRepository;

        @Bean
        public ArtistMapper artistMapper(){
            return new ArtistMapperImpl();
        }

        @Bean
        public ArtistService ArtistService(){
            return new ArtistService(artistRepository,artistMapper());
        }


    }

}
