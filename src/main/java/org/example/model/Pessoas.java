package org.example.model;

import javax.persistence.*;

@Entity
@Table(name = "customers")
public class Pessoas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String cpf;

    public Pessoas(String name, String cpf) {
        this.name = name;
        this.cpf = cpf;
    }

    public Pessoas() {

    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCpf() {
        return cpf;
    }
}
