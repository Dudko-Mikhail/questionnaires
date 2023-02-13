package by.dudko.questionnaires.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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

    @Enumerated(value = EnumType.STRING)
    private Type type;

    @org.hibernate.annotations.Type(type = "string-array")
    private String[] options;
    private boolean isRequired;
    private boolean isActive;

    public enum Type {
        SINGLE_LINE_TEXT, MULTILINE_TEXT, RADIO_BUTTON, CHECKBOX, COMBOBOX, DATE
    }
}
