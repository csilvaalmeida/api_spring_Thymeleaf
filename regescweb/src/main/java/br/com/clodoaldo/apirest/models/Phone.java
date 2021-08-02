package br.com.clodoaldo.apirest.models;

import javax.persistence.*;

@Entity
public class Phone {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoPhone tipoPhone;

    @Column(nullable = false)
    private Long numeroPhone;

    @Column(nullable = false)
    private Long  tbPersonId;

    public Phone() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoPhone getTipoPhone() {
        return tipoPhone;
    }

    public void setTipoPhone(TipoPhone tipoPhone) {
        this.tipoPhone = tipoPhone;
    }

    public Long getNumeroPhone() {
        return numeroPhone;
    }

    public void setNumeroPhone(Long numeroPhone) {
        this.numeroPhone = numeroPhone;
    }

    public Long gettbPersonId() {
        return tbPersonId;
    }

    public void settbPersonId(Long tbPersonId) {
        this.tbPersonId = tbPersonId;
    }
}
