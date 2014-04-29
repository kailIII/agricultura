package bean;


import entities.annotations.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import security.Usuario;
/**
 *
 * @author Java
 */
@Entity
@Table(uniqueConstraints = {
  @UniqueConstraint(columnNames = {"tecnico_usuario","distrito_id","localidade_id","produtor_codigo","data"},
 name = "MapeamentoDeUnidadesProdutivas")
})
@Views({
    @View(name = "MUPAdmin",
    title = "Mapeamentos de Unidades Produtivas",
    filters="[distrito,produtor,produtor.cpf,tecnico,data,Ctrl.DAO.filter()]", 
    members = "["
    + "Teste[[*tecnico;"
    + "         distrito;"
    + "         localidade;"
    + "         produtor;"
    + "         data;"
    + "         adicionarItemDeProducao()];"
    + "         agricultura<itemDeProducao,quantidadeHA,kG,remover()>;], "
    
    + "Pecuária[Bovinocultura[leiteBovino,quantidadeBovinosLeiteiros;carneBovina,quantidadeBovinosDeCorte;litrosDeLeiteMensal,kgCarneMensal]; "
    + "Caprinocultura[leiteCaprino,quantidadeCaprinosLeiteiros;carneCaprina,quantidadeCaprinosDeCorte];"
    + "Apicultura[quantidadeColmeias,quantidadeMelKg],"
    + "Ovinocultura[numeroDeAnimaisOvinos];"
    + "Avicultura[quantidadeAves];"
    + "Suinocultura[quantidadeSuinos];"
    + "];Obervações Adicionais[observacao]:2;[*dataDaCriacao;*dataDaModificacao],]",
     params = {@Param(name = "distrito", value = "#{dataItem.distrito}"),@Param(name = "localidade", value = "#{dataItem.localidade}")},
    template = "@CRUD_PAGE",    
    roles="Administrador"),
    
@View(name = "MUPUser",
    title = "Mapeamentos de Unidades Produtivas",
    filters="[distrito,produtor,produtor.cpf,data,Ctrl.DAO.filter()]", 
    members = "["
    + "[*tecnico;"
    + "         distrito;"
    + "         localidade;"
    + "         produtor;"
    + "         data;"
    + "adicionarItemDeProducao();"
    + "agricultura<itemDeProducao,quantidadeHA,kG,remover()>]; "
    + "[observacao];[*dataDaCriacao;*dataDaModificacao]"
    + "]",
    template = "@CRUD_PAGE",    
    roles="Tecnico",
    namedQuery="Select ot From MapeamentoDeUnidadesProdutivas ot where ot.tecnico = :tecnico",
     params = {@Param(name = "distrito", value = "#{dataItem.distrito}"),@Param(name = "tecnico", value = "#{context.currentUser()}")}),

})


