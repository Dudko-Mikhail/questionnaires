package by.dudko.questionnaires.service.impl;

import by.dudko.questionnaires.dto.PageResponse;
import by.dudko.questionnaires.dto.field.FieldCreateEditDto;
import by.dudko.questionnaires.dto.field.FieldReadDto;
import by.dudko.questionnaires.mapper.impl.field.FieldCreateEditMapper;
import by.dudko.questionnaires.mapper.impl.field.FieldReadMapper;
import by.dudko.questionnaires.model.Field;
import by.dudko.questionnaires.repository.FieldRepository;
import by.dudko.questionnaires.repository.UserRepository;
import by.dudko.questionnaires.service.FieldService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FieldServiceImpl implements FieldService {
    private final FieldRepository fieldRepository;
    private final FieldReadMapper fieldReadMapper;
    private final UserRepository userRepository;
    private final FieldCreateEditMapper fieldCreateEditMapper;

    @Override
    public PageResponse<FieldReadDto> findAllByUserId(long userId, Pageable pageable) {
        return PageResponse.of(fieldRepository.findAllByUserIdOrderByOrder(userId, pageable)
                        .map(fieldReadMapper::map));
    }

    @Transactional
    @Override
    public Optional<FieldReadDto> save(long userId, FieldCreateEditDto createEditDto) {
        return userRepository.findById(userId)
                .map(user -> {
                    Field field = fieldCreateEditMapper.map(createEditDto);
                    field.setUser(user);
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
    public boolean deleteById(long id) {
        return fieldRepository.findById(id)
                .map(field -> {
                    fieldRepository.deleteById(id);
                    return true;
                })
                .orElse(false);
    }
}
