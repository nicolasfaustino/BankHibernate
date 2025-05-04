package org.example;

import org.example.model.Account;
import org.example.model.Customer;
import org.example.util.HibernateUtil;
import org.hibernate.Session;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int opcao;
        HibernateUtil.getSession();

        do {
            System.out.println("\n=== MENU ===");
            System.out.println("1. Cadastrar cliente");
            System.out.println("2. Criar conta para cliente");
            System.out.println("3. Listar clientes e contas");
            System.out.println("4. Acessar Conta");
            System.out.print("Escolha uma opção: ");
            opcao = Integer.parseInt(scanner.nextLine());

            switch (opcao) {
                case 1 -> cadastrarCliente();
                case 2 -> criarConta();
                case 3 -> listarClientesEContas();
                case 4 -> acessarConta();
                case 0 -> System.out.println("Encerrando...");
                default -> System.out.println("Opção inválida.");
            }

        } while (opcao != 0);

        HibernateUtil.shutdown();
    }

    private static void cadastrarCliente() {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();

        Customer c = new Customer(nome, cpf);

        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.persist(c);
        session.getTransaction().commit();
        session.close();

        System.out.println("Cliente cadastrado.");
    }

    private static void criarConta() {
        System.out.print("CPF do cliente: ");
        String cpf = scanner.nextLine();
        System.out.print("Tipo da conta (ex: Corrente/Poupança): ");
        String tipo = scanner.nextLine();

        Session session = HibernateUtil.getSession();

        Customer cliente = session.createQuery("from Customer where cpf = :cpf", Customer.class).setParameter("cpf", cpf).uniqueResult();

        if (cliente == null) {
            System.out.println("Cliente não encontrado.");
        } else {
            Account conta = new Account(cliente, tipo);
            session.beginTransaction();
            session.persist(conta);
            session.getTransaction().commit();
            System.out.println("Conta criada com sucesso para " + cliente.getName());
        }

        session.close();
    }

    private static void listarClientesEContas() {
        Session session = HibernateUtil.getSession();
        List<Customer> clientes = session.createQuery("from Customer", Customer.class).list();

        for (Customer c : clientes) {
            System.out.println("\nCliente: " + c.getName() + " | CPF: " + c.getCpf() + " | ID: " + c.getId());
            List<Account> contas = c.getContas();
            if (contas.isEmpty()) {
                System.out.println("  Nenhuma conta cadastrada.");
            } else {
                for (Account a : contas) {
                    System.out.println("  Conta ID: " + a.getId() + " | Tipo: " + a.getType() + " | Saldo: R$ " + a.getValue());
                }
            }
        }

        session.close();
    }

    private static void acessarConta() {
        System.out.print("CPF do cliente: ");
        String cpf = scanner.nextLine();

        Session session = HibernateUtil.getSession();
        Customer cliente = session.createQuery("from Customer where cpf = :cpf", Customer.class).setParameter("cpf", cpf).uniqueResult();

        if (cliente == null) {
            System.out.println("Cliente não encontrado.");
            session.close();
            return;
        }

        List<Account> contas = cliente.getContas();
        if (contas.isEmpty()) {
            System.out.println("Esse cliente não possui contas.");
            session.close();
            return;
        }

        System.out.println("Contas do cliente " + cliente.getName() + ":");
        for (Account conta : contas) {
            System.out.println("ID: " + conta.getId() + " | Tipo: " + conta.getType() + " | Saldo: R$" + conta.getValue());
        }

        System.out.print("Informe o ID da conta para acessar: ");
        long contaId = Long.parseLong(scanner.nextLine());

        Account contaSelecionada = session.get(Account.class, contaId);

        if (contaSelecionada == null || !contaSelecionada.getOwner().getId().equals(cliente.getId())) {
            System.out.println("Conta inválida.");
            session.close();
            return;
        }

        int opcao;
        do {
            System.out.println("\n--- Conta: " + contaSelecionada.getType() + " (ID " + contaSelecionada.getId() + ") ---");
            System.out.println("Saldo atual: R$" + contaSelecionada.getValue());
            System.out.println("1. Depositar");
            System.out.println("2. Sacar");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");
            opcao = Integer.parseInt(scanner.nextLine());

            switch (opcao) {
                case 1 -> {
                    System.out.print("Valor a depositar: ");
                    int valor = Integer.parseInt(scanner.nextLine());
                    session.beginTransaction();
                    contaSelecionada.setValue(contaSelecionada.getValue() + valor);
                    session.getTransaction().commit();
                    System.out.println("Depósito realizado.");
                }
                case 2 -> {
                    System.out.print("Valor a sacar: ");
                    int valor = Integer.parseInt(scanner.nextLine());
                    if (valor > contaSelecionada.getValue()) {
                        System.out.println("Saldo insuficiente.");
                    } else {
                        session.beginTransaction();
                        contaSelecionada.setValue(contaSelecionada.getValue() - valor);
                        session.getTransaction().commit();
                        System.out.println("Saque realizado.");
                    }
                }
                case 0 -> System.out.println("Voltando...");
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);

        session.close();
    }
}
