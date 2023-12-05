package com.toni.musify.domain.alternativetitle.mappers;


import com.toni.musify.domain.alternativetitle.model.AlternativeTitle;
import com.toni.musify.domain.alternativetitle.model.AlternativeTitleDTO;
import com.toni.musify.domain.alternativetitle.model.AlternativeTitleViewDTO;
import com.toni.musify.domain.song.mappers.SongMapper;
import com.toni.musify.domain.user.model.User;
import com.toni.musify.domain.user.model.UserDTO;
import com.toni.musify.domain.user.model.UserViewDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class AlternativeTitleMapper {

    public abstract AlternativeTitleDTO toDTO(AlternativeTitle alternativeTitle);

    @Mapping(source="songId",target="song.id")
    public abstract AlternativeTitle toEntity(AlternativeTitleDTO alternativeTitle);

    public abstract AlternativeTitleViewDTO toViewDTO(AlternativeTitle alternativeTitle);

    public abstract AlternativeTitle toEntity(AlternativeTitleViewDTO alternativeTitleViewDTO);

    public abstract List<AlternativeTitleViewDTO> map(List<AlternativeTitle> alternativeTitles);

}
