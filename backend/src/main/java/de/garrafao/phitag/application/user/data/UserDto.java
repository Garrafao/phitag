package de.garrafao.phitag.application.user.data;

import java.util.Set;
import java.util.stream.Collectors;

import de.garrafao.phitag.application.annotationtype.data.AnnotationTypeDto;
import de.garrafao.phitag.application.language.data.LanguageDto;
import de.garrafao.phitag.domain.user.User;
import lombok.NonNull;
import lombok.Getter;

@Getter
public class UserDto {

    private final String username;
    private final String displayname;

    private final Boolean enabled;
    private final Boolean isbot;

    private final Set<LanguageDto> languages;
    private final Set<AnnotationTypeDto> annotationTypes;
    private final String description;
    private final String prolific_id;


    private UserDto(@NonNull final String username,
            @NonNull final String displayname,
            @NonNull final Boolean enabled,
            @NonNull final Boolean isbot,
            @NonNull final Set<LanguageDto> languages,
            @NonNull final Set<AnnotationTypeDto> annotationTypes,
            final String description,
                    final String prolific_id) {
        this.username = username;
        this.displayname = displayname;

        this.enabled = enabled;
        this.isbot = isbot;

        this.languages = languages;
        this.annotationTypes = annotationTypes;
        this.description = description;
        this.prolific_id = prolific_id;
    }

    public static UserDto from(@NonNull final User user) {
        return new UserDto(
                user.getUsername(),
                user.getDisplayname(),
                user.isEnabled(),
                user.isIsbot(),
                user.getLanguages().stream().map(LanguageDto::from).collect(Collectors.toSet()),
                user.getAnnotationTypes().stream().map(AnnotationTypeDto::from).collect(Collectors.toSet()),
                user.getDescription(),
                user.getProlific_id());
    }

}
