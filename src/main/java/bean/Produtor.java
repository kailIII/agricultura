package bean;


import entities.Context;
import entities.Repository;
import entities.annotations.*;
import entities.dao.DAOConstraintException;
import entities.dao.DAOException;
import entities.dao.DAOValidationException;
import entities.descriptor.PropertyType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import security.Usuario;
import util.jsf.Types;
/**
 *
 * @author Java
 */
@Entity


@Table(uniqueConstraints = {
  @UniqueConstraint(columnNames = {"cpf"},
 name = "Produtor")
})


@Views({
    
//    @View(name = "PerfilProdutor", 
//            members = "[["
//    + "[*codigo;"
//    + "         *nomeCompleto;"
//    + "         *localidade;"
//    + "         *distrito;"
//    + "         *sexo];"
//    + "         [*dataDeNascimento;"
//    + "         *telefone]]"
//    + "]",
//    title = "Perfil do Produtor",
//     namedQuery = "From Produtor Where codigo = :codigo",
//    params = @Param(name = "codigo", value = "#{codigo}", type = Integer.class)),
//  
    
    
    @View(name = "ManterProdutor",
    title = "Produtores",
    filters="[nomeCompleto,codigo,Ctrl.DAO.filter()]", 
    members = "[["
    + "Dados do Produtor[foto;"
    + "			codigo;"
    + "         nomeCompleto;"
    + "         distrito;"
    + "         localidade;"
    + "         propriedade;"
    + "         sexo;"
    + "         cpf;"
    + "         rg;"
    + "         dap;"
    + "         dataDeNascimento;"
    + "         telefone;"
    + "         adicionarFamiliar()];"
        
    + "         parentes<nome,parente,removerFamiliar()>; "
        + "[*usuarioQueModificou;*dataDaModificacao]],"
        
        
    + "Informações econômicas [situacaoFundiaria;"
    + "                  associacao;"
    + "                  qual_associacao;"
    + "                  areaDisponivel;"
    + "                  qualidadeDoSolo;"
    + "                  rendaFamiliar;"
    + "                  tipoDeMoradia;"
    + "                  infraEstruturaExistente;"
    + "                  suporteForrageiro;"
    + "                  atividadesEconomicas;"
    + "                  programaSocialQueParticipa;"
    + "                  eletricidade;"
    + "                  tipo_ligacao;"
    + "                  programaDeBeneficiamento;"
    + "                  financiamento;"
    + "                  qualFinanciamento;"
    + "                  Fontes Hídricas[[[[acude]],[[poco]],[[cisterna]]];[ outraFonteHidrica]]"

    + "]"
    + "]"
        ,
     params = {@Param(name = "distrito", value = "#{dataItem.distrito}"),@Param(name = "localidade", value = "#{dataItem.localidade}")},
    template = "@CRUD_PAGE",    
    roles="Administrador,Tecnico",
    rows = 10
     )})


        
  

public class Produtor implements Serializable {

     public enum SituacaoFundiaria{
        ARRENDATARIO,
        MEEIRO,
        PARCEIRO,
        POSSEIRO,
        PROPRIETARIO
      
     }
     
     
    
   
     public enum TipoDeMoradia {
       TAIPA, ALVENARIA, MISTA
    }

     public enum Sexo {
        MASCULINO, FEMININO
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long codigo;
    
    
    // Campo de foto
    @Lob
    @Column(length=10240) // 10kb
    @Editor(propertyType=PropertyType.IMAGE,
			outputComponentProperties = {@Param(name="width", value="300"),
					 	 		 		 @Param(name="lenght", value="300")})
    private byte[] foto;

    @NotEmpty(message = "Informe o nome do Produtor")
    @Column(length = 60)
    @PropertyDescriptor(index = 2, autoFilter = true, displayName = "Nome Completo", displayWidth=60)
    private String nomeCompleto;

    @Editor(inputComponentProperties=@Param(name="reRender",value="localidade"))
    @ManyToOne
    private Distrito distrito;

    @Editor(namedQuery="From Localidade where distrito = :distrito",
    inputComponentProperties=@Param(name="reRender",value="propriedade"))
    @ManyToOne
    private Localidade localidade;
 
    @Editor(namedQuery="From Propriedade where localidade = :localidade")
    @ManyToOne
    private Propriedade propriedade;

    
    @PropertyDescriptor(index = 5, displayName = "Sexo")
    private Sexo sexo;

    @NotEmpty(message = "Informe o CPF")
    @Column(length = 15)
    @PropertyDescriptor(index = 6, displayName = "CPF", mask="999.999.999-99")
    private String cpf;
    
    @NotEmpty(message = "Informe o RG")
    @Column(length = 15)
    @PropertyDescriptor(index = 7, displayName = "RG")
    private String rg;
      
