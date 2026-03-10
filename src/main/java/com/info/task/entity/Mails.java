package com.info.task.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "T_MAILS")
public class Mails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false, length = 10)
    private Long id;

    // Many-to-One relationship to People
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "T_PEOPLE_ID", nullable = false)
    private People people;

    @Column(name = "EMAIL_TYPE", nullable = false)
    private String emailType;

    @Email(message = "Invalid e-mail address")
    @Column(name = "EMAIL", length = 40)
    private String email;

    public Mails() {
    }
}
