package by.dudko.questionnaires.model;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Table(name = "fields")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(exclude = {"id", "user"})
public class Field {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "`order`")
    private int order;
    private String label;

    @ManyToOne
    @JoinColumn(name = "type_id", referencedColumnName = "id")
    private FieldType type;

    @Type(type = "string-array")
    private String[] options;
    private boolean isRequired;
    private boolean isActive;
}
