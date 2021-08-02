package br.com.clodoaldo.apirest.models;


import javax.persistence.*;
import java.util.List;

@Entity
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String segundoNome;

    @Column(nullable = false)
    private Long numeroCpf;

    @Column(nullable = false)
    private String dataNascimento;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "tbPhoneId", referencedColumnName = "id")
    private List<Phone> phone;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<Phone> getPhone() {
        return phone;
    }

    public void setPhone(List<Phone> phone) {
        this.phone = phone;
    }

    public Person() {  }

    public Person(String nome, String segundoNome, Long numeroCpf, String dataNascimento ) {
        this.nome = nome;
        this.segundoNome = segundoNome;
        this.numeroCpf = numeroCpf;
        this.dataNascimento = dataNascimento;
    }


    @Override
    public String toString() {
        return "Person{" +
               "id=" + id +
               ", nome='" + nome + '\'' +
               ", segundoNome =" + segundoNome +
                " numeroCpf ="  + numeroCpf +
               ", dataNascimento=" + dataNascimento +
               '}';
    }
}
