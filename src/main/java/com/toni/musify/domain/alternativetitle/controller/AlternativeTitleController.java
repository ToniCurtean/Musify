package com.toni.musify.domain.alternativetitle.controller;


import com.toni.musify.domain.alternativetitle.model.AlternativeTitle;
import com.toni.musify.domain.alternativetitle.model.AlternativeTitleDTO;
import com.toni.musify.domain.alternativetitle.model.AlternativeTitleViewDTO;
import com.toni.musify.domain.alternativetitle.service.AlternativeTitleService;
import com.toni.musify.domain.user.model.UserRole;
import com.toni.musify.security.check.RolesAllowed;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Objects;

import static com.toni.musify.security.ApplicationConfig.getCurrentUserRole;

@RestController
@RequestMapping("/alternativetitles")
@AllArgsConstructor
public class AlternativeTitleController {

    private final AlternativeTitleService alternativeTitleService;

    @RolesAllowed(value={"ADMIN"})
    @PostMapping
    public AlternativeTitleViewDTO addAlternativeTitle(@RequestBody @Valid AlternativeTitleDTO alternativeTitleDTO){
        return alternativeTitleService.addAlternativeTitle(alternativeTitleDTO);
    }

    @RolesAllowed(value={"ADMIN"})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAlternativeTitle(@PathVariable @Positive @NotNull Integer id){
        alternativeTitleService.deleteAlternativeTitleById(id);
        return ResponseEntity.noContent().build();
    }

    @RolesAllowed(value={"ADMIN"})
    @PutMapping("/{id}")
    public AlternativeTitleViewDTO updateAlternativeTitle(@PathVariable @Positive @NotNull Integer id,@RequestParam @NotBlank String newTitle){
        return alternativeTitleService.updateAlternative(id,newTitle);
    }

    @RolesAllowed(value={"ADMIN","REGULAR"})
    @GetMapping("/song/{songId}")
    public List<AlternativeTitleViewDTO> findAlternativeTitlesForSong(@PathVariable @Positive @NotNull Integer songId){
        return alternativeTitleService.findAlternativeTitlesForSong(songId);
    }

}
