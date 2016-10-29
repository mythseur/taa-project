package fr.istic.taa.dto;

import java.time.LocalDate;
import java.util.Objects;

import fr.istic.taa.domain.Contact;
import fr.istic.taa.domain.Enseignant;
import fr.istic.taa.domain.Stage;

/**
 * Created by guillaume on 29/10/16.
 */
public class StageIHM extends Stage {

    private Long id;

    private LocalDate datedebut;

    private LocalDate datefin;

    private String sujet;

    private String service;

    private String details;

    private Integer jours;

    private Integer heures;

    private Integer versement;

    private EtudiantIHM etudiant;

    private Enseignant referent;

    private EntrepriseIHM entreprise;

    private Contact encadrant;

    private Contact responsable;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public LocalDate getDatedebut() {
        return datedebut;
    }

    @Override
    public void setDatedebut(LocalDate datedebut) {
        this.datedebut = datedebut;
    }

    @Override
    public LocalDate getDatefin() {
        return datefin;
    }

    @Override
    public void setDatefin(LocalDate datefin) {
        this.datefin = datefin;
    }

    @Override
    public String getSujet() {
        return sujet;
    }

    @Override
    public void setSujet(String sujet) {
        this.sujet = sujet;
    }

    @Override
    public String getService() {
        return service;
    }

    @Override
    public void setService(String service) {
        this.service = service;
    }

    @Override
    public String getDetails() {
        return details;
    }

    @Override
    public void setDetails(String details) {
        this.details = details;
    }

    @Override
    public Integer getJours() {
        return jours;
    }

    @Override
    public void setJours(Integer jours) {
        this.jours = jours;
    }

    @Override
    public Integer getHeures() {
        return heures;
    }

    @Override
    public void setHeures(Integer heures) {
        this.heures = heures;
    }

    @Override
    public Integer getVersement() {
        return versement;
    }

    @Override
    public void setVersement(Integer versement) {
        this.versement = versement;
    }

    @Override
    public EtudiantIHM getEtudiant() {
        return etudiant;
    }

    public void setEtudiant(EtudiantIHM etudiant) {
        this.etudiant = etudiant;
    }

    @Override
    public Enseignant getReferent() {
        return referent;
    }

    @Override
    public void setReferent(Enseignant referent) {
        this.referent = referent;
    }

    @Override
    public EntrepriseIHM getEntreprise() {
        return entreprise;
    }

    public void setEntreprise(EntrepriseIHM entreprise) {
        this.entreprise = entreprise;
    }

    @Override
    public Contact getEncadrant() {
        return encadrant;
    }

    @Override
    public void setEncadrant(Contact encadrant) {
        this.encadrant = encadrant;
    }

    @Override
    public Contact getResponsable() {
        return responsable;
    }

    @Override
    public void setResponsable(Contact responsable) {
        this.responsable = responsable;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        StageIHM stage = (StageIHM) o;
        if (stage.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, stage.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Stage{" +
            "id=" + id +
            ", datedebut='" + datedebut + "'" +
            ", datefin='" + datefin + "'" +
            ", sujet='" + sujet + "'" +
            ", service='" + service + "'" +
            ", details='" + details + "'" +
            ", jours='" + jours + "'" +
            ", heures='" + heures + "'" +
            ", versement='" + versement + "'" +
            '}';
    }

    public static StageIHM create(Stage stage){
        StageIHM res = new StageIHM();
        res.setId(stage.getId());
        res.setSujet(stage.getSujet());
        res.setDatedebut(stage.getDatedebut());
        res.setDatefin(stage.getDatefin());
        res.setService(stage.getService());
        res.setDetails(stage.getDetails());
        res.setJours(stage.getJours());
        res.setHeures(stage.getHeures());
        res.setVersement(stage.getVersement());
        res.setReferent(stage.getReferent());
        res.setResponsable(stage.getResponsable());
        res.setEncadrant(stage.getEncadrant());

        return res;
    }

    public Stage createStage(){
        Stage stage = new Stage();
        stage.setId(id);
        stage.setSujet(sujet);
        stage.setDatedebut(datedebut);
        stage.setDatefin(datefin);
        stage.setService(service);
        stage.setDetails(details);
        stage.setJours(jours);
        stage.setHeures(heures);
        stage.setVersement(versement);
        stage.setReferent(referent);
        stage.setResponsable(responsable);
        stage.setEncadrant(encadrant);
        stage.setEtudiant(etudiant.createEtudiant());
        stage.setEntreprise(entreprise.createEntreprise());
        return stage;
    }

}
