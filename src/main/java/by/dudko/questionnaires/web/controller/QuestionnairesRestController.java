package by.dudko.questionnaires.web.controller;

import by.dudko.questionnaires.dto.PageResponse;
import by.dudko.questionnaires.dto.Questionnaire;
import by.dudko.questionnaires.service.QuestionnaireService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/api/questionnaires")
@RequiredArgsConstructor
public class QuestionnairesRestController {
    private final QuestionnaireService questionnaireService;

    @GetMapping
    public PageResponse<Questionnaire> findAll(Pageable pageable) {
        return questionnaireService.findAllQuestionnaires(pageable);
    }
}
