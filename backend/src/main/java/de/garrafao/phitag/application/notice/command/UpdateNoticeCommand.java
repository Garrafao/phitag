package de.garrafao.phitag.application.notice.command;

public class UpdateNoticeCommand {

    private final  Integer id;

    private final String title;

    private final String body;

    private final boolean isActive;


    public UpdateNoticeCommand(Integer id, String title, String body, boolean isActive) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.isActive = isActive;
    }
}
