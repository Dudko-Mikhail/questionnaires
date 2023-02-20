package by.dudko.questionnaires.dto;

import lombok.Builder;
import lombok.Value;
import org.springframework.data.domain.Page;

import java.util.List;

@Value
public class PageResponse<T> {
    List<T> content;
    Metadata metadata;

    public static <T> PageResponse<T> of(Page<T> page) {
        Metadata metadata = Metadata.builder()
                .page(page.getNumber() + 1)
                .size(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .numberOfElements(page.getNumberOfElements())
                .build();
        return new PageResponse<>(page.getContent(), metadata);
    }

    @Value
    @Builder
    public static class Metadata {
        int page;
        int size;
        long totalElements;
        int numberOfElements;
        int totalPages;
    }
}