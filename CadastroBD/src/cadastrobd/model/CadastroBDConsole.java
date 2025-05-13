package cadastrobd;

import cadastrobd.model.PessoaFisica;
import cadastrobd.model.PessoaFisicaDAO;
import cadastrobd.model.PessoaJuridica;
import cadastrobd.model.PessoaJuridicaDAO;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;

public class CadastroBDConsole {

    public static void main(String[] args) throws Exception {
        // Força saída e leitura em UTF-8 para exibir acentuação corretamente
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8.name()));
        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8.name());

        PessoaFisicaDAO pfDao = new PessoaFisicaDAO();
        PessoaJuridicaDAO pjDao = new PessoaJuridicaDAO();
        int opc;

        do {
            System.out.println("==============================");
            System.out.println("1 - Incluir Pessoa");
            System.out.println("2 - Alterar Pessoa");
            System.out.println("3 - Excluir Pessoa");
            System.out.println("4 - Buscar pelo Id");
            System.out.println("5 - Exibir Todos");
            System.out.println("0 - Finalizar Programa");
            System.out.println("==============================");
            System.out.print("Opção: ");
            
            opc = Integer.parseInt(scanner.nextLine());
            
            try {
                switch (opc) {
                    case 1: // Incluir
                        System.out.print("Tipo (F=Física, J=Jurídica): ");
                        char t1 = scanner.nextLine().toUpperCase().charAt(0);
                        if (t1 == 'F') {
                            PessoaFisica pf = new PessoaFisica();
                            System.out.print("Nome: ");         pf.setNome(scanner.nextLine());
                            System.out.print("Logradouro: ");  pf.setLogradouro(scanner.nextLine());
                            System.out.print("Cidade: ");      pf.setCidade(scanner.nextLine());
                            System.out.print("Estado: ");      pf.setEstado(scanner.nextLine());
                            System.out.print("Telefone: ");    pf.setTelefone(scanner.nextLine());
                            System.out.print("E-mail: ");      pf.setEmail(scanner.nextLine());
                            System.out.print("CPF: ");         pf.setCpf(scanner.nextLine());
                            System.out.println("Incluído: " + pfDao.incluir(pf));
                        } else {
                            PessoaJuridica pj = new PessoaJuridica();
                            System.out.print("Nome: ");         pj.setNome(scanner.nextLine());
                            System.out.print("Logradouro: ");   pj.setLogradouro(scanner.nextLine());
                            System.out.print("Cidade: ");       pj.setCidade(scanner.nextLine());
                            System.out.print("Estado: ");       pj.setEstado(scanner.nextLine());
                            System.out.print("Telefone: ");     pj.setTelefone(scanner.nextLine());
                            System.out.print("E-mail: ");       pj.setEmail(scanner.nextLine());
                            System.out.print("CNPJ: ");         pj.setCnpj(scanner.nextLine());
                            System.out.println("Incluído: " + pjDao.incluir(pj));
                        }
                        break;

                    case 2: // Alterar
                        System.out.print("Tipo (F=Física, J=Jurídica): ");
                        char t2 = scanner.nextLine().toUpperCase().charAt(0);
                        System.out.print("Id da Pessoa: ");
                        long idAlt = Long.parseLong(scanner.nextLine());
                        if (t2 == 'F') {
                            PessoaFisica pf = pfDao.getPessoa(idAlt);
                            if (pf != null) {
                                pf.exibir();
                                System.out.print("Novo Telefone: ");
                                pf.setTelefone(scanner.nextLine());
                                System.out.println("Alterado: " + pfDao.alterar(pf));
                            } else {
                                System.out.println("Pessoa Física não encontrada.");
                            }
                        } else {
                            PessoaJuridica pj = pjDao.getPessoa(idAlt);
                            if (pj != null) {
                                pj.exibir();
                                System.out.print("Novo E-mail: ");
                                pj.setEmail(scanner.nextLine());
                                System.out.println("Alterado: " + pjDao.alterar(pj));
                            } else {
                                System.out.println("Pessoa Jurídica não encontrada.");
                            }
                        }
                        break;

                    case 3: // Excluir
                        System.out.print("Tipo (F=Física, J=Jurídica): ");
                        char t3 = scanner.nextLine().toUpperCase().charAt(0);
                        System.out.print("Id da Pessoa: ");
                        long idExc = Long.parseLong(scanner.nextLine());
                        if (t3 == 'F') {
                            System.out.println("Excluído: " + pfDao.excluir(idExc));
                        } else {
                            System.out.println("Excluído: " + pjDao.excluir(idExc));
                        }
                        break;

                    case 4: // Buscar por ID
                        System.out.print("Tipo (F=Física, J=Jurídica): ");
                        char t4 = scanner.nextLine().toUpperCase().charAt(0);
                        System.out.print("Id da Pessoa: ");
                        long idGet = Long.parseLong(scanner.nextLine());
                        if (t4 == 'F') {
                            PessoaFisica pf = pfDao.getPessoa(idGet);
                            if (pf != null) pf.exibir();
                            else System.out.println("Não encontrada.");
                        } else {
                            PessoaJuridica pj = pjDao.getPessoa(idGet);
                            if (pj != null) pj.exibir();
                            else System.out.println("Não encontrada.");
                        }
                        break;

                    case 5: // Exibir todos
                        System.out.print("Tipo (F=Física, J=Jurídica): ");
                        char t5 = scanner.nextLine().toUpperCase().charAt(0);
                        if (t5 == 'F') {
                            System.out.println("Exibindo dados de Pessoa Física...");
                            List<PessoaFisica> fisicas = pfDao.getPessoas();
                            fisicas.forEach(PessoaFisica::exibir);
                        } else {
                            System.out.println("Exibindo dados de Pessoa Jurídica...");
                            List<PessoaJuridica> juridicas = pjDao.getPessoas();
                            juridicas.forEach(PessoaJuridica::exibir);
                        }
                        break;

                    case 0:
                        System.out.println("Finalizando...");
                        break;

                    default:
                        System.out.println("Opção inválida.");
                }
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        } while (opc != 0);

        scanner.close();
    }
}
