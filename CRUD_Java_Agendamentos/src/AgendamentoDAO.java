import Agendamento.Agendamento;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AgendamentoDAO {
    private String jdbcURL = "jdbc:mysql://localhost:3306/servicos_limpeza";
    private String jdbcUsername = "root";
    private String jdbcPassword = "109016";

    private static final String INSERT_AGENDAMENTO_SQL = "INSERT INTO agendamentos (data, endereco, nome_cliente, valor) VALUES (?, ?, ?, ?);";
    private static final String SELECT_AGENDAMENTO_BY_ID = "SELECT id, data, endereco, nome_cliente, valor FROM agendamentos WHERE id = ?;";
    private static final String SELECT_ALL_AGENDAMENTOS = "SELECT * FROM agendamentos;";
    private static final String DELETE_AGENDAMENTO_SQL = "DELETE FROM agendamentos WHERE id = ?;";
    private static final String UPDATE_AGENDAMENTO_SQL = "UPDATE agendamentos SET data = ?, endereco = ?, nome_cliente = ?, valor = ? WHERE id = ?;";

    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public void insertAgendamento(Agendamento agendamento) throws SQLException {
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(INSERT_AGENDAMENTO_SQL)) {
            preparedStatement.setDate(1, new java.sql.Date(agendamento.getData().getTime()));
            preparedStatement.setString(2, agendamento.getEndereco());
            preparedStatement.setString(3, agendamento.getNomeCliente());
            preparedStatement.setDouble(4, agendamento.getValor());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    public Agendamento selectAgendamento(int id) {
        Agendamento agendamento = null;
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SELECT_AGENDAMENTO_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Date data = rs.getDate("data");
                String endereco = rs.getString("endereco");
                String nomeCliente = rs.getString("nome_cliente");
                double valor = rs.getDouble("valor");
                agendamento = new Agendamento();
                agendamento.setId(id);
                agendamento.setData(data);
                agendamento.setEndereco(endereco);
                agendamento.setNomeCliente(nomeCliente);
                agendamento.setValor(valor);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return agendamento;
    }

    public List<Agendamento> selectAllAgendamentos() {
        List<Agendamento> agendamentos = new ArrayList<>();
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_AGENDAMENTOS)) {
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                Date data = rs.getDate("data");
                String endereco = rs.getString("endereco");
                String nomeCliente = rs.getString("nome_cliente");
                double valor = rs.getDouble("valor");
                Agendamento agendamento = new Agendamento();
                agendamento.setId(id);
                agendamento.setData(data);
                agendamento.setEndereco(endereco);
                agendamento.setNomeCliente(nomeCliente);
                agendamento.setValor(valor);
                agendamentos.add(agendamento);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return agendamentos;
    }

    public boolean deleteAgendamento(int id) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(DELETE_AGENDAMENTO_SQL)) {
            statement.setInt(1, id);
            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }

    public boolean updateAgendamento(Agendamento agendamento) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(UPDATE_AGENDAMENTO_SQL)) {
            statement.setDate(1, new java.sql.Date(agendamento.getData().getTime()));
            statement.setString(2, agendamento.getEndereco());
            statement.setString(3, agendamento.getNomeCliente());
            statement.setDouble(4, agendamento.getValor());
            statement.setInt(5, agendamento.getId());

            rowUpdated = statement.executeUpdate() > 0;
        }
        return rowUpdated;
    }

    private void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}
