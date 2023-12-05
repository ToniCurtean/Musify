package com.toni.musify.domain.alternativetitle.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.toni.musify.domain.BaseEntity;
import com.toni.musify.domain.UserIdListener;
import com.toni.musify.domain.song.model.Song;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="AlternativeTitle")
@Table(name="alternativetitles")
@EntityListeners(UserIdListener.class)
public class AlternativeTitle extends BaseEntity{

    @Size(min=1,max=30)
    @Column(name="title_s",nullable = false)
    private String alternativeTitle;

    @Size(min=4,max=30)
    private String language;

    @JsonBackReference
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_s")
    private Song song;

}
