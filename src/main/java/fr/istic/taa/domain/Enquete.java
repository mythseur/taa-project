package fr.istic.taa.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Enquete.
 */
@Entity
@Table(name = "enquete")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "enquete")
public class Enquete implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "situation")
    private String situation;

    @Column(name = "mode_obtention")
    private String modeObtention;

    @Column(name = "mode_enquete")
    private String modeEnquete;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "nom_usuel")
    private String nomUsuel;

    @Column(name = "rue")
    private String rue;

    @Column(name = "complement")
    private String complement;

    @Column(name = "ville")
    private String ville;

    @Column(name = "codepostal")
    private String codepostal;

    @Column(name = "datedebut")
    private LocalDate datedebut;

    @Column(name = "duree_recherche")
    private Integer dureeRecherche;

    @Column(name = "salaire")
    private Integer salaire;

    @Column(name = "salaire_fixe")
    private Integer salaireFixe;

    @Column(name = "salaire_variable")
    private Integer salaireVariable;

    @Column(name = "pourcentage")
    private Integer pourcentage;

    @Column(name = "avantage")
    private Integer avantage;

    @Column(name = "devise")
    private String devise;

    @ManyToOne
    private Etudiant etudiant;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSituation() {
        return situation;
    }

    public void setSituation(String situation) {
        this.situation = situation;
    }

    public String getModeObtention() {
        return modeObtention;
    }

    public void setModeObtention(String modeObtention) {
        this.modeObtention = modeObtention;
    }

    public String getModeEnquete() {
        return modeEnquete;
    }

    public void setModeEnquete(String modeEnquete) {
        this.modeEnquete = modeEnquete;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getNomUsuel() {
        return nomUsuel;
    }

    public void setNomUsuel(String nomUsuel) {
        this.nomUsuel = nomUsuel;
    }

    public String getRue() {
        return rue;
    }

    public void setRue(String rue) {
        this.rue = rue;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
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

    public LocalDate getDatedebut() {
        return datedebut;
    }

    public void setDatedebut(LocalDate datedebut) {
        this.datedebut = datedebut;
    }

    public Integer getDureeRecherche() {
        return dureeRecherche;
    }

    public void setDureeRecherche(Integer dureeRecherche) {
        this.dureeRecherche = dureeRecherche;
    }

    public Integer getSalaire() {
        return salaire;
    }

    public void setSalaire(Integer salaire) {
        this.salaire = salaire;
    }

    public Integer getSalaireFixe() {
        return salaireFixe;
    }

    public void setSalaireFixe(Integer salaireFixe) {
        this.salaireFixe = salaireFixe;
    }

    public Integer getSalaireVariable() {
        return salaireVariable;
    }

    public void setSalaireVariable(Integer salaireVariable) {
        this.salaireVariable = salaireVariable;
    }

    public Integer getPourcentage() {
        return pourcentage;
    }

    public void setPourcentage(Integer pourcentage) {
        this.pourcentage = pourcentage;
    }

    public Integer getAvantage() {
        return avantage;
    }

    public void setAvantage(Integer avantage) {
        this.avantage = avantage;
    }

    public String getDevise() {
        return devise;
    }

    public void setDevise(String devise) {
        this.devise = devise;
    }

    public Etudiant getEtudiant() {
        return etudiant;
    }

    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Enquete enquete = (Enquete) o;
        if (enquete.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, enquete.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Enquete{" +
            "id=" + id +
            ", situation='" + situation + "'" +
            ", modeObtention='" + modeObtention + "'" +
            ", modeEnquete='" + modeEnquete + "'" +
            ", date='" + date + "'" +
            ", nomUsuel='" + nomUsuel + "'" +
            ", rue='" + rue + "'" +
            ", complement='" + complement + "'" +
            ", ville='" + ville + "'" +
            ", codepostal='" + codepostal + "'" +
            ", datedebut='" + datedebut + "'" +
            ", dureeRecherche='" + dureeRecherche + "'" +
            ", salaire='" + salaire + "'" +
            ", salaireFixe='" + salaireFixe + "'" +
            ", salaireVariable='" + salaireVariable + "'" +
            ", pourcentage='" + pourcentage + "'" +
            ", avantage='" + avantage + "'" +
            ", devise='" + devise + "'" +
            '}';
    }
}
