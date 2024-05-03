package de.garrafao.phitag.application.joblisting.data.dto;

import java.util.List;
import java.util.stream.Collectors;

import de.garrafao.phitag.domain.joblisting.Joblisting;
import lombok.Getter;

@Getter
public class PersonalJoblistingDto {

    private final JoblistingIdDto id;

    private final String displayname;
    private final String phase;

    private final boolean open;
    private final String description;
    private final List<String> waitinglist; // usernames

    private PersonalJoblistingDto(final JoblistingIdDto id, final String displayname, final String phase, final boolean open, final String description,
            final List<String> waitinglist) {
        this.id = id;
        this.displayname = displayname;
        this.phase = phase;
        this.open = open;
        this.description = description;
        this.waitinglist = waitinglist;
    }

    public static PersonalJoblistingDto from(final Joblisting joblisting) {
        List<String> waitinglist = joblisting.getWaitinglist().stream().map(user -> user.getUsername())
                .collect(Collectors.toList());
        return new PersonalJoblistingDto(
                JoblistingIdDto.from(joblisting.getId()),
                joblisting.getDisplayname(),
                joblisting.getPhase(),
                joblisting.isOpen(),
                joblisting.getDescription(),
                waitinglist);
    }

}