    @Column(length = 15)
    @PropertyDescriptor(index = 8, displayName = "DAP")
    private String dap;
    
    
    @PropertyDescriptor(index = 9)
    @NotNull(message = "Informe a data de nascimento do Produtor")
    @Temporal(javax.persistence.TemporalType.DATE)
    @Column(length = 15)
    private Date dataDeNascimento;

    @Column(length = 15)
    @PropertyDescriptor(index = 10, mask="(99)99999999")
    private String telefone;
    
    @OneToMany(cascade = CascadeType.ALL,   mappedBy="produtor")
    private List<Parente> parentes = new ArrayList<Parente>();
 
    @PropertyDescriptor(displayName = "Usuário que modificou")
    @ManyToOne
    private Usuario usuarioQueModificou = Usuario.getCurrentUser();
    
    @PropertyDescriptor(displayName = "Data da última modificação")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataDaModificacao = new Date();

    
   private SituacaoFundiaria situacaoFundiaria;
   
   @Column(length = 70)
   private String areaDisponivel;
   
   @Column(length = 70)
   private String qualidadeDoSolo;
   
   @PropertyDescriptor(displayName="Açude")
   private boolean acude;
   
   @PropertyDescriptor(displayName="Poço")
   private boolean poco;
   
   private boolean cisterna;
   
   @Column(length = 70)
   @PropertyDescriptor(displayName="Outra Fonte Hídrica")
   private String outraFonteHidrica;

   @ManyToOne
   private RendaFamiliar rendaFamiliar;
   private TipoDeMoradia tipoDeMoradia;
   @Column(length = 70)
   private String infraEstruturaExistente;
   @Column(length = 70)
   private String suporteForrageiro;
   @Column(length = 70)
   private String atividadesEconomicas;
   @Column(length = 70)
   private String programaSocialQueParticipa;
   private boolean financiamento;

   @Column(length = 70)
   @PropertyDescriptor(displayName = "Qual o Financiamento?")
   private String qualFinanciamento;

   @Column(length = 70)
   private String programaDeBeneficiamento;
   
   @PropertyDescriptor(displayName = "Associação")
   private boolean associacao;
   
   @PropertyDescriptor(displayName = "Qual Associação?")
   private String qual_associacao;
   
   private boolean eletricidade;
   
   @PropertyDescriptor(displayName = "Qual o tipo de ligação?")
   private String tipo_ligacao;

    
//   @ActionDescriptor(displayName="Listar relatórios",value="Relatórios de Assistência Técnica")
//    public String goView2(){
//    Context.setValue("produtor",this);
//    return "redirect:main.jsf?view=bean.RelatorioAssistenciaTecnica@View2";
//    }

    
   @ActionDescriptor(refreshView=true, image = "image:save",shortDescription="Salvar")
   @Transaction
    public void testando() throws DAOException, DAOValidationException, DAOConstraintException {
      	
    setDataDaModificacao( new Date());
    setUsuarioQueModificou( Usuario.getCurrentUser());
    
    }
    
    public void adicionarFamiliar() {
        Parente par = new Parente();
        par.setProdutor(this);
        parentes.add(par);
       
    }

    public Propriedade getPropriedade() {
        return propriedade;
    }

    public void setPropriedade(Propriedade propriedade) {
        this.propriedade = propriedade;
    }

    
    
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
    
    

    public boolean isAssociacao() {
        return associacao;
    }

    public void setAssociacao(boolean associacao) {
        this.associacao = associacao;
    }

    public boolean isEletricidade() {
        return eletricidade;
    }

    public void setEletricidade(boolean eletricidade) {
        this.eletricidade = eletricidade;
    }

    public String getQual_associacao() {
        return qual_associacao;
    }

    public void setQual_associacao(String qual_associacao) {
        this.qual_associacao = qual_associacao;
    }

    public String getTipo_ligacao() {
        return tipo_ligacao;
    }

    public void setTipo_ligacao(String tipo_ligacao) {
        this.tipo_ligacao = tipo_ligacao;
    }

    
    
    public String getQualFinanciamento() {
        return qualFinanciamento;
    }

    public void setQualFinanciamento(String qualFinanciamento) {
        this.qualFinanciamento = qualFinanciamento;
    }
    
    

    public boolean isCisterna() {
        return cisterna;
    }

    public void setCisterna(boolean cisterna) {
        this.cisterna = cisterna;
    }
    
    

    public boolean isAcude() {
        return acude;
    }

    public void setAcude(boolean acude) {
        this.acude = acude;
    }

    public String getAreaDisponivel() {
        return areaDisponivel;
    }

    public void setAreaDisponivel(String areaDisponivel) {
        this.areaDisponivel = areaDisponivel;
    }

    public String getAtividadesEconomicas() {
        return atividadesEconomicas;
    }

