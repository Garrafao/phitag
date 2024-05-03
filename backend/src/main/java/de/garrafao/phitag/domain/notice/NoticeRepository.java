package de.garrafao.phitag.domain.notice;

import java.util.List;
import java.util.Optional;

public interface NoticeRepository {
    
    Optional<Notice> findById(Integer id);


    Notice save(Notice notice);

    List<Notice> findAll();


    void delete(Notice notice);
}