package de.garrafao.phitag.infrastructure.persistence.jpa.notice;

import de.garrafao.phitag.domain.notice.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepositoryJpa extends JpaRepository<Notice, Integer> {
    


}
