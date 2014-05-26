/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package methods;

import bean.*;
import entities.Context;
import entities.CurrentUser;
import java.util.Date;
import org.hibernate.HibernateException;
import org.hibernate.event.SaveOrUpdateEvent;
import org.hibernate.event.SaveOrUpdateEventListener;
import security.Usuario;

/**
 *
 * @author Am1b10s
 */
public class Salvar implements SaveOrUpdateEventListener {
    @Override
    public void onSaveOrUpdate(SaveOrUpdateEvent soue) throws HibernateException {
        if (soue.getObject() instanceof Produtor) {
            Produtor produtor = (Produtor) soue.getObject();
            CurrentUser currentUser = (CurrentUser) Context.getCurrentUser();
            produtor.setDataDaModificacao(new Date());
            produtor.setUsuarioQueModificou (Usuario.getCurrentUser());
       }
       else if (soue.getObject() instanceof OrdemDeServico) {
            OrdemDeServico ordemDeServico = (OrdemDeServico) soue.getObject();
            CurrentUser currentUser = (CurrentUser) Context.getCurrentUser();
            ordemDeServico.setDataDaModificacao(new Date());
            ordemDeServico.setUsuarioQueModificou (Usuario.getCurrentUser());
       } 
       else if (soue.getObject() instanceof RelatorioAssistenciaTecnica) {
            RelatorioAssistenciaTecnica relatorioAssistenciaTecnica = (RelatorioAssistenciaTecnica) soue.getObject();
            relatorioAssistenciaTecnica.setDataDaModificacao(new Date());
            
       } 
       else if (soue.getObject() instanceof MapeamentoDeUnidadesProdutivas) {
            MapeamentoDeUnidadesProdutivas mapeamentoDeUnidadesProdutivas = (MapeamentoDeUnidadesProdutivas) soue.getObject();
            mapeamentoDeUnidadesProdutivas.setDataDaModificacao(new Date());
            
       } 
       else if (soue.getObject() instanceof DiarioDeCampo) {
            DiarioDeCampo diarioDeCampo = (DiarioDeCampo) soue.getObject();
            diarioDeCampo.setDataDaModificacao(new Date());
            
       } 
}
}