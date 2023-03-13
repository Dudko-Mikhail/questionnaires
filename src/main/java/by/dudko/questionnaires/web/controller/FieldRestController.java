package by.dudko.questionnaires.web.controller;

import by.dudko.questionnaires.dto.FieldDto;
import by.dudko.questionnaires.dto.PageResponse;
import by.dudko.questionnaires.service.FieldService;
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

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FieldRestController {
    private final FieldService fieldService;

    @GetMapping("/questionnaires/{id}/fields")
    public PageResponse<FieldDto> findByUserId(@PathVariable("id") long questionnaireId, Pageable pageable) {
        return fieldService.findAllByQuestionnaireId(questionnaireId, pageable);
    }

    @GetMapping("/fields/types")
    public List<String> findAllFieldTypes() {
        return this.fieldService.findAllFieldTypes();
    }

    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping("/questionnaires/{id}/fields")
    @PreAuthorize("@questionnaireServiceImpl.isQuestionnaireOwner(principal.id, #questionnaireId)")
    public FieldDto saveField(@PathVariable("id") long questionnaireId, @RequestBody @Validated FieldDto fieldDto) {
        return fieldService.save(questionnaireId, fieldDto);
    }

    @PutMapping("/fields/{id}")
    @PreAuthorize("@fieldServiceImpl.isFieldOwner(principal.id, #id)")
    public FieldDto updateField(@PathVariable long id, @RequestBody @Validated FieldDto fieldDto) {
        fieldDto.setId(id);
        return fieldService.update(fieldDto);
    }

    @DeleteMapping("/fields/{id}")
    @PreAuthorize("@fieldServiceImpl.isFieldOwner(principal.id, #id)")
    public void deleteById(@PathVariable("id") long id) {
        fieldService.deleteById(id);
    }
}
