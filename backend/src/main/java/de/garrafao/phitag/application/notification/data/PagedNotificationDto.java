package de.garrafao.phitag.application.notification.data;

import java.util.List;

import org.springframework.data.domain.Page;

import de.garrafao.phitag.domain.notification.Notification;
import lombok.Getter;

@Getter
public class PagedNotificationDto {

    private final List<NotificationDto> content;

    private final int page;
    private final int size;
    private final long totalElements;
    private final int totalPages;

    public PagedNotificationDto(List<NotificationDto> content, int page, int size, long totalElements, int totalPages) {
        this.content = content;
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }

    public static PagedNotificationDto empty() {
        return new PagedNotificationDto(List.of(), 0, 0, 0, 0);
    }

    public static PagedNotificationDto from(final Page<Notification> page) {
        return new PagedNotificationDto(
                page.getContent().stream().map(NotificationDto::from).toList(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages());
    }


    
}
