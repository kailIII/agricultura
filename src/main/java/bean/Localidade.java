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
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author fuscaosoftware
 */
@Entity
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = {"distrito_id", "nome"},
            name = "Localidade")
})
@Views({
    @View(name = "Adicionar",
            title = "Localidades",
            filters = "[nome,Ctrl.DAO.filter()]",
            members = "["
            + "     nome,distrito"
            + "]",
            template = "@FORM+@CRUD+@PAGER",
            roles = "Administrador")})
public class Localidade implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @PropertyDescriptor(index = 1, hidden = true)
    private Long id;
    @NotEmpty(message = "Informe o nome da Localidade")
    @Column(length = 60)
    @PropertyDescriptor(index = 2, autoFilter = true, displayName = "Nome da Localidade")
    private String nome;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "localidade")
    private List<Propriedade> propriedades = new ArrayList<Propriedade>();
    @ManyToOne
    @NotNull
    private Distrito distrito;

    public Distrito getDistrito() {
        return distrito;
    }

    public void setDistrito(Distrito distrito) {
        this.distrito = distrito;
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

    public List<Propriedade> getPropriedades() {
        return propriedades;
    }

    public void setPropriedades(List<Propriedade> propriedades) {
        this.propriedades = propriedades;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Localidade other = (Localidade) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.nome, other.nome)) {
            return false;
        }
        if (!Objects.equals(this.distrito, other.distrito)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.id);
        hash = 97 * hash + Objects.hashCode(this.nome);
        hash = 97 * hash + Objects.hashCode(this.distrito);
        return hash;
    }

    @Override
    public String toString() {
        return nome;

    }
}
