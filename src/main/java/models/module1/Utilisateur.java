package models.module1;

import enums.Role;
import enums.Status;
import models.module3.Blessure;
import models.module3.DossierMedical;
import models.module5.PerformanceAthlete;
import models.module6.InstallationSportive;

import java.util.ArrayList;
import java.util.List;

public class Utilisateur {
    private int id;
    private String nom;
    private String prenom;
    private Role role;
    private String email;
    private String hashedPassword;
    private String adresse;
    private String telephone;
    private Status status;
    private String image_url;
    private Equipe equipe;


    private List<Blessure> blessures;
    private List<PerformanceAthlete> performances;
    private List<DossierMedical> dossierMedicals;
    private List<InstallationSportive> installationSportives;
    private List<Equipe> equipesEntrainees;


    public Utilisateur(int id, String nom, String prenom, Role role, String email, Equipe equipe, String image_url, Status status, String telephone, String adresse, String hashedPassword) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.role = role;
        this.email = email;
        this.equipe = equipe;
        this.image_url = image_url;
        this.status = status;
        this.telephone = telephone;
        this.adresse = adresse;
        this.hashedPassword = hashedPassword;
        this.blessures = new ArrayList<>();
        this.performances = new ArrayList<>();
        this.dossierMedicals = new ArrayList<>();
        this.installationSportives = new ArrayList<>();
        this.equipesEntrainees = new ArrayList<>();
    }

    public Utilisateur(String nom, String prenom, Role role, String email, String hashedPassword, String adresse, String telephone, Status status, String image_url) {
        this.nom = nom;
        this.prenom = prenom;
        this.role = role;
        this.email = email;
        this.hashedPassword = hashedPassword;
        this.adresse = adresse;
        this.telephone = telephone;
        this.status = status;
        this.image_url = image_url;
        this.blessures = new ArrayList<>();
        this.performances = new ArrayList<>();
        this.dossierMedicals = new ArrayList<>();
        this.installationSportives = new ArrayList<>();
        this.equipesEntrainees = new ArrayList<>();
    }

    public Utilisateur(String nom, String prenom, Role role, String email, String hashedPassword, String adresse, String telephone, Status status, String image_url, Equipe equipe) {
        this.nom = nom;
        this.prenom = prenom;
        this.role = role;
        this.email = email;
        this.hashedPassword = hashedPassword;
        this.adresse = adresse;
        this.telephone = telephone;
        this.status = status;
        this.image_url = image_url;
        this.equipe = equipe; //nullable, pour le ROLE coach et athlete seulement
        this.blessures = new ArrayList<>();
        this.performances = new ArrayList<>();
        this.dossierMedicals = new ArrayList<>();
        this.installationSportives = new ArrayList<>();
        this.equipesEntrainees = new ArrayList<>();
    }

    public Utilisateur() {
        this.blessures = new ArrayList<>();
        this.performances = new ArrayList<>();
        this.dossierMedicals = new ArrayList<>();
        this.installationSportives = new ArrayList<>();
        this.equipesEntrainees = new ArrayList<>();
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

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public Equipe getEquipe() {
        return equipe;
    }

    public void setEquipe(Equipe equipe) {
        this.equipe = equipe;
    }

    public List<Blessure> getBlessures() {
        return blessures;
    }

    public void setBlessures(List<Blessure> blessures) {
        this.blessures = blessures;
    }

    public List<PerformanceAthlete> getPerformances() {
        return performances;
    }

    public void setPerformances(List<PerformanceAthlete> performances) {
        this.performances = performances;
    }

    public List<DossierMedical> getDossierMedicals() {
        return dossierMedicals;
    }

    public void setDossierMedicals(List<DossierMedical> dossierMedicals) {
        this.dossierMedicals = dossierMedicals;
    }

    public List<InstallationSportive> getInstallationSportives() {
        return installationSportives;
    }

    public List<Equipe> getEquipesEntrainees() {
        return equipesEntrainees;
    }

    public void setEquipesEntrainees(List<Equipe> equipesEntrainees) {
        this.equipesEntrainees = equipesEntrainees;
    }

    public void setInstallationSportives(List<InstallationSportive> installationSportives) {
        this.installationSportives = installationSportives;
    }


    @Override
    public String toString() {
        return "Utilisateur{" +
                "nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", role=" + role +
                ", email='" + email + '\'' +
                ", hashedPassword='" + hashedPassword + '\'' +
                ", adresse='" + adresse + '\'' +
                ", telephone='" + telephone + '\'' +
                ", status=" + status +
                ", image_url='" + image_url + '\'' +
                ", equipe=" + equipe +
                ", id=" + id +
                '}';
    }
}
