package de.garrafao.phitag.domain.notification;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import de.garrafao.phitag.domain.core.PageRequestWraper;
import de.garrafao.phitag.domain.user.User;

public interface NotificationRepository {
    
    Notification save(final Notification notification);

    Optional<Notification> findById(final Integer id);

    Page<Notification> findByUser(final User user, final PageRequestWraper pagewraper);

    Page<Notification> findByUserAndRead(final User user, final boolean read, final PageRequestWraper pagewraper);
}
