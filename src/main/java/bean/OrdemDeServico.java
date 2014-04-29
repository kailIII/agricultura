/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import entities.annotations.Param;
import entities.annotations.PropertyDescriptor;
import entities.annotations.View;
import entities.annotations.Views;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import org.hibernate.validator.constraints.NotEmpty;
import security.Usuario;

/**
 *
 * @author fuscaosoftware
 */
@Entity
@Table(uniqueConstraints = {
  @UniqueConstraint(columnNames = {"ano","produtor_codigo"},
 name = "OrdemDeServico")
})
@Views({
   
    @View(name = "OrdemDeServico", filters="[ano,produtor,produtor.cpf,area,silagem,Ctrl.DAO.filter()]", 
            members = "[["
    + "ano;produtor;"
        
    + "         cultura;"
    + "         area;"
    + "         horaTrator;"
    + "         silagem;"
    + "         quantidadeDeToneladas;quantidadeDeMetrosLona;*usuarioQueModificou;*dataDaModificacao]]",
    template = "@CRUD_PAGE",  
    title = "Ordens De Serviço",
    roles="Administrador,Tecnico")
})
public class OrdemDeServico implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @PropertyDescriptor(index = 1, autoFilter = true, hidden= true)
    private Long id;
    
    @Column(length = 7)
    @PropertyDescriptor(index = 2, mask="9999")
    @NotNull(message="Informe o Ano")
    private String ano;
    
    @ManyToOne
    @NotNull(message = "Informe o Produtor")
    @PropertyDescriptor(index=3)
    private Produtor produtor;
    
    
    @Column(length = 50)
    @PropertyDescriptor(index = 4, autoFilter = true, displayName = "Cultura")
    private String cultura;

    
    @Column(length = 50)
    @PropertyDescriptor(index = 5, autoFilter = true, displayName = "Área")
    private String area;
    
    
    @Column(length = 15)
    @PropertyDescriptor(index = 6, autoFilter = true, displayName = "Quantidade Hora/Trator")
    private String horaTrator;
    
    
    @Column(length = 50)
    @PropertyDescriptor(index = 7, autoFilter = true, displayName = "Silagem")
    private String silagem;
 
    
    @Column(length = 15)
    @PropertyDescriptor(index = 8, autoFilter = true, displayName = "Quantidade de Toneladas")
    private String quantidadeDeToneladas;
    
    
    @Column(length = 15)
    @PropertyDescriptor(index = 9, autoFilter = true, displayName = "Quantidade Metros/Lona")
    private String quantidadeDeMetrosLona;

    
    @PropertyDescriptor(displayName = "Usuário que modificou")
    @ManyToOne
    private Usuario usuarioQueModificou = Usuario.getCurrentUser();
    
    @PropertyDescriptor(displayName = "Data da última modificação")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataDaModificacao = new Date();

    public Date getDataDaModificacao() {
        return dataDaModificacao;
    }

    public void setDataDaModificacao(Date dataDaModificacao) {
        this.dataDaModificacao = dataDaModificacao;
    }

    public Usuario getUsuarioQueModificou() {
        return usuarioQueModificou;
    }

    public void setUsuarioQueModificou(Usuario usuarioQueModificou) {
        this.usuarioQueModificou = usuarioQueModificou;
    }
    
    
    
    public String getCultura() {
        return cultura;
    }

    public void setCultura(String cultura) {
        this.cultura = cultura;
    }

    
    
    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

   
    
    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getHoraTrator() {
        return horaTrator;
    }

    public void setHoraTrator(String horaTrator) {
        this.horaTrator = horaTrator;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Produtor getProdutor() {
        return produtor;
    }

    public void setProdutor(Produtor produtor) {
        this.produtor = produtor;
    }

    public String getQuantidadeDeMetrosLona() {
        return quantidadeDeMetrosLona;
    }

    public void setQuantidadeDeMetrosLona(String quantidadeDeMetrosLona) {
        this.quantidadeDeMetrosLona = quantidadeDeMetrosLona;
    }

    public String getQuantidadeDeToneladas() {
        return quantidadeDeToneladas;
    }

    public void setQuantidadeDeToneladas(String quantidadeDeToneladas) {
        this.quantidadeDeToneladas = quantidadeDeToneladas;
    }

    
   

    public String getSilagem() {
        return silagem;
    }

    public void setSilagem(String silagem) {
        this.silagem = silagem;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final OrdemDeServico other = (OrdemDeServico) obj;
        if (this.ano != other.ano && (this.ano == null || !this.ano.equals(other.ano))) {
            return false;
        }
        if (this.produtor != other.produtor && (this.produtor == null || !this.produtor.equals(other.produtor))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + (this.ano != null ? this.ano.hashCode() : 0);
        hash = 23 * hash + (this.produtor != null ? this.produtor.hashCode() : 0);
        return hash;
    }
    
    
    
}
