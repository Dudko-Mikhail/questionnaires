package by.dudko.questionnaires.controller;

import by.dudko.questionnaires.dto.field.FieldCreateEditDto;
import by.dudko.questionnaires.dto.field.FieldReadDto;
import by.dudko.questionnaires.service.FieldService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
@RequiredArgsConstructor
public class FieldRestController {
    private final FieldService fieldService;

    @GetMapping("/users/{id}/fields")
    public List<FieldReadDto> findUserFields(@PathVariable("id") long userId) {
        return fieldService.findAllByUserId(userId);
    }

    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping("/users/{id}/fields")
    public long saveField(@PathVariable("id") long userId, @RequestBody @Validated FieldCreateEditDto createEditDto) {
        return fieldService.save(userId, createEditDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @PostMapping("/fields/{id}")
    public void updateField(@PathVariable long id, @RequestBody @Validated FieldCreateEditDto createEditDto) {
        if (!fieldService.update(id, createEditDto)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @DeleteMapping("/fields/{id}")
    public void deleteById(@PathVariable long id) {
        if (!fieldService.deleteById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
