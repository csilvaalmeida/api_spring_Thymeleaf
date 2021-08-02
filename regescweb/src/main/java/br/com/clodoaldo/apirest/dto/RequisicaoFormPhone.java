package br.com.clodoaldo.apirest.dto;

import br.com.clodoaldo.apirest.models.Phone;
import br.com.clodoaldo.apirest.models.TipoPhone;


import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;


// É uma classe DTO (Data Transfer Object)
public class RequisicaoFormPhone {


    @NotNull
    @Enumerated(EnumType.STRING)
    private TipoPhone tipoPhone;


    @DecimalMin("0.0")
    @NotNull
    private Long numeroPhone;

    private Long  tbPersonId;


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
    
    
    public Phone toPhone() {
        Phone Phone = new Phone();
        Phone.setTipoPhone(this.tipoPhone);
        Phone.setNumeroPhone(this.numeroPhone);
        Phone.settbPersonId(this.tbPersonId);
        return Phone;
    }


    public Phone toPhone(Phone Phone) {
        Phone.setTipoPhone(this.tipoPhone);
        Phone.setNumeroPhone(this.numeroPhone);
        Phone.settbPersonId(this.tbPersonId);
        return Phone;
    }

    public void fromPhone(Phone Phone) {
        this.tipoPhone = Phone.getTipoPhone();
        this.numeroPhone = Phone.getNumeroPhone();
        this.tbPersonId = Phone.gettbPersonId();
    }

    @Override
    public String toString() {
        return "RequisicaoNovoPhone{" +
                "Tipo Phone='" + tipoPhone + '\'' +
                ", Numero Phone" + numeroPhone +
                ", Id pessoa" + tbPersonId +
                '}';
    }
    
}
