package de.garrafao.phitag.application.notice.data;

import de.garrafao.phitag.domain.notice.Notice;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoticeDto {
    private final String title;
    private final String body;

    private final boolean isActive;

    public NoticeDto(String title, String body, boolean isActive) {
        this.title = title;
        this.body = body;
        this.isActive = isActive;
    }

    public static NoticeDto from(Notice notice) {
        return new NoticeDto(notice.getTitle(),
                notice.getBody(), notice.isActive());
    }

}
