/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import entities.annotations.PropertyDescriptor;
import entities.annotations.View;
import entities.annotations.Views;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.*;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author fuscaosoftware
 */
@Entity
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = {"nome", "localidade_id"},
            name = "Propriedade")
})
@Views({
    @View(name = "Adicionar",
            title = "Propriedades",
            filters = "[nome,Ctrl.DAO.filter()]",
            members = "["
            + "     nome,localidade"
            + "]",
            template = "@FORM+@CRUD+@PAGER",
            roles = "Administrador")})
public class Propriedade implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @PropertyDescriptor(index = 1, hidden = true)
    private Long id;
    @NotEmpty(message = "Informe o nome da Propriedade")
    @Column(length = 60)
    @PropertyDescriptor(index = 2, autoFilter = true, displayName = "Nome da Propriedade")
    private String nome;
    @ManyToOne
    private Localidade localidade;

    public Localidade getLocalidade() {
        return localidade;
    }

    public void setLocalidade(Localidade localidade) {
        this.localidade = localidade;
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

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Propriedade other = (Propriedade) obj;
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
        hash = 73 * hash + Objects.hashCode(this.id);
        hash = 73 * hash + Objects.hashCode(this.nome);
        return hash;
    }

    @Override
    public String toString() {
        return nome;
    }
}
