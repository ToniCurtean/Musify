package com.toni.musify.domain.playlist.mappers;


import com.toni.musify.domain.playlist.model.Playlist;
import com.toni.musify.domain.playlist.model.PlaylistDTO;
import com.toni.musify.domain.playlist.model.PlaylistViewDTO;
import com.toni.musify.domain.song.mappers.SongMapper;
import com.toni.musify.domain.user.mappers.UserMapper;
import com.toni.musify.domain.user.model.User;
import com.toni.musify.domain.user.model.UserDTO;
import com.toni.musify.domain.user.model.UserViewDTO;
import com.toni.musify.domain.user.repository.UserRepository;
import com.toni.musify.domain.user.service.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel="spring",uses= {UserMapper.class, SongMapper.class})
public abstract class PlaylistMapper {

    public abstract PlaylistDTO toDTO(Playlist playlist);

    @Mapping(source="userId",target="owner.id")
    public abstract Playlist toEntity(PlaylistDTO playlist);

    public abstract PlaylistViewDTO toViewDTO(Playlist playlist);

    public abstract Playlist toEntity(PlaylistViewDTO playlistViewDTO);

    public abstract List<PlaylistViewDTO> map(List<Playlist> playlists);

}
