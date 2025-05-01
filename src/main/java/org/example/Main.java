package org.example;

import org.example.model.Conta;
import org.example.model.History;
import org.example.model.Pessoas;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class Main {
    public static void main(String[] args) {
        Pessoas p = new Pessoas("Nicolas", "10628264933");
        System.out.println(p.getId());
        Conta account = new Conta(p.getId(), "Corrente");

        Pessoas p2 = new Pessoas("Mathes", "102000000000");
        Conta account2 = new Conta(p2.getId(), "Corrente");
        History history = new History(p.getId(), p2.getId(), "Recebida", 100);

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        session.save(p);
        session.save(account);
        session.save(p2);
        session.save(account2);
        session.save(history);

        tx.commit();
        session.close();

        System.out.println("Usuário salvo com sucesso!");
    }
}
