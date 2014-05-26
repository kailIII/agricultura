/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import entities.annotations.EntityDescriptor;
import entities.annotations.PropertyDescriptor;
import entities.annotations.View;
import entities.annotations.Views;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author fuscaosoftware
 */
@Entity
@Table(uniqueConstraints = {
  @UniqueConstraint(columnNames = {"nome"},
 name = "Parente")
})
@EntityDescriptor(hidden = true)
public class Parente implements Serializable {
    
    public enum Parentesco{
    Avô,Avó,Esposa,Esposo,Filho,Filha,Pai,Mãe,Tio,Tia
    }

    @Id
    @GeneratedValue
    private Long id;
    
    @NotEmpty
    @Column(length= 60)
    @Length(max = 60)
    private String nome;

    
    @NotNull
    private Parentesco parente;

    public Parentesco getParente() {
        return parente;
    }

    public void setParente(Parentesco parente) {
        this.parente = parente;
    }

    
  

    @ManyToOne
    private Produtor produtor;

    public Produtor getProdutor() {
        return produtor;
    }

    public void setProdutor(Produtor produtor) {
        this.produtor = produtor;
    }
    
    
    
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
    

    
        
    public void removerFamiliar(){
        produtor.getParentes().remove(this);
       
    }
    
    
}
