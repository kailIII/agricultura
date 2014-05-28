/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import entities.Repository;
import entities.annotations.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import org.hibernate.validator.constraints.NotEmpty;
import security.Usuario;

/**
 *
 * @author Luis Lima
 */
@Entity
@Table(uniqueConstraints = {
  @UniqueConstraint(columnNames = {"tecnico_usuario","projeto_id","acao_id","referencia","produtor_codigo","localidade_id"},
 name = "DdC")
})
@Views({
    @View(name="DDCAdmin",
    title="Diários de Campo",
    filters="[distrito,produtor,produtor.cpf,tecnico,referencia,Ctrl.DAO.filter()]",
    members ="["
              + "     [*tecnico:2;"
        + "         acao:2;"
        + "         projeto:2;"
        + "         referencia:2;"
        + "         produtor:2;"
        + "         distrito:2;"
        + "         localidade:2];"
//        + "         adicionarDiaDeVisita():2,"
//        + "         diasdevisita<diaDeVisita,removerVisita()>];"
        + "         [ sinteseDeAtividadesDesenvolvidas;"    
        + "         relatorioDeProblemasDetectados;"
        + "         orientacoesTecnicasRealizadas];[*dataDaCriacao;*dataDaModificacao]"
        + "]"
        ,
    template="@CRUD_PAGE",    
      params = {@Param(name = "distrito", value = "#{dataItem.distrito}"),@Param(name = "localidade", value = "#{dataItem.localidade}")},
    roles="Administrador",
    rows = 10), 

@View(name="DDCUser",
    title="Diários de Campo",
    filters="[distrito,produtor,produtor.cpf,referencia,Ctrl.DAO.filter()]",
    members ="["
        + "     [*tecnico:2;"
        + "         acao:2;"
        + "         projeto:2;"
        + "         referencia:2;"
        + "         produtor:2;"
        + "         distrito:2;"
        + "         localidade:2];"
                  
//        + "         adicionarDiaDeVisita():2,"
//        + "         diasdevisita<diaDeVisita,removerVisita()>];"
        + "         [sinteseDeAtividadesDesenvolvidas;"    
        + "         relatorioDeProblemasDetectados;"
        + "         orientacoesTecnicasRealizadas];[*dataDaCriacao;*dataDaModificacao]"
        + "]"        ,
    template="@CRUD_PAGE",
    roles="Tecnico",
    namedQuery="Select ot From DiarioDeCampo ot where ot.tecnico = :tecnico",
    params = {@Param(name = "distrito", value = "#{dataItem.distrito}"),@Param(name = "tecnico", value = "#{context.currentUser()}")}),
    
    
})
public class DiarioDeCampo implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @PropertyDescriptor(index = 1, autoFilter = true)
    private Long codigo;
    
    @PropertyDescriptor(index = 2, displayName = "Ação")
    @ManyToOne
    @NotNull(message = "Informe a Ação")
    private Acao acao;
    
    @NotNull
    @ManyToOne
    @PropertyDescriptor(displayName="Técnico")
    private Usuario tecnico = Usuario.getCurrentUser();
   
    @PropertyDescriptor(index = 3)
    @ManyToOne
    @NotNull(message = "Informe o Projeto")
    private Projeto projeto;

    @Past
    @Temporal(javax.persistence.TemporalType.DATE)
    @PropertyDescriptor(index = 5, displayName = "Dia da Visita")
    @NotNull(message = "Informe o dia da Visita")
    private Date referencia = new Date();
    
    @Editor(inputComponentProperties=@Param(name="reRender",value="localidade"))
    @ManyToOne
    private Distrito distrito;

    @Editor(namedQuery="From Localidade where distrito = :distrito")
    @ManyToOne
    private Localidade localidade;
    
    @PropertyDescriptor(index = 7)
    @ManyToOne
    @NotNull(message = "Informe o Produtor")
    private Produtor produtor;
    
    @Lob
    @PropertyDescriptor(index = 13, displayName = "Síntese de Atividades Desenvolvidas", displayRows=4, displayWidth=65)
    private String sinteseDeAtividadesDesenvolvidas;
    
    @Lob
    @PropertyDescriptor(index = 14, displayName = "Relatório de Problemas Detectados", displayRows=4, displayWidth=65)
    private String relatorioDeProblemasDetectados;
    
    @Lob
    @PropertyDescriptor(index = 15, displayName = "Orientações Tecnicas Realizadas", displayRows=4, displayWidth=65)
    private String orientacoesTecnicasRealizadas;
    
    
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
    
    
    
