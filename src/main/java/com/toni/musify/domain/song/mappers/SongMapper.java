package com.toni.musify.domain.song.mappers;

import com.toni.musify.domain.alternativetitle.mappers.AlternativeTitleMapper;
import com.toni.musify.domain.artist.mappers.ArtistMapper;
import com.toni.musify.domain.playlist.mappers.PlaylistMapper;
import com.toni.musify.domain.song.model.Song;
import com.toni.musify.domain.song.model.SongDTO;
import com.toni.musify.domain.song.model.SongViewDTO;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring",uses={ArtistMapper.class, AlternativeTitleMapper.class})
public abstract class SongMapper {

    public abstract SongDTO toDTO(Song song);

    public abstract Song toEntity(SongDTO songDTO);

    public abstract SongViewDTO toViewDTO(Song song);

    public abstract Song toEntity(SongViewDTO songViewDTO);

    public abstract List<SongViewDTO> map(List<Song> songs);

}
