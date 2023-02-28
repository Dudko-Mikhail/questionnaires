package by.dudko.questionnaires.service.impl;

import by.dudko.questionnaires.dto.FieldDto;
import by.dudko.questionnaires.dto.PageResponse;
import by.dudko.questionnaires.exception.EntityNotFoundException;
import by.dudko.questionnaires.mapper.impl.FieldMapper;
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
    private final UserRepository userRepository;
    private final FieldTypeRepository fieldTypeRepository;
    private final FieldMapper fieldMapper;

    @Override
    public PageResponse<FieldDto> findAllByUserId(long userId, Pageable pageable) {
        return PageResponse.of(fieldRepository.findAllByUserIdOrderByOrder(userId, pageable)
                .map(fieldMapper::map));
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
    public FieldDto save(long userId, FieldDto fieldDto) {
        return userRepository.findById(userId)
                .map(user -> {
                    Field field = fieldMapper.reverseMap(fieldDto);
                    field.setType(fieldTypeRepository.findByValue(fieldDto.getType()).get());
                    int order = fieldRepository.findMaxOrderByUserId(userId) + 1;
                    field.setUser(user);
                    field.setOrder(order);
                    return fieldRepository.save(field);
                })
                .map(fieldMapper::map)
                .orElseThrow(() -> EntityNotFoundException.of(User.class, Field.Fields.id, Long.toString(userId)));
    }

    @Transactional
    @Override
    public FieldDto update(FieldDto fieldDto) {
        long fieldId = fieldDto.getId();
        return fieldRepository.findById(fieldId)
                .map(field -> {
                    field.setType(fieldTypeRepository.findByValue(fieldDto.getType()).get());
                    fieldMapper.reverseMap(fieldDto, field);
                    return fieldDto;
                })
                .orElseThrow(() -> EntityNotFoundException.of(Field.class, Field.Fields.id, Long.toString(fieldId)));
    }

    @Transactional
    @Override
    public void deleteById(long fieldId) {
        Field field = fieldRepository.findById(fieldId)
                .orElseThrow(() -> EntityNotFoundException.of(Field.class, Field.Fields.id, Long.toString(fieldId)));
        fieldRepository.delete(field);
    }
}
