package de.garrafao.phitag.infrastructure.persistence.jpa.notice;

import de.garrafao.phitag.domain.notice.Notice;
import de.garrafao.phitag.domain.notice.NoticeRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class NoticeRepositoryBridge implements NoticeRepository {

    private final NoticeRepositoryJpa noticeRepositoryJpa;

    public NoticeRepositoryBridge(NoticeRepositoryJpa noticeRepositoryJpa) {
        this.noticeRepositoryJpa = noticeRepositoryJpa;
    }


    @Override
    public Optional<Notice> findById(Integer id) {
        return noticeRepositoryJpa.findById(id);
    }

    @Override
    public Notice save(Notice notice) {
        return noticeRepositoryJpa.save(notice);
    }

    @Override
    public List<Notice> findAll() {
        return noticeRepositoryJpa.findAll();
    }

    @Override
    public void delete(Notice notice) {
        noticeRepositoryJpa.delete(notice);
    }

}
