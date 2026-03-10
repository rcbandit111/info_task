package com.info.task.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "T_PEOPLE")
public class People implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false, length = 10)
    private Long id;

    @Column(name = "FULL_NAME", nullable = false, length = 90)
    @Pattern(
            regexp = "^[\\p{L}\\s-]+$",
            message = "Only letters, empty space and '-' are allowed"
    )
    private String fullName;

    @Column(name = "PIN")
    @Pattern(
            regexp = "^\\d{10}$",
            message = "Exactly 10 letters are allowed"
    )
    private String pin;

    // One-to-Many to Addresses
    @OneToMany(mappedBy = "people", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Addresses> addresses = new ArrayList<>();

    // One-to-Many to Mails
    @OneToMany(mappedBy = "people", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Mails> mails = new ArrayList<>();

    public People() {
    }
}
