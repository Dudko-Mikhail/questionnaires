package by.dudko.questionnaires.web.controller;

import by.dudko.questionnaires.dto.PageResponse;
import by.dudko.questionnaires.dto.ResponseDto;
import by.dudko.questionnaires.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
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
    private final ResponseService responseService;

    @GetMapping("/users/{id}/responses")
    @PreAuthorize("principal.id == #id")
    public PageResponse<ResponseDto> findAllByUserId(@PathVariable long id, Pageable pageable) {
        return responseService.findAllByUserId(id, pageable);
    }

    @PostMapping("/users/{id}/responses")
    public ResponseDto saveResponse(@PathVariable long id, @RequestBody @Validated ResponseDto responseDto) {
        return responseService.save(id, responseDto);
    }
}
