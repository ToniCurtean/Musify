package com.toni.musify.domain.artist.mappers;

import com.toni.musify.domain.artist.model.Artist;
import com.toni.musify.domain.artist.model.ArtistDTO;
import com.toni.musify.domain.artist.model.ArtistViewDTO;
import com.toni.musify.domain.playlist.model.Playlist;
import com.toni.musify.domain.playlist.model.PlaylistViewDTO;
import com.toni.musify.domain.song.mappers.SongMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class ArtistMapper {

    public abstract ArtistDTO toDTO(Artist artist);
    public abstract Artist toEntity(ArtistDTO artistDTO);

    @Mapping(target="presentationName",source="artist",qualifiedByName = "getPresentationName")
    public abstract ArtistViewDTO toViewDTO(Artist artist);

    @Named("getPresentationName")
    protected String getPresentationName(Artist artist){
        if(artist==null)
            return "";
        return artist.getFirstName()+" '"+artist.getStageName()+"' "+artist.getLastName();
    }

    public abstract List<ArtistViewDTO> map(List<Artist> artists);

}
