package cadastrobd.model;

import cadastrobd.model.util.ConectorBD;
import cadastrobd.model.util.SequenceManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PessoaJuridicaDAO {

    public PessoaJuridica getPessoa(long id) {
        String sql = "SELECT p.id, p.nome, p.logradouro, p.cidade, p.estado, p.telefone, p.email, pj.cnpj " +
                     "FROM Pessoa p JOIN PessoaJuridica pj ON p.id = pj.id " +
                     "WHERE p.id = ?";
        try (Connection conn = ConectorBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new PessoaJuridica(
                        rs.getLong("id"),
                        rs.getString("nome"),
                        rs.getString("logradouro"),
                        rs.getString("cidade"),
                        rs.getString("estado"),
                        rs.getString("telefone"),
                        rs.getString("email"),
                        rs.getString("cnpj")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<PessoaJuridica> getPessoas() {
        List<PessoaJuridica> lista = new ArrayList<>();
        String sql = "SELECT p.id, p.nome, p.logradouro, p.cidade, p.estado, p.telefone, p.email, pj.cnpj " +
                     "FROM Pessoa p JOIN PessoaJuridica pj ON p.id = pj.id";
        try (Connection conn = ConectorBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new PessoaJuridica(
                    rs.getLong("id"),
                    rs.getString("nome"),
                    rs.getString("logradouro"),
                    rs.getString("cidade"),
                    rs.getString("estado"),
                    rs.getString("telefone"),
                    rs.getString("email"),
                    rs.getString("cnpj")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public boolean incluir(PessoaJuridica pj) {
        String insPessoa = "INSERT INTO Pessoa(id, nome, logradouro, cidade, estado, telefone, email) " +
                           "VALUES (?, ?, ?, ?, ?, ?, ?)";
        String insPJ     = "INSERT INTO PessoaJuridica(id, cnpj) VALUES (?, ?)";
        Connection conn = null;
        PreparedStatement ps1 = null, ps2 = null;
        try {
            long novoId = SequenceManager.getValue("SEQ_PESSOA");
            pj.setId(novoId);

            conn = ConectorBD.getConnection();
            conn.setAutoCommit(false);

            ps1 = conn.prepareStatement(insPessoa);
            ps1.setLong(1, pj.getId());
            ps1.setString(2, pj.getNome());
            ps1.setString(3, pj.getLogradouro());
            ps1.setString(4, pj.getCidade());
            ps1.setString(5, pj.getEstado());
            ps1.setString(6, pj.getTelefone());
            ps1.setString(7, pj.getEmail());
            ps1.executeUpdate();

            ps2 = conn.prepareStatement(insPJ);
            ps2.setLong(1, pj.getId());
            ps2.setString(2, pj.getCnpj());
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

    public boolean alterar(PessoaJuridica pj) {
        String updPessoa = "UPDATE Pessoa SET nome=?, logradouro=?, cidade=?, estado=?, telefone=?, email=? WHERE id=?";
        String updPJ     = "UPDATE PessoaJuridica SET cnpj=? WHERE id=?";
        Connection conn = null;
        PreparedStatement ps1 = null, ps2 = null;
        try {
            conn = ConectorBD.getConnection();
            conn.setAutoCommit(false);

            ps1 = conn.prepareStatement(updPessoa);
            ps1.setString(1, pj.getNome());
            ps1.setString(2, pj.getLogradouro());
            ps1.setString(3, pj.getCidade());
            ps1.setString(4, pj.getEstado());
            ps1.setString(5, pj.getTelefone());
            ps1.setString(6, pj.getEmail());
            ps1.setLong(7, pj.getId());
            ps1.executeUpdate();

            ps2 = conn.prepareStatement(updPJ);
            ps2.setString(1, pj.getCnpj());
            ps2.setLong(2, pj.getId());
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
        String delPJ  = "DELETE FROM PessoaJuridica WHERE id=?";
        String delP   = "DELETE FROM Pessoa WHERE id=?";
        Connection conn = null;
        PreparedStatement ps1 = null, ps2 = null;
        try {
            conn = ConectorBD.getConnection();
            conn.setAutoCommit(false);

            ps1 = conn.prepareStatement(delPJ);
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
