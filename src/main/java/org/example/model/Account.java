package org.example.model;

import javax.persistence.*;

@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "owner", nullable = false)
    private Customer owner;

    @Column(nullable = false)
    private int value;

    @Column(nullable = false)
    private String type;

    public Account(Customer owner, String type) {
        this.owner = owner;
        this.type = type;
        this.value = 0;
    }

    public Account() {

    }

    public Long getId() { return id; }
    public Customer getOwner() { return owner; }
    public String getType() { return type; }
    public int getValue() { return value; }
    public void setValue(int value) { this.value = value; }
}
