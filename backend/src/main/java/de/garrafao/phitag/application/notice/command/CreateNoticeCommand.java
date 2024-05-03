package de.garrafao.phitag.application.notice.command;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.Validate;

@Getter
@Setter
public class CreateNoticeCommand {

    private final String title;

    private final String body;

    private final boolean isActive;

    public CreateNoticeCommand(final String title, final String username, String username1, final String body, final boolean isActive) {
        Validate.notBlank(title, "title cannot be blank");
        Validate.notBlank(body, "body cannot be blank");
        this.title = title;
        this.body = body;
        this.isActive = isActive;
    }
}
