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
 * @author fuscaosoftware
 */
@Entity
@Table(uniqueConstraints = {
  @UniqueConstraint(columnNames = {"nome"},
 name = "Acao")
})
@Views({
    @View(name="Adicionar",
    title="Ações",
    filters="[nome,Ctrl.DAO.filter()]",
    members ="["
    + "     nome"
    + "]"
        ,
    template="@FORM+@CRUD+@PAGER",    
    roles="Administrador",
    rows = 10)})
public class Acao implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @PropertyDescriptor(index = 1, autoFilter = true)
    private Long id;
    
    @NotEmpty(message = "Informe o nome da Ação")
    @Column(length = 60)
    @PropertyDescriptor(index = 2, autoFilter = true, displayName = "Nome da Ação")
    private String nome;
    
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

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Acao other = (Acao) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        if ((this.nome == null) ? (other.nome != null) : !this.nome.equals(other.nome)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 37 * hash + (this.nome != null ? this.nome.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return nome;
    }
    
    
    
}
