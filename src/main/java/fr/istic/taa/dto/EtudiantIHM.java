package fr.istic.taa.dto;

import fr.istic.taa.domain.Etudiant;
import fr.istic.taa.domain.enumeration.Sexe;

import java.util.Objects;

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
}
