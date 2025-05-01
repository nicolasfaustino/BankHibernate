package org.example.model;

import javax.persistence.*;

@Entity
@Table(name = "contas")
public class Conta {
    @Id
    @Column(nullable = false)
    private Long ownerId;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;

    @Column(nullable = false)
    private int value;

    @Column(nullable = false)
    private String type;

    public Conta(Long ownerId, String type) {
        this.ownerId = ownerId;
        this.type = type;
        this.value = 0;
    }

    public Conta() {

    }

    public String getType() {
        return type;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
