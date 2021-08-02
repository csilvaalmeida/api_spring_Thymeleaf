package br.com.clodoaldo.apirest.dto;

import br.com.clodoaldo.apirest.models.Person;


import javax.persistence.Column;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

// É uma classe DTO (Data Transfer Object)
public class
RequisicaoFormPerson {

    private Long id;

    @NotBlank
    @NotNull
    private String nome; // em caso de erro, NotBlank.requisicaoNovoPerson.nome

    @NotBlank
    @NotNull
    private String segundoNome;

    @DecimalMin("0.0")
    @NotNull
    @Column(nullable = false)
    private Long numeroCpf;

    @NotBlank
    @NotNull
    @Column(nullable = false)
    private String dataNascimento;

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSegundoNome() {
        return segundoNome;
    }

    public void setSegundoNome(String segundoNome) {
        this.segundoNome = segundoNome;
    }

    public Long getNumeroCpf() {
        return numeroCpf;
    }

    public void setNumeroCpf(Long numeroCpf) {
        this.numeroCpf = numeroCpf;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Person toPerson() {
        Person Person = new Person();
        Person.setNome(this.nome);
        Person.setSegundoNome(this.segundoNome);
        Person.setNumeroCpf(this.numeroCpf);
        Person.setDataNascimento(this.dataNascimento);

        return Person;
    }


    public Person toPerson(Person Person) {
        Person.setNome(this.nome);
        Person.setSegundoNome(this.segundoNome);
        Person.setNumeroCpf(this.numeroCpf);
        Person.setDataNascimento(this.dataNascimento);
        return Person;
    }

    public void fromPerson(Person Person) {
        this.nome = Person.getNome();
        this.segundoNome = Person.getSegundoNome();
        this.numeroCpf = Person.getNumeroCpf();
        this.dataNascimento = Person.getDataNascimento();
    }

    @Override
    public String toString() {
        return "RequisicaoNovoPerson{" +
               "nome='" + nome + '\'' +
               ", Sobre nome" + segundoNome +
                ", CPF  =" + numeroCpf +
               ", nascimento =" + dataNascimento +
               '}';
    }
}
