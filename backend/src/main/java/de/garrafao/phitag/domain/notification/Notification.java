package de.garrafao.phitag.domain.notification;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.Validate;

import de.garrafao.phitag.domain.user.User;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "phitagnotification")
@Getter
public class Notification {
    
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "notification_description")
    private String notificationDescription;

    @ManyToOne
    @JoinColumn(name = "notification_user")
    private User user;

    @Setter
    @Column(name = "notification_read")
    private boolean read;

    Notification() {
    }

    public Notification(
            final User user,
            final String notification) {
        Validate.notNull(user);
        Validate.notNull(notification);

        this.user = user;
        this.notificationDescription = notification;
    }

    @Override
    public String toString() {
        return "Notification [id=" + id + ", user=" + user + ", notification=" + notificationDescription + "]";
    }

    @Override
    public boolean equals(final Object object) {
        if (object == null) {
            return false;
        }
        if (object == this) {
            return true;
        }
        if (object.getClass() != getClass()) {
            return false;
        }
        final Notification other = (Notification) object;
        return id.equals(other.id);
    }

}
