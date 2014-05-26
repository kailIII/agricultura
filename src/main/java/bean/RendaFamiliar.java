/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import entities.annotations.PropertyDescriptor;
import entities.annotations.View;
import entities.annotations.Views;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author Am1b10s
 */
@Entity
@Table(uniqueConstraints = {
  @UniqueConstraint(columnNames = {"renda"},
 name = "RendaFamiliar")
})
@Views({
    @View(name="Adicionar",
    title="Renda Familiar",
    filters="[renda,Ctrl.DAO.filter()]",
    members ="["
    + "     renda"
    + "]"
        ,
    template="@FORM+@CRUD+@PAGER",    
    roles="Administrador")})


public class RendaFamiliar implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @PropertyDescriptor(index = 1, autoFilter = true)
    private Long id;
    
    @NotEmpty()
    @Column(length = 60)
    @PropertyDescriptor(index = 2, autoFilter = true, displayName = "Renda Familiar")
    private String renda;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRenda() {
        return renda;
    }

    public void setRenda(String renda) {
        this.renda = renda;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final RendaFamiliar other = (RendaFamiliar) obj;
        if ((this.renda == null) ? (other.renda != null) : !this.renda.equals(other.renda)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + (this.renda != null ? this.renda.hashCode() : 0);
        return hash;
    }



    @Override
    public String toString() {
        return renda;
    }
    
    
}
