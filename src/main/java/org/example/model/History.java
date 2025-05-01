package org.example.model;

import javax.persistence.*;

@Entity
@Table(name = "transactions")
public class History {
    @Id
    @Column(nullable = false)
    private Long accountId;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

    @Column(nullable = false)
    private Long otherAccountId;

    @Column(nullable = false)
    private String Type;

    @Column(nullable = false)
    private int amount;

    public History(Long accountId, Long otherAccountId, String Type, int amount) {
        this.accountId = accountId;
        this.otherAccountId = otherAccountId;
        this.Type = Type;
        this.amount = amount;
    }

    public History() {

    }

    public History getTransaction() {
        return this;
    }
}