//    @OneToMany(cascade = CascadeType.ALL,
//    mappedBy="diariodecampo")
//    @PropertyDescriptor(displayName="Dias de Visita")
//    private List<DiasDeVisita> diasdevisita = new ArrayList<DiasDeVisita>();
 
    
    @ActionDescriptor(displayName="Adicionar Ação1",value="Adicionar Ação2")
    public String addAcao(){
        return "redirect:main.jsf?view=bean.Acao@Adicionar";
    }        
    
    @ActionDescriptor(displayName="Adicionar Tecnico1",value="Adicionar Tecnico2")
    public String addTecnico(){
        return "redirect:main.jsf?view=bean.Tecnico@Adicionar";
    }
    
    @ActionDescriptor(displayName="Adicionar Projeto1",value="Adicionar Projeto2")
    public String addProjeto(){
        return "redirect:main.jsf?view=bean.Projeto@Adicionar";
    }
    
    @ActionDescriptor(displayName="Adicionar Área de Ação1",value="Adicionar Área de Ação2")
    public String addDistrito(){
        return "redirect:main.jsf?view=bean.Distrito@Adicionar";
    }
    
    @ActionDescriptor(displayName="Adicionar Produtor1",value="Adicionar Produtor2")
    public String addProdutor(){
        return "redirect:main.jsf?view=bean.Produtor@ManterProdutor";
    }

//    public void adicionarDiaDeVisita() {
// 
//        DiasDeVisita ddv = new DiasDeVisita();
//        ddv.setDiariodecampo(this);
//        diasdevisita.add(ddv);
//       
//    }
//
//    public List<DiasDeVisita> getDiasdevisita() {
//        return diasdevisita;
//    }
//
//    public void setDiasdevisita(List<DiasDeVisita> diasdevisita) {
//        this.diasdevisita = diasdevisita;
//    }
//    
//    

    
    public Usuario getTecnico() {
        return tecnico;
    }

    public void setTecnico(Usuario tecnico) {
        this.tecnico = tecnico;
    }
    
    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public Localidade getLocalidade() {
        return localidade;
    }

    public void setLocalidade(Localidade localidade) {
        this.localidade = localidade;
    }

    public Acao getAcao() {
        return acao;
    }

    public void setAcao(Acao acao) {
        this.acao = acao;
    }
    
//    public Date getDiaDeVisita01() {
//        return diaDeVisita01;
//    }
//
//    public void setDiaDeVisita01(Date diaDeVisita01) {
//        this.diaDeVisita01 = diaDeVisita01;
//    }
//
//    public Date getDiaDeVisita02() {
//        return diaDeVisita02;
//    }
//
//    public void setDiaDeVisita02(Date diaDeVisita02) {
//        this.diaDeVisita02 = diaDeVisita02;
//    }
//
//    public Date getDiaDeVisita03() {
//        return diaDeVisita03;
//    }
//
//    public void setDiaDeVisita03(Date diaDeVisita03) {
//        this.diaDeVisita03 = diaDeVisita03;
//    }
//
//    public Date getDiaDeVisita04() {
//        return diaDeVisita04;
//    }
//
//    public void setDiaDeVisita04(Date diaDeVisita04) {
//        this.diaDeVisita04 = diaDeVisita04;
//    }
//
//    public String getLocalidade() {
//        return localidade;
//    }
//
//    public void setLocalidade(String localidade) {
//        this.localidade = localidade;
//    }

    public Produtor getProdutor() {
        return produtor;
    }

    public void setProdutor(Produtor produtor) {
        this.produtor = produtor;
    }

    public Date getReferencia() {
        return referencia;
    }

    public void setReferencia(Date referencia) {
        this.referencia = referencia;
    }

    public Distrito getDistrito() {
        return distrito;
    }

    public void setDistrito(Distrito distrito) {
        this.distrito = distrito;
    }



    public Projeto getProjeto() {
        return projeto;
    }

    public void setProjeto(Projeto projeto) {
        this.projeto = projeto;
    }
    
    
    public String getOrientacoesTecnicasRealizadas() {
        return orientacoesTecnicasRealizadas;
    }

    public void setOrientacoesTecnicasRealizadas(String orientacoesTecnicasRealizadas) {
        this.orientacoesTecnicasRealizadas = orientacoesTecnicasRealizadas;
    }

    public String getRelatorioDeProblemasDetectados() {
        return relatorioDeProblemasDetectados;
    }

    public void setRelatorioDeProblemasDetectados(String relatorioDeProblemasDetectados) {
        this.relatorioDeProblemasDetectados = relatorioDeProblemasDetectados;
    }

    public String getSinteseDeAtividadesDesenvolvidas() {
        return sinteseDeAtividadesDesenvolvidas;
    }

    public void setSinteseDeAtividadesDesenvolvidas(String sinteseDeAtividadesDesenvolvidas) {
        this.sinteseDeAtividadesDesenvolvidas = sinteseDeAtividadesDesenvolvidas;
    }

    
    
}
