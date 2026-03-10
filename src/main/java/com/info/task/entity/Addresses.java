package com.info.task.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "T_ADDRESSES")
public class Addresses implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false, length = 10)
    private Long id;

    // Many-to-One relationship to People
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "T_PEOPLE_ID", nullable = false)
    private People people;


    @Column(name = "ADDR_TYPE", nullable = false, length = 5)
    private String addrType;

    @Column(name = "ADDR_INFO", length = 300)
    private String addrInfo;

    public Addresses() {
    }
}
