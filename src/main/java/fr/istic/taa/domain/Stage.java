package fr.istic.taa.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Stage.
 */
@Entity
@Table(name = "stage")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "stage")
public class Stage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "datedebut")
    private LocalDate datedebut;

    @Column(name = "datefin")
    private LocalDate datefin;

    @Column(name = "sujet")
    private String sujet;

    @Column(name = "service")
    private String service;

    @Column(name = "details")
    private String details;

    @Column(name = "jours")
    private Integer jours;

    @Column(name = "heures")
    private Integer heures;

    @Column(name = "versement")
    private Integer versement;

    @ManyToOne
    private Etudiant etudiant;

    @ManyToOne
    private Enseignant referent;

    @ManyToOne
    private Entreprise entreprise;

    @ManyToOne
    private Contact encadrant;

    @ManyToOne
    private Contact responsable;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatedebut() {
        return datedebut;
    }

    public void setDatedebut(LocalDate datedebut) {
        this.datedebut = datedebut;
    }

    public LocalDate getDatefin() {
        return datefin;
    }

    public void setDatefin(LocalDate datefin) {
        this.datefin = datefin;
    }

    public String getSujet() {
        return sujet;
    }

    public void setSujet(String sujet) {
        this.sujet = sujet;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Integer getJours() {
        return jours;
    }

    public void setJours(Integer jours) {
        this.jours = jours;
    }

    public Integer getHeures() {
        return heures;
    }

    public void setHeures(Integer heures) {
        this.heures = heures;
    }

    public Integer getVersement() {
        return versement;
    }

    public void setVersement(Integer versement) {
        this.versement = versement;
    }

    public Etudiant getEtudiant() {
        return etudiant;
    }

    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }

    public Enseignant getReferent() {
        return referent;
    }

    public void setReferent(Enseignant enseignant) {
        this.referent = enseignant;
    }

    public Entreprise getEntreprise() {
        return entreprise;
    }

    public void setEntreprise(Entreprise entreprise) {
        this.entreprise = entreprise;
    }

    public Contact getEncadrant() {
        return encadrant;
    }

    public void setEncadrant(Contact contact) {
        this.encadrant = contact;
    }

    public Contact getResponsable() {
        return responsable;
    }

    public void setResponsable(Contact contact) {
        this.responsable = contact;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Stage stage = (Stage) o;
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
}
