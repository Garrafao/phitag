package de.garrafao.phitag.infrastructure.persistence.jpa.notification;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import de.garrafao.phitag.domain.notification.Notification;
import de.garrafao.phitag.domain.user.User;

public interface NotificationRepositoryJpa extends JpaRepository<Notification, Integer>, JpaSpecificationExecutor<Notification> {
    
    public Notification save(final Notification notification);

    public Optional<Notification> findById(final String id);

    public Page<Notification> findByUser(final User user, final Pageable pageable);

    public Page<Notification> findByUserAndRead(final User user, final boolean read, final Pageable pageable);
}
