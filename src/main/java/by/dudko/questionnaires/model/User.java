package by.dudko.questionnaires.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "email")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String email;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private String password;
    private boolean isActivated;
    private UUID verificationCode;

    @OneToMany(mappedBy = "user")
    @Builder.Default
    private List<Field> fields = new ArrayList<>();

    @OneToMany(mappedBy = "questionnaireOwner")
    @Builder.Default
    private List<Response> responses = new ArrayList<>();
}
