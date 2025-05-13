package cadastrobd.model;

public class PessoaJuridica extends Pessoa {
    private String cnpj;

    public PessoaJuridica() { }

    public PessoaJuridica(long id, String nome, String logradouro, String cidade,
                          String estado, String telefone, String email, String cnpj) {
        super(id, nome, logradouro, cidade, estado, telefone, email);
        this.cnpj = cnpj;
    }

    @Override
    public void exibir() {
        System.out.printf("Id: %d%n", getId());
        System.out.printf("Nome: %s%n", getNome());
        System.out.printf("Logradouro: %s%n", getLogradouro());
        System.out.printf("Cidade: %s%n", getCidade());
        System.out.printf("Estado: %s%n", getEstado());
        System.out.printf("Telefone: %s%n", getTelefone());
        System.out.printf("E-mail: %s%n", getEmail());
        System.out.printf("CNPJ: %s%n%n", getCnpj());
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
}