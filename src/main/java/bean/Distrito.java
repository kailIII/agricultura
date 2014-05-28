/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import entities.annotations.PropertyDescriptor;
import entities.annotations.View;
import entities.annotations.Views;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.*;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author fuscaosoftware
 */
@Entity
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = {"nome"},
            name = "Distrito")
})
@Views({
    @View(name = "Adicionar",
            title = "Distritos",
            filters = "[nome,Ctrl.DAO.filter()]",
            members = "["
            + "     nome"
            + "]",
            template = "@FORM+@CRUD+@PAGER",
            roles = "Administrador",
            rows = 10)})
public class Distrito implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @PropertyDescriptor(index = 1, hidden = true)
    private Long id;
    @NotEmpty(message = "Informe o nome do Distrito")
    @Column(length = 60)
    @PropertyDescriptor(index = 2, autoFilter = true, displayName = "Nome do Distrito")
    private String nome;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "distrito")
    private List<Localidade> localidades = new ArrayList<Localidade>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Localidade> getLocalidades() {
        return localidades;
    }

    public void setLocalidades(List<Localidade> localidades) {
        this.localidades = localidades;
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
        final Distrito other = (Distrito) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.nome, other.nome)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.id);
        hash = 89 * hash + Objects.hashCode(this.nome);
        return hash;
    }

    @Override
    public String toString() {
        return nome;
    }
}
