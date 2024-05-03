package de.garrafao.phitag.application.notification.data;

import de.garrafao.phitag.domain.notification.Notification;
import lombok.Getter;

@Getter
public class NotificationDto {

    private Integer id;
    private String message;
    private boolean read;

    public NotificationDto() {
    }

    public NotificationDto(final Integer id, final String message, final boolean read) {
        this.id = id;
        this.message = message;
        this.read = read;
    }

    public static NotificationDto from(final Notification notification) {
        return new NotificationDto(notification.getId(), notification.getNotificationDescription(), notification.isRead());
    }
    
}
