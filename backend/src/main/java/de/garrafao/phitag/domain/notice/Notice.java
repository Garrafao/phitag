package de.garrafao.phitag.domain.notice;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "phitagnotice")
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "body", nullable = false)
    private String body;

    @Column(name = "isActive", nullable = false)
    private boolean isActive;

    public Notice(String username, String title, String body, boolean isActive) {
        this.username = username;
        this.title = title;
        this.body = body;
        this.isActive = isActive;
    }

    @Override
    public String toString() {
        return "Notice{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}
