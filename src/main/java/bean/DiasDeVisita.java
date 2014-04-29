/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import entities.annotations.EntityDescriptor;
import entities.annotations.PropertyDescriptor;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import org.hibernate.validator.constraints.Length;


@Entity
@EntityDescriptor(hidden = true)
public class DiasDeVisita implements Serializable {
    
    @Id
    @GeneratedValue
    private Long id;
    
    @Past
    @NotNull
    @Temporal(javax.persistence.TemporalType.DATE)
    @PropertyDescriptor(index = 9, displayName = "Data da Visita")
    @Column(length= 20, unique=true)
    @Length(max = 20)
    private Date diaDeVisita;
    
    @ManyToOne
    private DiarioDeCampo diariodecampo;
//    
//    public void removerVisita(){
//        diariodecampo.getDiasdevisita().remove(this);
//    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDiaDeVisita() {
        return diaDeVisita;
    }

    public void setDiaDeVisita(Date diaDeVisita) {
        this.diaDeVisita = diaDeVisita;
    }

    public DiarioDeCampo getDiariodecampo() {
        return diariodecampo;
    }

    public void setDiariodecampo(DiarioDeCampo diariodecampo) {
        this.diariodecampo = diariodecampo;
    }
    
    
    
}
