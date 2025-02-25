package services.module6;

import enums.EtatEquipement;
import enums.TypeEquipement;
import models.module6.Equipement;
import services.BaseService;
import services.IService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceEquipement extends BaseService implements IService<Equipement> {
    @Override
    public void add(Equipement equipement) throws SQLException {
        //nom	description	etat	typeEquipement	image_url	quantite	installationSportive_id

        String sql = "INSERT INTO equipement(nom, description, etat, typeEquipement, image_url,quantite , installationSportive_id)VALUES(?,?,?,?,?,?,?)";
        PreparedStatement stmt = con.prepareStatement(sql);

        stmt.setString(1, equipement.getNom());
        stmt.setString(2, equipement.getDescription());
        stmt.setString(3, equipement.getEtat().toString());
        stmt.setString(4, equipement.getTypeEquipement().toString());
        stmt.setString(5, equipement.getImage_url());
        stmt.setInt(6, equipement.getQuantite());
        stmt.setString(7, equipement.getInstallationSportive().toString());

        stmt.executeUpdate();
        System.out.println("Equipement " + equipement.getNom() + " ajoute");
    }

    @Override
    public void edit(Equipement equipement) throws SQLException {

    }

    @Override
    public void delete(Equipement equipement) throws SQLException {
        String sql = "DELETE FROM equipement";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.executeUpdate();
        System.out.println("Equipement " + equipement.getNom() + " supprimer");
    }

    @Override
    public Equipement get(Equipement equipement) throws SQLException {
        return get(equipement.getId());
    }

    @Override
    public Equipement get(int id) throws SQLException {
        String sql = "SELECT * FROM equipement WHERE id = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            Equipement equipement = new Equipement();
            equipement.setId(rs.getInt("id"));
            equipement.setNom(rs.getString("nom"));
            equipement.setDescription(rs.getString("description"));
            equipement.setEtat(EtatEquipement.valueOf(rs.getString("etat")));
            equipement.setTypeEquipement(TypeEquipement.valueOf(rs.getString("typeEquipement")));
            equipement.setImage_url(rs.getString("image_url"));
            equipement.setQuantite(rs.getInt("quantite"));
            equipement.setInstallationSportive(new ServiceInstallationSportive().get(rs.getInt("installationSportive_id")));

            return equipement;

        }
        return null;
    }

    @Override
    public List<Equipement> getAll() throws SQLException {
        List<Equipement> equipements = new ArrayList<>();
        String sql = "SELECT * FROM equipement";
        PreparedStatement stmt = con.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Equipement equipement = new Equipement();
            equipement.setId(rs.getInt("id"));
            equipement.setNom(rs.getString("nom"));
            equipement.setDescription(rs.getString("description"));
            equipement.setEtat(EtatEquipement.valueOf(rs.getString("etat")));
            equipement.setTypeEquipement(TypeEquipement.valueOf(rs.getString("typeEquipement")));
            equipement.setImage_url(rs.getString("image_url"));
            equipement.setQuantite(rs.getInt("quantite"));
            equipement.setInstallationSportive(new ServiceInstallationSportive().get(rs.getInt("installationSportive_id")));

            equipements.add(equipement);
        }
        return equipements;
    }

    public List<Equipement> getEquipementsByInstallationSportiveId(int installationSportive_id) throws SQLException {
        List<Equipement> equipements = new ArrayList<>();
        String sql = "SELECT * FROM equipement where installationSportive_id = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, installationSportive_id);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Equipement equipement = new Equipement();
            equipement.setId(rs.getInt("id"));
            equipement.setNom(rs.getString("nom"));
            equipement.setDescription(rs.getString("description"));
            equipement.setEtat(EtatEquipement.valueOf(rs.getString("etat")));
            equipement.setTypeEquipement(TypeEquipement.valueOf(rs.getString("typeEquipement")));
            equipement.setImage_url(rs.getString("image_url"));
            equipement.setQuantite(rs.getInt("quantite"));
            equipement.setInstallationSportive(new ServiceInstallationSportive().get(rs.getInt("installationSportive_id")));

            equipements.add(equipement);

        }
        return equipements;
    }
}
