package cadastrobd.model;

import cadastrobd.model.util.ConectorBD;
import cadastrobd.model.util.SequenceManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PessoaFisicaDAO {

    public PessoaFisica getPessoa(long id) {
        String sql = "SELECT p.id, p.nome, p.logradouro, p.cidade, p.estado, p.telefone, p.email, pf.cpf " +
                     "FROM Pessoa p JOIN PessoaFisica pf ON p.id = pf.id " +
                     "WHERE p.id = ?";
        try (Connection conn = ConectorBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    PessoaFisica pf = new PessoaFisica(
                        rs.getLong("id"),
                        rs.getString("nome"),
                        rs.getString("logradouro"),
                        rs.getString("cidade"),
                        rs.getString("estado"),
                        rs.getString("telefone"),
                        rs.getString("email"),
                        rs.getString("cpf")
                    );
                    return pf;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<PessoaFisica> getPessoas() {
        List<PessoaFisica> lista = new ArrayList<>();
        String sql = "SELECT p.id, p.nome, p.logradouro, p.cidade, p.estado, p.telefone, p.email, pf.cpf " +
                     "FROM Pessoa p JOIN PessoaFisica pf ON p.id = pf.id";
        try (Connection conn = ConectorBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new PessoaFisica(
                    rs.getLong("id"),
                    rs.getString("nome"),
                    rs.getString("logradouro"),
                    rs.getString("cidade"),
                    rs.getString("estado"),
                    rs.getString("telefone"),
                    rs.getString("email"),
                    rs.getString("cpf")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public boolean incluir(PessoaFisica pf) {
        String insPessoa = "INSERT INTO Pessoa(id, nome, logradouro, cidade, estado, telefone, email) " +
                           "VALUES (?, ?, ?, ?, ?, ?, ?)";
        String insPF     = "INSERT INTO PessoaFisica(id, cpf) VALUES (?, ?)";
        Connection conn = null;
        PreparedStatement ps1 = null, ps2 = null;
        try {
            long novoId = SequenceManager.getValue("SEQ_PESSOA");
            pf.setId(novoId);

            conn = ConectorBD.getConnection();
            conn.setAutoCommit(false);

            ps1 = conn.prepareStatement(insPessoa);
            ps1.setLong(1, pf.getId());
            ps1.setString(2, pf.getNome());
            ps1.setString(3, pf.getLogradouro());
            ps1.setString(4, pf.getCidade());
            ps1.setString(5, pf.getEstado());
            ps1.setString(6, pf.getTelefone());
            ps1.setString(7, pf.getEmail());
            ps1.executeUpdate();

            ps2 = conn.prepareStatement(insPF);
            ps2.setLong(1, pf.getId());
            ps2.setString(2, pf.getCpf());
            ps2.executeUpdate();

            conn.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            if (conn != null) try { conn.rollback(); } catch (SQLException ignore) {}
            return false;
        } finally {
            ConectorBD.close(ps2);
            ConectorBD.close(ps1);
            ConectorBD.close(conn);
        }
    }

    public boolean alterar(PessoaFisica pf) {
        String updPessoa = "UPDATE Pessoa SET nome=?, logradouro=?, cidade=?, estado=?, telefone=?, email=? WHERE id=?";
        String updPF     = "UPDATE PessoaFisica SET cpf=? WHERE id=?";
        Connection conn = null;
        PreparedStatement ps1 = null, ps2 = null;
        try {
            conn = ConectorBD.getConnection();
            conn.setAutoCommit(false);

            ps1 = conn.prepareStatement(updPessoa);
            ps1.setString(1, pf.getNome());
            ps1.setString(2, pf.getLogradouro());
            ps1.setString(3, pf.getCidade());
            ps1.setString(4, pf.getEstado());
            ps1.setString(5, pf.getTelefone());
            ps1.setString(6, pf.getEmail());
            ps1.setLong(7, pf.getId());
            ps1.executeUpdate();

            ps2 = conn.prepareStatement(updPF);
            ps2.setString(1, pf.getCpf());
            ps2.setLong(2, pf.getId());
            ps2.executeUpdate();

            conn.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            if (conn != null) try { conn.rollback(); } catch (SQLException ignore) {}
            return false;
        } finally {
            ConectorBD.close(ps2);
            ConectorBD.close(ps1);
            ConectorBD.close(conn);
        }
    }

    public boolean excluir(long id) {
        String delPF  = "DELETE FROM PessoaFisica WHERE id=?";
        String delP   = "DELETE FROM Pessoa WHERE id=?";
        Connection conn = null;
        PreparedStatement ps1 = null, ps2 = null;
        try {
            conn = ConectorBD.getConnection();
            conn.setAutoCommit(false);

            ps1 = conn.prepareStatement(delPF);
            ps1.setLong(1, id);
            ps1.executeUpdate();

            ps2 = conn.prepareStatement(delP);
            ps2.setLong(1, id);
            ps2.executeUpdate();

            conn.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            if (conn != null) try { conn.rollback(); } catch (SQLException ignore) {}
            return false;
        } finally {
            ConectorBD.close(ps2);
            ConectorBD.close(ps1);
            ConectorBD.close(conn);
        }
    }
}
