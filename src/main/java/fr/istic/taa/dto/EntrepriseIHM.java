package fr.istic.taa.dto;

import fr.istic.taa.domain.DonneesEntreprise;
import fr.istic.taa.domain.Entreprise;

import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * Created by guillaume on 27/10/16.
 */
public class EntrepriseIHM extends Entreprise {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String nom;

    private String siret;

    private Integer effectif;

    private String adresse;

    private String ville;

    private String codepostal;

    private String tel;

    private String url;

    private String commentaire;

    private String mail;

    private ZonedDateTime dateModif;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getSiret() {
        return siret;
    }

    public void setSiret(String siret) {
        this.siret = siret;
    }

    public Integer getEffectif() {
        return effectif;
    }

    public void setEffectif(Integer effectif) {
        this.effectif = effectif;
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

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
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
        EntrepriseIHM entrepriseIHM = (EntrepriseIHM) o;
        if (entrepriseIHM.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, entrepriseIHM.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Entreprise{" +
            "id=" + id +
            ", nom='" + nom + "'" +
            ", siret='" + siret + "'" +
            ", effectif='" + effectif + "'" +
            ", adresse='" + adresse + "'" +
            ", ville='" + ville + "'" +
            ", codepostal='" + codepostal + "'" +
            ", tel='" + tel + "'" +
            ", url='" + url + "'" +
            ", commentaire='" + commentaire + "'" +
            ", mail='" + mail + "'" +
            '}';
    }

    public static EntrepriseIHM create(Entreprise ent, DonneesEntreprise don) {
        if (ent == null || don == null)
            return null;

        EntrepriseIHM res = new EntrepriseIHM();
        res.setId(ent.getId());
        res.setSiret(ent.getSiret());
        res.setNom(ent.getNom());
        res.setEffectif(ent.getEffectif());
        res.setAdresse(don.getAdresse());
        res.setCodepostal(don.getCodepostal());
        res.setVille(don.getVille());
        res.setTel(don.getTel());
        res.setUrl(don.getUrl());
        res.setCommentaire(don.getCommentaire());
        res.setMail(don.getMail());
        res.setDateModif(don.getDatemodif());
        return res;
    }

    public void setEntreprise(Entreprise ent) {
        this.setId(ent.getId());
        this.setSiret(ent.getSiret());
        this.setNom(ent.getNom());
        this.setEffectif(ent.getEffectif());
    }

    public void setDonnees(DonneesEntreprise don) {
        this.setAdresse(don.getAdresse());
        this.setCodepostal(don.getCodepostal());
        this.setVille(don.getVille());
        this.setTel(don.getTel());
        this.setUrl(don.getUrl());
        this.setCommentaire(don.getCommentaire());
        this.setMail(don.getMail());
        this.setDateModif(don.getDatemodif());
    }

    public Entreprise createEntreprise() {
        Entreprise ent = new Entreprise();
        ent.setId(getId());
        ent.setSiret(getSiret());
        ent.setNom(getNom());
        ent.setEffectif(getEffectif());
        return ent;
    }

    public DonneesEntreprise createDonnees() {
        DonneesEntreprise don = new DonneesEntreprise();
        don.setAdresse(getAdresse());
        don.setCodepostal(getCodepostal());
        don.setVille(getVille());
        don.setTel(getTel());
        don.setUrl(getUrl());
        don.setCommentaire(getCommentaire());
        don.setMail(getMail());
        don.setDatemodif(getDateModif());
        return don;
    }

    public ZonedDateTime getDateModif() {
        return dateModif;
    }

    public void setDateModif(ZonedDateTime dateModif) {
        this.dateModif = dateModif;
    }
}
