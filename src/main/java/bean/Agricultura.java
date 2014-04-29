/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import entities.annotations.EntityDescriptor;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

/**
 *
 * @author Am1b10s
 */
@Entity
@Table(uniqueConstraints = {
  @UniqueConstraint(columnNames = {"itemDeProducao"},
 name = "Agricultura")
})
@EntityDescriptor(hidden = true)
public class Agricultura implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    
    public enum ItemDeProducao{
	Milho,Feijao,Mandioca,Sorgo,Capim_Elef,Mamona,Girassol,Algodao,Palma
    }

    
    private int quantidadeHA;
    
    private int kG;

    @NotNull
    private ItemDeProducao itemDeProducao;

    @ManyToOne
    private MapeamentoDeUnidadesProdutivas mapeamentoDeUnidadesProdutivas;

    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ItemDeProducao getItemDeProducao() {
        return itemDeProducao;
    }

    public void setItemDeProducao(ItemDeProducao itemDeProducao) {
        this.itemDeProducao = itemDeProducao;
    }

    

    public void remover(){
        mapeamentoDeUnidadesProdutivas.getAgricultura().remove(this);
       
    }
    
    public MapeamentoDeUnidadesProdutivas getMapeamentoDeUnidadesProdutivas() {
        return mapeamentoDeUnidadesProdutivas;
    }

    public void setMapeamentoDeUnidadesProdutivas(MapeamentoDeUnidadesProdutivas mapeamentoDeUnidadesProdutivas) {
        this.mapeamentoDeUnidadesProdutivas = mapeamentoDeUnidadesProdutivas;
    }

    public int getkG() {
        return kG;
    }

    public void setkG(int kG) {
        this.kG = kG;
    }

    public int getQuantidadeHA() {
        return quantidadeHA;
    }

    public void setQuantidadeHA(int quantidadeHA) {
        this.quantidadeHA = quantidadeHA;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Agricultura other = (Agricultura) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (this.itemDeProducao != other.itemDeProducao) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.id);
        hash = 89 * hash + (this.itemDeProducao != null ? this.itemDeProducao.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return  "Item De Produção=" + itemDeProducao;
    }
    
    


    
}
