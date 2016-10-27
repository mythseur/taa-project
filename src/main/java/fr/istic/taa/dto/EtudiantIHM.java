package fr.istic.taa.dto;

import java.util.Objects;

import fr.istic.taa.domain.DonneesEtudiant;
import fr.istic.taa.domain.Etudiant;
import fr.istic.taa.domain.enumeration.Sexe;

public class EtudiantIHM extends Etudiant{
    private static final long serialVersionUID = 1L;

    private Long id;

    private String iNe;

    private String nom;

    private String prenom;

    private Sexe sexe;

    private String adresse;

    private String ville;

    private String codepostal;

    private String telperso;

    private String telmobile;

    private String mail;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getiNe() {
        return iNe;
    }

    public void setiNe(String iNe) {
        this.iNe = iNe;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Sexe getSexe() {
        return sexe;
    }

    public void setSexe(Sexe sexe) {
        this.sexe = sexe;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getCodepostal() {
        return codepostal;
    }

    public void setCodepostal(String codepostal) {
        this.codepostal = codepostal;
    }

    public String getTelperso() {
        return telperso;
    }

    public void setTelperso(String telperso) {
        this.telperso = telperso;
    }

    public String getTelmobile() {
        return telmobile;
    }

    public void setTelmobile(String telmobile) {
        this.telmobile = telmobile;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EtudiantIHM etudiantIHM = (EtudiantIHM) o;
        if (etudiantIHM.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, etudiantIHM.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Etudiant{" +
            "id=" + id +
            ", iNe='" + iNe + "'" +
            ", nom='" + nom + "'" +
            ", prenom='" + prenom + "'" +
            ", sexe='" + sexe + "'" +
            ", adresse='" + adresse + "'" +
            ", ville='" + ville + "'" +
            ", codepostal='" + codepostal + "'" +
            ", telperso='" + telperso + "'" +
            ", telmobile='" + telmobile + "'" +
            ", mail='" + mail + "'" +
            '}';
    }

    public static EtudiantIHM create(Etudiant etu, DonneesEtudiant don) {
        EtudiantIHM res = new EtudiantIHM();
        if(etu != null){
            res.setId(etu.getId());
            res.setiNe(etu.getiNe());
            res.setPrenom(etu.getPrenom());
            res.setNom(etu.getNom());
            res.setSexe(etu.getSexe());
        }
        if(don != null){
            res.setAdresse(don.getAdresse());
            res.setCodepostal(don.getCodepostal());
            res.setVille(don.getVille());
            res.setMail(don.getMail());
            res.setTelmobile(don.getTelmobile());
            res.setTelperso(don.getTelperso());
        }
        return res;
    }

    public Etudiant createEtudiant(){
        Etudiant etu = new Etudiant();
        etu.setId(getId());
        etu.setiNe(getiNe());
        etu.setPrenom(getPrenom());
        etu.setNom(getNom());
        etu.setSexe(getSexe());
        return etu;
    }

    public DonneesEtudiant createDonnees(){
        DonneesEtudiant don = new DonneesEtudiant();
        don.setAdresse(getAdresse());
        don.setCodepostal(getCodepostal());
        don.setVille(getVille());
        don.setTelperso(getTelperso());
        don.setTelmobile(getTelmobile());
        don.setMail(getMail());
        return don;
    }
}
