package by.dudko.questionnaires.service.impl;

import by.dudko.questionnaires.dto.field.FieldCreateEditDto;
import by.dudko.questionnaires.dto.field.FieldReadDto;
import by.dudko.questionnaires.mapper.impl.field.FieldCreateEditMapper;
import by.dudko.questionnaires.mapper.impl.field.FieldReadMapper;
import by.dudko.questionnaires.repository.FieldRepository;
import by.dudko.questionnaires.repository.UserRepository;
import by.dudko.questionnaires.service.FieldService;
import lombok.RequiredArgsConstructor;
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
    private final FieldCreateEditMapper fieldCreateEditMapper;

    @Override
    public List<FieldReadDto> findAllByUserId(long userId) {
        return fieldRepository.findAllByUserIdOrderByOrder(userId).stream()
                .map(fieldReadMapper::map)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public Optional<FieldReadDto> save(long userId, FieldCreateEditDto createEditDto) {
        if (userRepository.findById(userId).isPresent()) {
            return Optional.of(createEditDto)
                    .map(fieldCreateEditMapper::map)
                    .map(fieldRepository::save)
                    .map(fieldReadMapper::map);
        }
        return Optional.empty();
    }

    @Transactional
    @Override
    public Optional<FieldReadDto> update(long fieldId, FieldCreateEditDto createEditDto) {
        return fieldRepository.findById(fieldId)
                .map(field -> {
                            fieldCreateEditMapper.map(createEditDto, field);
                            return fieldReadMapper.map(field);
                        }
                );
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
