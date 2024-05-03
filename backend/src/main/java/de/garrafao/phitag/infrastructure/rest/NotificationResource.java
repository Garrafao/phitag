package de.garrafao.phitag.infrastructure.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.garrafao.phitag.application.notification.NotificationApplicationService;
import de.garrafao.phitag.application.notification.command.UpdateNotificationCommand;
import de.garrafao.phitag.application.notification.data.PagedNotificationDto;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping(value = "/api/v1/notification")
public class NotificationResource {

    private final NotificationApplicationService notificationApplicationService;

    @Autowired
    public NotificationResource(NotificationApplicationService notificationApplicationService) {
        this.notificationApplicationService = notificationApplicationService;
    }

    /**
     * Find all Notifications for a User
     * 
     * @param authenticationToken The authentication token
     * @param page                The page
     */
    @RequestMapping(value = "")
    public PagedNotificationDto findAllNotificationsForUser(
            @RequestHeader(value = "Authorization") final String authenticationToken,
            @RequestParam(value = "onlyunread", required = false, defaultValue = "false") final boolean onlyunread,
            @RequestParam(value = "page", required = false, defaultValue = "0") final int page) {

        return this.notificationApplicationService.findAllNotificationsForUser(authenticationToken, onlyunread, page);
    }

    /**
     * Find all non read Notifications
     * 
     * @param authenticationToken The authentication token
     * @param page                The page
     */
    @RequestMapping(value = "/unread")
    public PagedNotificationDto findAllUnreadNotificationsForUser(
            @RequestHeader(value = "Authorization") final String authenticationToken,
            @RequestParam(value = "page", required = false, defaultValue = "0") final int page) {

        return this.notificationApplicationService.findAllUnreadNotificationsForUser(authenticationToken, page);
    }

    /**
     * Mark Notifications as read
     * 
     * @param authenticationToken The authentication token
     * @param notificationIds     The notification ids
     */
    @PostMapping(value = "/read")
    public void markNotificationsAsRead(
            @RequestHeader(value = "Authorization") final String authenticationToken,
            @RequestBody final UpdateNotificationCommand command) {
        this.notificationApplicationService.markNotificationsAsRead(authenticationToken, command);
    }

}
