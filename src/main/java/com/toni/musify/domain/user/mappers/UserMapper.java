package com.toni.musify.domain.user.mappers;

import com.toni.musify.domain.playlist.mappers.PlaylistMapper;
import com.toni.musify.domain.user.model.User;
import com.toni.musify.domain.user.model.UserDTO;
import com.toni.musify.domain.user.model.UserViewDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses= PlaylistMapper.class)
public abstract class UserMapper {

    public abstract UserDTO toDTO(User user);

    public abstract User toEntity(UserDTO user);

    public abstract UserViewDTO toViewDTO(User user);

    public abstract User toEntity(UserViewDTO userViewDTO);

}
