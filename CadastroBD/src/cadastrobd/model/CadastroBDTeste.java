package cadastrobd.model;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class CadastroBDTeste {
    public static void main(String[] args) throws Exception {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8.name()));

        PessoaFisicaDAO pfDao = new PessoaFisicaDAO();
        PessoaJuridicaDAO pjDao = new PessoaJuridicaDAO();

        PessoaFisica pf = new PessoaFisica(
            0,
            "Rafael Silva",
            "Rua A, 123",
            "São Paulo",
            "SP",
            "(11) 99999-0000",
            "rafael@exemplo.com",
            "123.456.789-00"
        );
        System.out.println("Incluindo PF: " + pfDao.incluir(pf));

        pf.setTelefone("(11) 98888-1111");
        System.out.println("Alterando PF: " + pfDao.alterar(pf));

        System.out.println("Todas Pessoas Físicas:");
        List<PessoaFisica> listaPF = pfDao.getPessoas();
        listaPF.forEach(Pessoa::exibir);

        System.out.println("Excluindo PF: " + pfDao.excluir(pf.getId()));

        PessoaJuridica pj = new PessoaJuridica(
            0,
            "Empresa X Ltda",
            "Av. B, 456",
            "Campinas",
            "SP",
            "(19) 3555-4444",
            "contato@empresax.com",
            "12.345.678/0001-99"
        );
        System.out.println("Incluindo PJ: " + pjDao.incluir(pj));

        pj.setEmail("financeiro@empresax.com");
        System.out.println("Alterando PJ: " + pjDao.alterar(pj));

        System.out.println("Todas Pessoas Jurídicas:");
        List<PessoaJuridica> listaPJ = pjDao.getPessoas();
        listaPJ.forEach(Pessoa::exibir);

        System.out.println("Excluindo PJ: " + pjDao.excluir(pj.getId()));
    }
}