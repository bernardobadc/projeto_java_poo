import Agendamento.Agendamento;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        AgendamentoDAO agendamentoDAO = new AgendamentoDAO();
        Scanner scanner = new Scanner(System.in);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        while (true) {
            System.out.println("1. Adicionar agendamento");
            System.out.println("2. Consultar agendamento por ID");
            System.out.println("3. Consultar todos os agendamentos");
            System.out.println("4. Atualizar agendamento");
            System.out.println("5. Remover agendamento");
            System.out.println("6. Sair");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consumir a nova linha

            switch (choice) {
                case 1:
                    System.out.println("Insira a data (yyyy-MM-dd): ");
                    String dataStr = scanner.nextLine();
                    System.out.println("Insira o endereço: ");
                    String endereco = scanner.nextLine();
                    System.out.println("Insira o nome do cliente: ");
                    String nomeCliente = scanner.nextLine();
                    System.out.println("Insira o valor: ");
                    double valor = scanner.nextDouble();
                    scanner.nextLine(); // Consumir a nova linha

                    Agendamento novoAgendamento = new Agendamento();
                    try {
                        novoAgendamento.setData(dateFormat.parse(dataStr));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    novoAgendamento.setEndereco(endereco);
                    novoAgendamento.setNomeCliente(nomeCliente);
                    novoAgendamento.setValor(valor);

                    agendamentoDAO.insertAgendamento(novoAgendamento);
                    System.out.println("Agendamento adicionado com sucesso!");
                    break;

                case 2:
                    System.out.println("Insira o ID do agendamento: ");
                    int id = scanner.nextInt();
                    scanner.nextLine(); // Consumir a nova linha

                    Agendamento agendamento = agendamentoDAO.selectAgendamento(id);
                    if (agendamento != null) {
                        System.out.println("ID: " + agendamento.getId());
                        System.out.println("Data: " + dateFormat.format(agendamento.getData()));
                        System.out.println("Endereço: " + agendamento.getEndereco());
                        System.out.println("Nome do Cliente: " + agendamento.getNomeCliente());
                        System.out.println("Valor: " + agendamento.getValor());
                    } else {
                        System.out.println("Agendamento não encontrado.");
                    }
                    break;

                case 3:
                    List<Agendamento> agendamentos = agendamentoDAO.selectAllAgendamentos();
                    for (Agendamento ag : agendamentos) {
                        System.out.println("ID: " + ag.getId());
                        System.out.println("Data: " + dateFormat.format(ag.getData()));
                        System.out.println("Endereço: " + ag.getEndereco());
                        System.out.println("Nome do Cliente: " + ag.getNomeCliente());
                        System.out.println("Valor: " + ag.getValor());
                        System.out.println("------------------------");
                    }
                    break;

                case 4:
                    System.out.println("Insira o ID do agendamento a ser atualizado: ");
                    int updateId = scanner.nextInt();
                    scanner.nextLine(); // Consumir a nova linha

                    System.out.println("Insira a nova data (yyyy-MM-dd): ");
                    String updateDataStr = scanner.nextLine();
                    System.out.println("Insira o novo endereço: ");
                    String updateEndereco = scanner.nextLine();
                    System.out.println("Insira o novo nome do cliente: ");
                    String updateNomeCliente = scanner.nextLine();
                    System.out.println("Insira o novo valor: ");
                    double updateValor = scanner.nextDouble();
                    scanner.nextLine(); // Consumir a nova linha

                    Agendamento updateAgendamento = new Agendamento();
                    updateAgendamento.setId(updateId);
                    try {
                        updateAgendamento.setData(dateFormat.parse(updateDataStr));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    updateAgendamento.setEndereco(updateEndereco);
                    updateAgendamento.setNomeCliente(updateNomeCliente);
                    updateAgendamento.setValor(updateValor);

                    if (agendamentoDAO.updateAgendamento(updateAgendamento)) {
                        System.out.println("Agendamento atualizado com sucesso!");
                    } else {
                        System.out.println("Falha ao atualizar o agendamento.");
                    }
                    break;

                case 5:
                    System.out.println("Insira o ID do agendamento a ser removido: ");
                    int deleteId = scanner.nextInt();
                    scanner.nextLine(); // Consumir a nova linha

                    if (agendamentoDAO.deleteAgendamento(deleteId)) {
                        System.out.println("Agendamento removido com sucesso!");
                    } else {
                        System.out.println("Falha ao remover o agendamento.");
                    }
                    break;

                case 6:
                    System.out.println("Saindo...");
                    scanner.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("Opção inválida. Por favor, tente novamente.");
                    break;
            }
        }
    }
}
