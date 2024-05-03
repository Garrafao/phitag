package de.garrafao.phitag.infrastructure.persistence.jpa.notification;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import de.garrafao.phitag.domain.core.PageRequestWraper;
import de.garrafao.phitag.domain.notification.Notification;
import de.garrafao.phitag.domain.notification.NotificationRepository;
import de.garrafao.phitag.domain.user.User;

@Repository
public class NotificationRepositoryBridge implements NotificationRepository {

    private final NotificationRepositoryJpa notificationRepositoryJpa;

    @Autowired
    public NotificationRepositoryBridge(NotificationRepositoryJpa notificationRepositoryJpa) {
        this.notificationRepositoryJpa = notificationRepositoryJpa;
    }

    @Override
    public Notification save(Notification notification) {
        return this.notificationRepositoryJpa.save(notification);
    }

    @Override
    public Page<Notification> findByUserAndRead(User user, boolean read, PageRequestWraper pagewraper) {
        return this.notificationRepositoryJpa.findByUserAndRead(user, read, pagewraper.getPageRequest());
    }

    @Override
    public Page<Notification> findByUser(User user, PageRequestWraper pagewraper) {
        return this.notificationRepositoryJpa.findByUser(user, pagewraper.getPageRequest());
    }

    @Override
    public Optional<Notification> findById(final Integer id) {
        return this.notificationRepositoryJpa.findById(id);
    }


    
    
}
