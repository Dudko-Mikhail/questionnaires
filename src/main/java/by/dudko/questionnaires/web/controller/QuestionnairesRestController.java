package by.dudko.questionnaires.web.controller;

import by.dudko.questionnaires.dto.PageResponse;
import by.dudko.questionnaires.dto.questionnaire.QuestionnaireDto;
import by.dudko.questionnaires.dto.questionnaire.QuestionnaireFilter;
import by.dudko.questionnaires.service.QuestionnaireService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class QuestionnairesRestController {
    private final QuestionnaireService questionnaireService;

    @GetMapping("/questionnaires")
    public PageResponse<QuestionnaireDto> findAll(Pageable pageable, QuestionnaireFilter filter) {
        return questionnaireService.findAll(filter, pageable);
    }

    @GetMapping("/questionnaires/{id}")
    public QuestionnaireDto findById(@PathVariable long id) {
        return questionnaireService.findById(id);
    }

    @PreAuthorize("principal.id == #userId")
    @GetMapping("/users/{id}/questionnaires")
    public PageResponse<QuestionnaireDto> findAllByUserId(@PathVariable("id") long userId, Pageable pageable) {
        return questionnaireService.findAllByUserId(userId, pageable);
    }

    @PreAuthorize("principal.id == #userId")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/users/{id}/questionnaires")
    public QuestionnaireDto saveQuestionnaire(@PathVariable("id") long userId,
                                              @RequestBody @Validated QuestionnaireDto questionnaireDto) {
        return questionnaireService.save(userId, questionnaireDto);
    }

    @PreAuthorize("@questionnaireServiceImpl.isQuestionnaireOwner(principal.id, #id)")
    @PutMapping("/questionnaires/{id}")
    public QuestionnaireDto update(@PathVariable long id,
                                   @RequestBody @Validated QuestionnaireDto questionnaireDto) {
        return questionnaireService.update(id, questionnaireDto);
    }

    @PreAuthorize("@questionnaireServiceImpl.isQuestionnaireOwner(principal.id, #id)")
    @DeleteMapping("/questionnaires/{id}")
    public void deleteById(@PathVariable long id) {
        questionnaireService.deleteById(id);
    }

}
