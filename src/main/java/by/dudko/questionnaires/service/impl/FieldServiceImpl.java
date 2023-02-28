package by.dudko.questionnaires.service.impl;

import by.dudko.questionnaires.dto.PageResponse;
import by.dudko.questionnaires.dto.field.FieldCreateEditDto;
import by.dudko.questionnaires.dto.field.FieldReadDto;
import by.dudko.questionnaires.exception.EntityNotFoundException;
import by.dudko.questionnaires.mapper.impl.field.FieldCreateEditMapper;
import by.dudko.questionnaires.mapper.impl.field.FieldReadMapper;
import by.dudko.questionnaires.model.Field;
import by.dudko.questionnaires.model.FieldType;
import by.dudko.questionnaires.model.User;
import by.dudko.questionnaires.repository.FieldRepository;
import by.dudko.questionnaires.repository.FieldTypeRepository;
import by.dudko.questionnaires.repository.UserRepository;
import by.dudko.questionnaires.service.FieldService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FieldServiceImpl implements FieldService {
    private final FieldRepository fieldRepository;
    private final FieldReadMapper fieldReadMapper;
    private final UserRepository userRepository;
    private final FieldTypeRepository fieldTypeRepository;
    private final FieldCreateEditMapper fieldCreateEditMapper;

    @Override
    public PageResponse<FieldReadDto> findAllByUserId(long userId, Pageable pageable) {
        return PageResponse.of(fieldRepository.findAllByUserIdOrderByOrder(userId, pageable)
                .map(fieldReadMapper::map));
    }

    @Override
    public List<String> findAllFieldTypes() {
        return fieldTypeRepository.findAll()
                .stream()
                .map(FieldType::getValue)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public FieldReadDto save(long userId, FieldCreateEditDto createEditDto) {
        return userRepository.findById(userId)
                .map(user -> {
                    Field field = fieldCreateEditMapper.map(createEditDto);
                    field.setType(fieldTypeRepository.findByValue(createEditDto.getType()).get());
                    int order = fieldRepository.findMaxOrderByUserId(userId) + 1;
                    field.setUser(user);
                    field.setOrder(order);
                    return fieldRepository.save(field);
                })
                .map(fieldReadMapper::map)
                .orElseThrow(() -> EntityNotFoundException.of(User.class, "id", Long.toString(userId)));
    }

    @Transactional
    @Override
    public FieldReadDto update(long fieldId, FieldCreateEditDto createEditDto) {
        return fieldRepository.findById(fieldId)
                .map(field -> fieldCreateEditMapper.map(createEditDto, field))
                .map(fieldReadMapper::map)
                .orElseThrow(() -> EntityNotFoundException.of(Field.class, "id", Long.toString(fieldId)));
    }

    @Transactional
    @Override
    public void deleteById(long fieldId) {
        Field field = fieldRepository.findById(fieldId)
                .orElseThrow(() -> EntityNotFoundException.of(Field.class, "id", Long.toString(fieldId)));
        fieldRepository.delete(field);
    }
}
