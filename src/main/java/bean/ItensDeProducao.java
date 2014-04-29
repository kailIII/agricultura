package bean;

import entities.annotations.PropertyDescriptor;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Java
 */
@Embeddable
public class ItensDeProducao implements Serializable {
    

    @Column(length=20)
    @PropertyDescriptor(displayName = "Hortaliças:")
    private boolean  hortalicas = false;

    @Column(length=20)
    @PropertyDescriptor(displayName = "Leite Bovino:")
    private boolean  leiteBovino = false;

    @Column(length=20)
    @PropertyDescriptor(displayName = "Bovino de Corte:")
    private boolean  bovinoDeCorte = false;

    @Column(length=20)
    @PropertyDescriptor(displayName = "Leite Caprino:")
    private boolean  leiteCaprino = false;

    @Column(length=20)
    @PropertyDescriptor(displayName = "Caprino de Corte:")
    private boolean  caprinoDeCorte = false;

    @Column(length=20)
    @PropertyDescriptor(displayName = "Galinha Caipira:")
    private boolean  galinhaCaipira = false;

    @Column(length=20)
    @PropertyDescriptor(displayName = "Doce Caseiro:")
    private boolean  doceCaseiro = false;

    @Column(length=20)
    @PropertyDescriptor(displayName = "Mel de Abelha:")
    private boolean  melDeAbelha = false;

    @Column(length = 20)
    @PropertyDescriptor(displayName = "Caju:")
    private boolean  caju = false;

    @Column(length = 20)
    @PropertyDescriptor(displayName = "Ovino de Corte:")
    private boolean  ovinoDeCorte = false;

    @Column(length = 20)
    @PropertyDescriptor(displayName = "Frutas Diversas:")
    private boolean  frutasDiversas = false;

    @Column(length = 20)
    @PropertyDescriptor(displayName = "Queijo:")
    private boolean  queijo = false;

    public boolean isBovinoDeCorte() {
        return bovinoDeCorte;
    }

    public boolean isCaju() {
        return caju;
    }

    public boolean isCaprinoDeCorte() {
        return caprinoDeCorte;
    }

    public boolean isDoceCaseiro() {
        return doceCaseiro;
    }

    public boolean isFrutasDiversas() {
        return frutasDiversas;
    }

    public boolean isGalinhaCaipira() {
        return galinhaCaipira;
    }

    public boolean isHortalicas() {
        return hortalicas;
    }

    public boolean isLeiteBovino() {
        return leiteBovino;
    }

    public boolean isLeiteCaprino() {
        return leiteCaprino;
    }

    public boolean isMelDeAbelha() {
        return melDeAbelha;
    }

    public boolean isOvinoDeCorte() {
        return ovinoDeCorte;
    }

    public boolean isQueijo() {
        return queijo;
    }

    public void setBovinoDeCorte(boolean bovinoDeCorte) {
        this.bovinoDeCorte = bovinoDeCorte;
    }

    public void setCaju(boolean caju) {
        this.caju = caju;
    }

    public void setCaprinoDeCorte(boolean caprinoDeCorte) {
        this.caprinoDeCorte = caprinoDeCorte;
    }

    public void setDoceCaseiro(boolean doceCaseiro) {
        this.doceCaseiro = doceCaseiro;
    }

    public void setFrutasDiversas(boolean frutasDiversas) {
        this.frutasDiversas = frutasDiversas;
    }

    public void setGalinhaCaipira(boolean galinhaCaipira) {
        this.galinhaCaipira = galinhaCaipira;
    }

    public void setHortalicas(boolean hortalicas) {
        this.hortalicas = hortalicas;
    }

    public void setLeiteBovino(boolean leiteBovino) {
        this.leiteBovino = leiteBovino;
    }

    public void setLeiteCaprino(boolean leiteCaprino) {
        this.leiteCaprino = leiteCaprino;
    }

    public void setMelDeAbelha(boolean melDeAbelha) {
        this.melDeAbelha = melDeAbelha;
    }

    public void setOvinoDeCorte(boolean ovinoDeCorte) {
        this.ovinoDeCorte = ovinoDeCorte;
    }

    public void setQueijo(boolean queijo) {
        this.queijo = queijo;
    }
    
    
    
}
