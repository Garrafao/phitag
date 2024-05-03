package de.garrafao.phitag.application.notification.command;

import java.util.List;

import lombok.Getter;

@Getter
public class UpdateNotificationCommand {

    private final List<Integer> notificationIds;

    UpdateNotificationCommand() {
        this.notificationIds = null;
    }

    public UpdateNotificationCommand(final List<Integer> notificationIds) {
        this.notificationIds = notificationIds;
    }

}
