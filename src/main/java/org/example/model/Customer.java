package org.example.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "owner")
    private List<Account> contas;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String cpf;

    public Customer(String name, String cpf) {
        this.name = name;
        this.cpf = cpf;
    }

    public Customer() {

    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getCpf() { return cpf; }
    public List<Account> getContas() { return contas; }
}
