package de.garrafao.phitag.application.notice;

import de.garrafao.phitag.application.common.CommonService;
import de.garrafao.phitag.application.notice.command.CreateNoticeCommand;
import de.garrafao.phitag.application.notice.data.NoticeDto;
import de.garrafao.phitag.domain.notice.Notice;
import de.garrafao.phitag.domain.notice.NoticeRepository;
import de.garrafao.phitag.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NoticeApplicationService {

    private final NoticeRepository noticeRepository;
    private  final  CommonService commonService;

    @Autowired
    public NoticeApplicationService(final NoticeRepository noticeRepository, final CommonService commonService) {
        this.noticeRepository = noticeRepository;
        this.commonService = commonService;
    }


    public List<NoticeDto> getAllNotice() {
        return noticeRepository.findAll().stream()
                .map(NoticeDto::from)
                .collect(Collectors.toList());
    }

     public Optional<NoticeDto> getNoticeById(final Integer id){
        return  this.noticeRepository.findById(id).map(NoticeDto::from);
     }


    @Transactional
    public void create(final String authenticationToken, final CreateNoticeCommand command) {
        User owner = this.commonService.getUserByAuthenticationToken(authenticationToken);
        String username =owner.getUsername();
        noticeRepository.save(new Notice(username, command.getTitle(), command.getBody(), command.isActive()));
    }

    @Transactional
    public void update(final String authenticationToken, final String owner){

        //fetching user information
        final User requester = this.commonService.getUserByAuthenticationToken(authenticationToken);

    }

    private void validateUpdateCommand(final Notice notice,final CreateNoticeCommand command){
        if (command.getTitle()!= null && !command.getTitle().isEmpty() && !command.getTitle().isBlank()) {
            notice.setTitle(command.getTitle());
        }
        if (command.getBody() != null && !command.getBody().isEmpty() && !command.getBody().isBlank()) {
            notice.setBody(command.getBody());
        }
           notice.setActive(command.isActive());

    }



}
