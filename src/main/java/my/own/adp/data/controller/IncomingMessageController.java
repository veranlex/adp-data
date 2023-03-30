package my.own.adp.data.controller;

import lombok.RequiredArgsConstructor;
import my.own.adp.data.dto.IncomingMsgJournalDto;
import my.own.adp.data.model.IncomingMsgJournalIdsRequest;
import my.own.adp.data.model.IncomingMsgJournalStatusesRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import my.own.adp.data.service.impl.IncomingMsgJournalServiceImpl;
import my.own.adp.data.service.impl.RabbitMsgJournalServiceImpl;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/incoming-message")
public class IncomingMessageController {

    private final IncomingMsgJournalServiceImpl journalServiceImpl;
    private final RabbitMsgJournalServiceImpl rabbitMsgJournalService;

    @PostMapping("/messages-by-id")
    @PreAuthorize("hasAnyRole('partner')")
    public ResponseEntity<List<IncomingMsgJournalDto>> getIncomingMsgJournalDtoByIds(@Valid @RequestBody IncomingMsgJournalIdsRequest message) {
        List<IncomingMsgJournalDto> listMsgJournalDto = journalServiceImpl.getMsgJournalDtoByIds(message);
        return new ResponseEntity<>(listMsgJournalDto, HttpStatus.OK);

    }

    @PostMapping("/messages-by-status")
    @PreAuthorize("hasAnyRole('partner')")
    public ResponseEntity<List<IncomingMsgJournalDto>> getIncomingMsgJournalDtoByStatuses(@Valid @RequestBody IncomingMsgJournalStatusesRequest message) {
        List<IncomingMsgJournalDto> listMsgJournalDto = journalServiceImpl.getMsgJournalDtoByStatuses(message);
        return new ResponseEntity<>(listMsgJournalDto, HttpStatus.OK);
    }

    @PostMapping("/send-by-id")
    @PreAuthorize("hasAnyRole('partner')")
    public void sendIncomingMsgJournalByIds(@RequestBody @Size(min = 1) List<String> ids) {
        rabbitMsgJournalService.sendAndReceiveMsgJournalByIds(ids);
    }

    @PostMapping("/send-by-status")
    @PreAuthorize("hasAnyRole('partner')")
    public void sendIncomingMsgJournalByStatus(@RequestBody @Size(min = 1) List<String> statuses) {
        rabbitMsgJournalService.sendAndReceiveMsgJournalByStatuses(statuses);
    }

}