public class MapeamentoDeUnidadesProdutivas implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @PropertyDescriptor(index = 1, hidden = true)
    private Long id;

    @Editor(inputComponentProperties=@Param(name="reRender",value="localidade"))
    @ManyToOne
    private Distrito distrito;

    @Editor(namedQuery="From Localidade where distrito = :distrito")
    @ManyToOne
    private Localidade localidade;
    
    @NotNull
    @ManyToOne
    @PropertyDescriptor(displayName="Técnico")
    private Usuario tecnico = Usuario.getCurrentUser();
    
    @ManyToOne
    @NotNull(message = "Informe o Produtor")
    @PropertyDescriptor(index=4)
    private Produtor produtor;
          
    @Past
    @PropertyDescriptor(index = 5)
    @NotNull(message = "Informe a Data do Relatório")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date data = new Date();
    
    @Lob
    @Column(length=80)
    @PropertyDescriptor(index = 6, displayName="Descrição", displayRows=3, displayWidth=180)
    private String observacao;
  
    @PropertyDescriptor(displayName = "Data da criação")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataDaCriacao = new Date();
    
    @PropertyDescriptor(displayName = "Data da última modificação")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataDaModificacao = new Date();
    
    @OneToMany(cascade = CascadeType.ALL,   mappedBy="mapeamentoDeUnidadesProdutivas")
    private List<Agricultura> agricultura = new ArrayList<Agricultura>();
 
    //Bovinocultura
    private boolean leiteBovino;
    private int quantidadeBovinosLeiteiros;
    private boolean carneBovina;
    private int quantidadeBovinosDeCorte;
    private int litrosDeLeiteMensal;
    private int kgCarneMensal;

    //Ovinocultura
    private int numeroDeAnimaisOvinos;

    //Caprinocultura
    private boolean leiteCaprino;
    private int quantidadeCaprinosLeiteiros;
    private boolean carneCaprina;
    private int quantidadeCaprinosDeCorte;
    
    //Avicultura
    private int quantidadeAves;
    
    //Suinocultura
    private int quantidadeSuinos;

    //Apicultura
    private int quantidadeColmeias;
    private int quantidadeMelKg;

    
    
    
    
     public void adicionarItemDeProducao() {
        Agricultura agr = new Agricultura();
        agr.setMapeamentoDeUnidadesProdutivas(this);
        agricultura.add(agr);
       
    }

    public boolean isCarneBovina() {
        return carneBovina;
    }

    public void setCarneBovina(boolean carneBovina) {
        this.carneBovina = carneBovina;
    }

    public boolean isCarneCaprina() {
        return carneCaprina;
    }

    public void setCarneCaprina(boolean carneCaprina) {
        this.carneCaprina = carneCaprina;
    }

    public int getKgCarneMensal() {
        return kgCarneMensal;
    }

    public void setKgCarneMensal(int kgCarneMensal) {
        this.kgCarneMensal = kgCarneMensal;
    }

    public boolean isLeiteBovino() {
        return leiteBovino;
    }

    public void setLeiteBovino(boolean leiteBovino) {
        this.leiteBovino = leiteBovino;
    }

    public boolean isLeiteCaprino() {
        return leiteCaprino;
    }

    public void setLeiteCaprino(boolean leiteCaprino) {
        this.leiteCaprino = leiteCaprino;
    }

    public int getLitrosDeLeiteMensal() {
        return litrosDeLeiteMensal;
    }

    public void setLitrosDeLeiteMensal(int litrosDeLeiteMensal) {
        this.litrosDeLeiteMensal = litrosDeLeiteMensal;
    }

    public int getNumeroDeAnimaisOvinos() {
        return numeroDeAnimaisOvinos;
    }

    public void setNumeroDeAnimaisOvinos(int numeroDeAnimaisOvinos) {
        this.numeroDeAnimaisOvinos = numeroDeAnimaisOvinos;
    }

    

    public int getQuantidadeAves() {
        return quantidadeAves;
    }

    public void setQuantidadeAves(int quantidadeAves) {
        this.quantidadeAves = quantidadeAves;
    }

    public int getQuantidadeBovinosDeCorte() {
        return quantidadeBovinosDeCorte;
    }

    public void setQuantidadeBovinosDeCorte(int quantidadeBovinosDeCorte) {
        this.quantidadeBovinosDeCorte = quantidadeBovinosDeCorte;
    }

    public int getQuantidadeBovinosLeiteiros() {
        return quantidadeBovinosLeiteiros;
    }

    public void setQuantidadeBovinosLeiteiros(int quantidadeBovinosLeiteiros) {
        this.quantidadeBovinosLeiteiros = quantidadeBovinosLeiteiros;
    }

    public int getQuantidadeCaprinosDeCorte() {
        return quantidadeCaprinosDeCorte;
    }

    public void setQuantidadeCaprinosDeCorte(int quantidadeCaprinosDeCorte) {
        this.quantidadeCaprinosDeCorte = quantidadeCaprinosDeCorte;
    }

    public int getQuantidadeCaprinosLeiteiros() {
        return quantidadeCaprinosLeiteiros;
    }

    public void setQuantidadeCaprinosLeiteiros(int quantidadeCaprinosLeiteiros) {
        this.quantidadeCaprinosLeiteiros = quantidadeCaprinosLeiteiros;
    }

    public int getQuantidadeColmeias() {
        return quantidadeColmeias;
    }

    public void setQuantidadeColmeias(int quantidadeColmeias) {
        this.quantidadeColmeias = quantidadeColmeias;
    }

    public int getQuantidadeMelKg() {
        return quantidadeMelKg;
    }

    public void setQuantidadeMelKg(int quantidadeMelKg) {
        this.quantidadeMelKg = quantidadeMelKg;
    }

    public int getQuantidadeSuinos() {
        return quantidadeSuinos;
    }

    public void setQuantidadeSuinos(int quantidadeSuinos) {
        this.quantidadeSuinos = quantidadeSuinos;
    }

     
     
    public List<Agricultura> getAgricultura() {
        return agricultura;
    }

    public void setAgricultura(List<Agricultura> agricultura) {
        this.agricultura = agricultura;
    }
    
     
     
    public Date getDataDaCriacao() {
        return dataDaCriacao;
    }

    public void setDataDaCriacao(Date dataDaCriacao) {
        this.dataDaCriacao = dataDaCriacao;
    }

    public Date getDataDaModificacao() {
        return dataDaModificacao;
    }

    public void setDataDaModificacao(Date dataDaModificacao) {
        this.dataDaModificacao = dataDaModificacao;
    }
    
    

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

       
    public Long getId() {
        return id;
    }

    public Date getData() {
        return data;
    }

    public Distrito getDistrito() {
        return distrito;
    }

    public void setDistrito(Distrito distrito) {
        this.distrito = distrito;
    }

    public Localidade getLocalidade() {
        return localidade;
    }

    public void setLocalidade(Localidade localidade) {
        this.localidade = localidade;
    }
    
//
//    public String getDistrito() {
//        return distrito;
//    }
//
    
//
//    public String getLocalidade() {
//        return localidade;
//    }

    public Produtor getProdutor() {
        return produtor;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setData(Date data) {
        this.data = data;
    }

//    public void setDistrito(String distrito) {
//        this.distrito = distrito;
//    }


//    public void setLocalidade(String localidade) {
//        this.localidade = localidade;
//    }

    public void setProdutor(Produtor produtor) {
        this.produtor = produtor;
    }

    public Usuario getTecnico() {
        return tecnico;
    }

    public void setTecnico(Usuario tecnico) {
        this.tecnico = tecnico;
    }

    
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MapeamentoDeUnidadesProdutivas other = (MapeamentoDeUnidadesProdutivas) obj;
        if ((this.distrito == null) ? (other.distrito != null) : !this.distrito.equals(other.distrito)) {
            return false;
        }
        if ((this.localidade == null) ? (other.localidade != null) : !this.localidade.equals(other.localidade)) {
            return false;
        }
        if (this.produtor != other.produtor && (this.produtor == null || !this.produtor.equals(other.produtor))) {
            return false;
        }
        if (this.data != other.data && (this.data == null || !this.data.equals(other.data))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + (this.distrito != null ? this.distrito.hashCode() : 0);
        hash = 37 * hash + (this.localidade != null ? this.localidade.hashCode() : 0);
        hash = 37 * hash + (this.produtor != null ? this.produtor.hashCode() : 0);
        hash = 37 * hash + (this.data != null ? this.data.hashCode() : 0);
        return hash;
    }
   
    
}
