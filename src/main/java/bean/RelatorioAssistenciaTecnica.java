package bean;


import entities.annotations.*;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import org.hibernate.validator.constraints.NotEmpty;
import security.Usuario;



@Entity
@Table(uniqueConstraints = {
  @UniqueConstraint(columnNames = {"tecnico_usuario","distrito_id","localidade_id","produtor_codigo","data"},
 name = "RelatorioAssistenciaTecnica")
})

@Views({
   
    @View(name = "RATAdmin", filters="[distrito,produtor,produtor.cpf,tecnico,data,Ctrl.DAO.filter()]", 
            members = "[["
    + "[*tecnico:2;"
    + "         distrito;"
    + "         localidade;"
    + "         produtor;"
//        + "         produtor,produtor.verProdutor();"
    + "         data];"
 
    + "         [situacoesDetectadas;"
    + "         orientacoesTecnicasAplicadas];[*dataDaCriacao;*dataDaModificacao]]"
    + "]",
    template = "@CRUD_PAGE",  
      params = {@Param(name = "distrito", value = "#{dataItem.distrito}"),@Param(name = "localidade", value = "#{dataItem.localidade}")},
    title = "Relatórios de Assistência Técnica",
    roles="Administrador"),

    @View(name = "RATUser", filters="[distrito,produtor,produtor.cpf,data,Ctrl.DAO.filter()]", 
            members = "[["
    + "[*tecnico;"
    + "         distrito;"
    + "         localidade;"
    + "         produtor;"
    + "         data];"
    + "         [situacoesDetectadas;"
    + "         orientacoesTecnicasAplicadas];[*dataDaCriacao;*dataDaModificacao]]"
    + "]",
    template = "@CRUD_PAGE",  
    title = "Relatórios de Assistência Técnica",
    roles="Tecnico",
    namedQuery="Select ot From RelatorioAssistenciaTecnica ot where ot.tecnico = :tecnico",
     params = {@Param(name = "distrito", value = "#{dataItem.distrito}"),@Param(name = "tecnico", value = "#{context.currentUser()}")}),
    
})
public class RelatorioAssistenciaTecnica implements Serializable {

   

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @PropertyDescriptor(index = 1, hidden = true)
    private Integer id;
    
    @NotNull(message = "Informe o Produtor")
    @ManyToOne
    @PropertyDescriptor(index = 2, autoFilter = true, displayWidth=50)
    private Produtor produtor;

    @Editor(inputComponentProperties=@Param(name="reRender",value="localidade"))
    @ManyToOne
    private Distrito distrito;

    @Editor(namedQuery="From Localidade where distrito = :distrito")
    @ManyToOne
    private Localidade localidade;
     
//    @PropertyDescriptor(index = 3, autoFilter = true)
//    @Column(length = 50)
//    @NotEmpty(message = "Informe a localidade do produtor")
//    private String localidade;
//     
//    @PropertyDescriptor(index = 4, autoFilter = true)
//    @Column(length = 50)
//    @NotEmpty(message = "Informe o distrito do produtor")
//    private String distrito;
    @NotNull
    @ManyToOne
    @PropertyDescriptor(displayName="Técnico")
    private Usuario tecnico = Usuario.getCurrentUser();
        
    @Past
    @NotNull(message = "Informe a Data da reserva")
    @Temporal(javax.persistence.TemporalType.DATE)
    @PropertyDescriptor(index = 5, autoFilter = true)
    private Date data = new Date();
    
    @Lob
    @Column(length=50)
    @PropertyDescriptor(index = 6, displayName="Situações detectadas", shortDescription="Situações detectadas na visita", displayRows=5, displayWidth=100)
    private String situacoesDetectadas;
    
    @Lob
    @Column(length=50)
    @PropertyDescriptor(index = 7, displayName= "Orientações técnicas",shortDescription="Orientações técnicas aplicadas na visita" , displayRows=5, displayWidth=100)
    private String orientacoesTecnicasAplicadas;
    
    @PropertyDescriptor(displayName = "Data da criação")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataDaCriacao = new Date();
    
    @PropertyDescriptor(displayName = "Data da última modificação")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataDaModificacao = new Date();

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
    
    

    public Usuario getTecnico() {
        return tecnico;
    }

    public void setTecnico(Usuario tecnico) {
        this.tecnico = tecnico;
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
    
    

//    public String getDistrito() {
//        return distrito;
//    }
//
//    public String getLocalidade() {
//        return localidade;
//    }
//
//    public void setDistrito(String distrito) {
//        this.distrito = distrito;
//    }
//
//    public void setLocalidade(String localidade) {
//        this.localidade = localidade;
//    }

    public Integer getId() {
        return id;
    }

    public Produtor getProdutor() {
        return produtor;
    }

    public String getOrientacoesTecnicasAplicadas() {
        return orientacoesTecnicasAplicadas;
    }

    public void setOrientacoesTecnicasAplicadas(String orientacoesTecnicasAplicadas) {
        this.orientacoesTecnicasAplicadas = orientacoesTecnicasAplicadas;
    }

    public String getSituacoesDetectadas() {
        return situacoesDetectadas;
    }

    public void setSituacoesDetectadas(String situacoesDetectadas) {
        this.situacoesDetectadas = situacoesDetectadas;
    }

    
        
    public void setData(Date data) {
        this.data = data;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setProdutor(Produtor produtor) {
        this.produtor = produtor;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final RelatorioAssistenciaTecnica other = (RelatorioAssistenciaTecnica) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
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
        int hash = 7;
        hash = 71 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 71 * hash + (this.produtor != null ? this.produtor.hashCode() : 0);
        hash = 71 * hash + (this.data != null ? this.data.hashCode() : 0);
        return hash;
    }

   
    
}