    public void setAtividadesEconomicas(String atividadesEconomicas) {
        this.atividadesEconomicas = atividadesEconomicas;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getDap() {
        return dap;
    }

    public void setDap(String dap) {
        this.dap = dap;
    }

    public Date getDataDeNascimento() {
        return dataDeNascimento;
    }

    public void setDataDeNascimento(Date dataDeNascimento) {
        this.dataDeNascimento = dataDeNascimento;
    }

    public Distrito getDistrito() {
        return distrito;
    }

    public void setDistrito(Distrito distrito) {
        this.distrito = distrito;
    }

    public boolean isFinanciamento() {
        return financiamento;
    }

    public void setFinanciamento(boolean financiamento) {
        this.financiamento = financiamento;
    }

    public String getInfraEstruturaExistente() {
        return infraEstruturaExistente;
    }

    public void setInfraEstruturaExistente(String infraEstruturaExistente) {
        this.infraEstruturaExistente = infraEstruturaExistente;
    }

    public Localidade getLocalidade() {
        return localidade;
    }

    public void setLocalidade(Localidade localidade) {
        this.localidade = localidade;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getOutraFonteHidrica() {
        return outraFonteHidrica;
    }

    public void setOutraFonteHidrica(String outraFonteHidrica) {
        this.outraFonteHidrica = outraFonteHidrica;
    }

    public List<Parente> getParentes() {
        return parentes;
    }

    public void setParentes(List<Parente> parentes) {
        this.parentes = parentes;
    }

    public boolean isPoco() {
        return poco;
    }

    public void setPoco(boolean poco) {
        this.poco = poco;
    }

    public String getProgramaDeBeneficiamento() {
        return programaDeBeneficiamento;
    }

    public void setProgramaDeBeneficiamento(String programaDeBeneficiamento) {
        this.programaDeBeneficiamento = programaDeBeneficiamento;
    }

    public String getProgramaSocialQueParticipa() {
        return programaSocialQueParticipa;
    }

    public void setProgramaSocialQueParticipa(String programaSocialQueParticipa) {
        this.programaSocialQueParticipa = programaSocialQueParticipa;
    }

    public String getQualidadeDoSolo() {
        return qualidadeDoSolo;
    }

    public void setQualidadeDoSolo(String qualidadeDoSolo) {
        this.qualidadeDoSolo = qualidadeDoSolo;
    }
//
//    public String getQuantitativoDeRebanho() {
//        return quantitativoDeRebanho;
//    }
//
//    public void setQuantitativoDeRebanho(String quantitativoDeRebanho) {
//        this.quantitativoDeRebanho = quantitativoDeRebanho;
//    }

    public RendaFamiliar getRendaFamiliar() {
        return rendaFamiliar;
    }

    public void setRendaFamiliar(RendaFamiliar rendaFamiliar) {
        this.rendaFamiliar = rendaFamiliar;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public SituacaoFundiaria getSituacaoFundiaria() {
        return situacaoFundiaria;
    }

    public void setSituacaoFundiaria(SituacaoFundiaria situacaoFundiaria) {
        this.situacaoFundiaria = situacaoFundiaria;
    }

    public String getSuporteForrageiro() {
        return suporteForrageiro;
    }

    public void setSuporteForrageiro(String suporteForrageiro) {
        this.suporteForrageiro = suporteForrageiro;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public TipoDeMoradia getTipoDeMoradia() {
        return tipoDeMoradia;
    }

    public void setTipoDeMoradia(TipoDeMoradia tipoDeMoradia) {
        this.tipoDeMoradia = tipoDeMoradia;
    }
    
    public byte[] getFoto() {
		return foto;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
	}

@ActionDescriptor(componenteType= Types.COMMAND_LINK,value="Ver Produtor")
    public String verProdutor() {
        Context.setValue("codigo", this.getCodigo());
      
        return "redirect:main.jsf?view=bean.Produtor@PerfilProdutor";
    }
  
     
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Produtor other = (Produtor) obj;
        if (this.codigo != other.codigo && (this.codigo == null || !this.codigo.equals(other.codigo))) {
            return false;
        }
        if ((this.cpf == null) ? (other.cpf != null) : !this.cpf.equals(other.cpf)) {
            return false;
        }
        if ((this.rg == null) ? (other.rg != null) : !this.rg.equals(other.rg)) {
            return false;
        }
        if ((this.dap == null) ? (other.dap != null) : !this.dap.equals(other.dap)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + (this.codigo != null ? this.codigo.hashCode() : 0);
        hash = 23 * hash + (this.cpf != null ? this.cpf.hashCode() : 0);
        hash = 23 * hash + (this.rg != null ? this.rg.hashCode() : 0);
        hash = 23 * hash + (this.dap != null ? this.dap.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return nomeCompleto;
    }
    
    

}
