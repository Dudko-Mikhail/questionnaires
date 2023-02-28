package by.dudko.questionnaires.web.controller;

import by.dudko.questionnaires.dto.PageResponse;
import by.dudko.questionnaires.dto.ResponseDto;
import by.dudko.questionnaires.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ResponseRestController {
    private final SimpMessagingTemplate messagingTemplate;
    private final ResponseService responseService;

    @GetMapping("/users/{id}/responses")
    @PreAuthorize("principal.id == #id")
    public PageResponse<ResponseDto> findAllByUserId(@PathVariable long id, Pageable pageable) {
        return responseService.findAllByUserId(id, pageable);
    }

    @PostMapping("/users/{id}/responses")
    public ResponseDto saveResponse(@PathVariable long id, @RequestBody @Validated ResponseDto responseDto) {
        ResponseDto savedResponse = responseService.save(id, responseDto);
        this.messagingTemplate.convertAndSend("/topic/responses", savedResponse);
        return savedResponse;
    }
}
