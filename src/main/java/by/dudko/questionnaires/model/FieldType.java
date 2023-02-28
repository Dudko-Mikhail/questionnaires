package by.dudko.questionnaires.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "field_types")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@EqualsAndHashCode(of = "value")
public class FieldType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "type")
    private String value;
}
