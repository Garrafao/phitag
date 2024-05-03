package de.garrafao.phitag.application.notification;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import de.garrafao.phitag.application.common.CommonService;
import de.garrafao.phitag.application.notification.command.UpdateNotificationCommand;
import de.garrafao.phitag.application.notification.data.PagedNotificationDto;
import de.garrafao.phitag.application.validation.ValidationService;
import de.garrafao.phitag.domain.core.PageRequestWraper;
import de.garrafao.phitag.domain.notification.Notification;
import de.garrafao.phitag.domain.notification.NotificationRepository;
import de.garrafao.phitag.domain.notification.error.NotificationException;
import de.garrafao.phitag.domain.user.User;

@Service
public class NotificationApplicationService {

    // Repository

    private final NotificationRepository notificationRepository;

    // Service

    private final CommonService commonService;

    private final ValidationService validationService;

    @Autowired
    public NotificationApplicationService(final NotificationRepository notificationRepository,
            final CommonService commonService, final ValidationService validationService) {
        this.notificationRepository = notificationRepository;

        this.commonService = commonService;
        this.validationService = validationService;
    }

    /**
     * Find all Notifications for a User
     * 
     * @param authenticationToken The authentication token
     * @param page                The page
     * @return Page of Notifications
     */
    public PagedNotificationDto findAllNotificationsForUser(final String authenticationToken, final boolean onlyunread, final int page) {
        final User requester = this.commonService.getUserByAuthenticationToken(authenticationToken);

        Page<Notification> notifications = null;
        if (onlyunread) {
            notifications = this.notificationRepository.findByUserAndRead(requester, false, new PageRequestWraper(50, page, "id"));
        } else {
            notifications = this.notificationRepository.findByUser(requester, new PageRequestWraper(50, page, "id"));
        }
        
        return PagedNotificationDto.from(notifications);
    }

    /**
     * Find all unread Notifications for a User
     * 
     * @param authenticationToken The authentication token
     * @param page                The page
     * @return Page of Notifications
     */
    public PagedNotificationDto findAllUnreadNotificationsForUser(final String authenticationToken, final int page) {
        final User requester = this.commonService.getUserByAuthenticationToken(authenticationToken);

        Page<Notification> notifications = this.notificationRepository.findByUserAndRead(requester, false, new PageRequestWraper(page, 50, "id"));

        return PagedNotificationDto.from(notifications);
    }

    /**
     * Mark a list of Notifications as read
     * 
     * @param authenticationToken The authentication token
     * @param notificationIds     The list of Notification IDs
     */
    @Transactional
    public void markNotificationsAsRead(final String authenticationToken, final UpdateNotificationCommand command) {
        final User requester = this.commonService.getUserByAuthenticationToken(authenticationToken);

        for (final Integer notificationId : command.getNotificationIds()) {
            final Notification notification = this.notificationRepository.findById(notificationId)
                    .orElseThrow(() -> new IllegalArgumentException("Notification not found"));

            // Check if the Notification belongs to the User
            if (!notification.getUser().equals(requester)) {
                throw new NotificationException("Notification does not belong to the User");
            }

            notification.setRead(true);
            this.notificationRepository.save(notification);
        }
    }
}
