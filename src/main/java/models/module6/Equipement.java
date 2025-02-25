package models.module6;

import enums.EtatEquipement;
import enums.TypeEquipement;

public class Equipement {
    private int id;
    private String nom;
    private String description;
    private EtatEquipement etat;
    private TypeEquipement typeEquipement;
    private String image_url;
    private int quantite;
    private InstallationSportive installationSportive;

    public Equipement(String nom, String description, EtatEquipement etat, TypeEquipement typeEquipement, String image_url, int quantite, InstallationSportive installationSportive) {
        this.nom = nom;
        this.description = description;
        this.etat = etat;
        this.typeEquipement = typeEquipement;
        this.image_url = image_url;
        this.quantite = quantite;
        this.installationSportive = installationSportive;
    }

    public Equipement(int id, String nom, String description, EtatEquipement etat, TypeEquipement typeEquipement, String image_url, int quantite, InstallationSportive installationSportive) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.etat = etat;
        this.typeEquipement = typeEquipement;
        this.image_url = image_url;
        this.quantite = quantite;
        this.installationSportive = installationSportive;
    }

    public Equipement() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public EtatEquipement getEtat() {
        return etat;
    }

    public void setEtat(EtatEquipement etat) {
        this.etat = etat;
    }

    public TypeEquipement getTypeEquipement() {
        return typeEquipement;
    }

    public void setTypeEquipement(TypeEquipement typeEquipement) {
        this.typeEquipement = typeEquipement;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public InstallationSportive getInstallationSportive() {
        return installationSportive;
    }

    public void setInstallationSportive(InstallationSportive installationSportive) {
        this.installationSportive = installationSportive;
    }
}
