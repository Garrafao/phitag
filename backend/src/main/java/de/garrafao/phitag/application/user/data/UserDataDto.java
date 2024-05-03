package de.garrafao.phitag.application.user.data;

import java.util.Set;
import java.util.stream.Collectors;

import de.garrafao.phitag.application.language.data.LanguageDto;
import de.garrafao.phitag.application.usecase.data.UsecaseDto;
import de.garrafao.phitag.application.visibility.data.VisibilityDto;
import de.garrafao.phitag.domain.user.User;
import lombok.NonNull;
import lombok.Getter;

@Getter
public class UserDataDto {

    private final String username;
    private final String displayname;
    private final String email;

    private final Boolean enabled;

    private final VisibilityDto visibility;
    private final UsecaseDto usecase;

    private final Set<LanguageDto> languages;
    private final String description;

    private UserDataDto(@NonNull final String username, @NonNull final String displayname, @NonNull final String email,
            @NonNull final Boolean enabled, @NonNull final VisibilityDto visibilityDto, @NonNull final UsecaseDto usecaseDto,
            @NonNull final Set<LanguageDto> languages, final String description) {
        this.username = username;
        this.displayname = displayname;
        this.email = email;

        this.enabled = enabled;
        this.visibility = visibilityDto;
        this.usecase = usecaseDto;

        this.languages = languages;
        this.description = description;
    }

    public static UserDataDto from(@NonNull final User user) {
        return new UserDataDto(user.getUsername(), user.getDisplayname(), user.getEmail(), user.isEnabled(),
                VisibilityDto.from(user.getVisibility()), UsecaseDto.from(user.getUsecase()),
                user.getLanguages().stream().map(LanguageDto::from).collect(Collectors.toSet()), user.getDescription());
    }

}
