package by.dudko.questionnaires.service.impl;

import by.dudko.questionnaires.dto.PageResponse;
import by.dudko.questionnaires.dto.field.FieldCreateEditDto;
import by.dudko.questionnaires.dto.field.FieldReadDto;
import by.dudko.questionnaires.exception.UserNotFoundException;
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
import java.util.Optional;
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
    public List<FieldReadDto> findAllByUserId(long userId) {
        return userRepository.findById(userId)
                .map(User::getFields)
                .map(fields -> fields.stream()
                        .map(fieldReadMapper::map)
                        .collect(Collectors.toList()))
                .orElseThrow(() -> UserNotFoundException.of(userId));

    }

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
    public Optional<FieldReadDto> save(long userId, FieldCreateEditDto createEditDto) {
        return userRepository.findById(userId)
                .map(user -> {
                    Field field = fieldCreateEditMapper.map(createEditDto);
                    int order = fieldRepository.findMaxOrderByUserId(userId) + 1;
                    field.setUser(user);
                    field.setOrder(order);
                    return fieldRepository.save(field);
                })
                .map(fieldReadMapper::map);
    }

    @Transactional
    @Override
    public Optional<FieldReadDto> update(long fieldId, FieldCreateEditDto createEditDto) {
        return fieldRepository.findById(fieldId)
                .map(field -> fieldCreateEditMapper.map(createEditDto, field))
                .map(fieldReadMapper::map);
    }

    @Transactional
    @Override
    public boolean deleteById(long fieldId) {
        return fieldRepository.findById(fieldId)
                .map(field -> {
                    fieldRepository.deleteById(fieldId);
                    return true;
                })
                .orElse(false);
    }
}
