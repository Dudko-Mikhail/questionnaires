package by.dudko.questionnaires.web.controller;

import by.dudko.questionnaires.dto.FieldDto;
import by.dudko.questionnaires.dto.PageResponse;
import by.dudko.questionnaires.service.FieldService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FieldRestController {
    private final FieldService fieldService;

    @GetMapping("/users/{id}/fields")
    public PageResponse<FieldDto> findByUserId(@PathVariable("id") long userId, Pageable pageable) {
        return fieldService.findAllByUserId(userId, pageable);
    }

    @GetMapping("/fields/types")
    public List<String> findAllFieldTypes() {
        return this.fieldService.findAllFieldTypes();
    }

    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping("/users/{id}/fields")
    @PreAuthorize("principal.id == #userId")
    public FieldDto saveField(@PathVariable("id") long userId, @RequestBody @Validated FieldDto fieldDto) {
        return fieldService.save(userId, fieldDto);
    }

    @PutMapping("/fields/{id}")
    @PreAuthorize("@fieldRepository.isFieldOwner(principal.id, #id)")
    public FieldDto updateField(@PathVariable long id, @RequestBody @Validated FieldDto fieldDto) {
        fieldDto.setId(id);
        return fieldService.update(fieldDto);
    }

    @DeleteMapping("/fields/{id}")
    @PreAuthorize("@fieldRepository.isFieldOwner(principal.id, #id)")
    public void deleteById(@PathVariable("id") long id) {
        fieldService.deleteById(id);
    }
}
