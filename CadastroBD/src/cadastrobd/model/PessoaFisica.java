package cadastrobd.model;

public class PessoaFisica extends Pessoa {
    private String cpf;

    public PessoaFisica() { }

    public PessoaFisica(long id, String nome, String logradouro, String cidade,
                        String estado, String telefone, String email, String cpf) {
        super(id, nome, logradouro, cidade, estado, telefone, email);
        this.cpf = cpf;
    }

    @Override
    public void exibir() {
        System.out.printf("Estado: %s%n", getEstado());
        System.out.printf("Telefone: %s%n", getTelefone());
        System.out.printf("E-mail: %s%n", getEmail());
        System.out.printf("CPF: %s%n%n", getCpf());
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}