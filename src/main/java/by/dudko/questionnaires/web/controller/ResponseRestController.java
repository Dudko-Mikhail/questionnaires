package by.dudko.questionnaires.web.controller;

import by.dudko.questionnaires.dto.PageResponse;
import by.dudko.questionnaires.dto.ResponseDto;
import by.dudko.questionnaires.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ResponseRestController {
    private final SimpMessagingTemplate messagingTemplate;
    private final ResponseService responseService;

    @PreAuthorize("@questionnaireServiceImpl.isQuestionnaireOwner(principal.id, #questionnaireId)")
    @GetMapping("/questionnaires/{id}/responses")
    public PageResponse<ResponseDto> findAllByQuestionnaireId(@PathVariable("id") long questionnaireId,
                                                              Pageable pageable) {
        return responseService.findAllByQuestionnaireId(questionnaireId, pageable);
    }

    @PostMapping("/questionnaires/{id}/responses")
    public ResponseDto saveResponse(@PathVariable("id") long questionnaireId,
                                    @RequestBody @Validated ResponseDto responseDto) {
        ResponseDto savedResponse = responseService.save(questionnaireId, responseDto);
        this.messagingTemplate.convertAndSend("/topic/responses", savedResponse);
        return savedResponse;
    }
}
