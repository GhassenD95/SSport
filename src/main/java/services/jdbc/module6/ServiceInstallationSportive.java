package services.module6;

import enums.TypeInstallation;
import models.module6.InstallationSportive;
import services.BaseService;
import services.IService;
import services.module1.ServiceUtilisateur;
import services.module2.ServiceEntrainment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceInstallationSportive extends BaseService implements IService<InstallationSportive> {
    @Override
    public void add(InstallationSportive installationSportive) throws SQLException {
        //nom	typeInstallation	manager_id	adresse	capacite	isDisponible	image_url
        String sql = "INSERT INTO installationsportive(nom, typeInstallation, manager_id, adresse, capacite, isDisponible, image_url) VALUES(?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, installationSportive.getNom());
        stmt.setString(2, installationSportive.getTypeInstallation().name());
        stmt.setInt(3, installationSportive.getManager().getId());
        stmt.setString(4, installationSportive.getAdresse());
        stmt.setInt(5, installationSportive.getCapacite());
        stmt.setBoolean(6, installationSportive.isDisponible());
        stmt.setString(7, installationSportive.getImage_url());
        stmt.executeUpdate();

        System.out.println("ServiceInstallationSportive ajoute");
    }

    @Override
    public void edit(InstallationSportive installationSportive) throws SQLException {
        String sql = "";
    }

    @Override
    public void delete(InstallationSportive installationSportive) throws SQLException {
        String sql = "DELETE FROM installationsportive WHERE id = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, installationSportive.getId());
        stmt.executeUpdate();

        System.out.println("ServiceInstallationSportive supprime");
    }

    @Override
    public InstallationSportive get(InstallationSportive installationSportive) throws SQLException {
        return get(installationSportive.getId());
    }

    @Override
    public InstallationSportive get(int id) throws SQLException {
        String sql = "select * from installationsportive where id = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {

            InstallationSportive returnedInstallationSportive = new InstallationSportive();
            returnedInstallationSportive.setId(rs.getInt("id"));
            returnedInstallationSportive.setNom(rs.getString("nom"));
            returnedInstallationSportive.setAdresse(rs.getString("adresse"));
            returnedInstallationSportive.setDisponible(rs.getBoolean("isDisponible"));
            returnedInstallationSportive.setCapacite(rs.getInt("capacite"));
            returnedInstallationSportive.setImage_url(rs.getString("image_url"));
            returnedInstallationSportive.setTypeInstallation(TypeInstallation.valueOf(rs.getString("typeInstallation")));
            returnedInstallationSportive.setManager(new ServiceUtilisateur().get(rs.getInt("manager_id")));

            return returnedInstallationSportive;

        }
        return null;
    }

    @Override
    public List<InstallationSportive> getAll() throws SQLException {
        List<InstallationSportive> installationSportives = new ArrayList<>();

        String sql = "SELECT * FROM installationsportive";
        PreparedStatement stmt = con.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            InstallationSportive returnedInstallationSportive = new InstallationSportive();
            returnedInstallationSportive.setId(rs.getInt("id"));
            returnedInstallationSportive.setNom(rs.getString("nom"));
            returnedInstallationSportive.setAdresse(rs.getString("adresse"));
            returnedInstallationSportive.setDisponible(rs.getBoolean("isDisponible"));
            returnedInstallationSportive.setCapacite(rs.getInt("capacite"));
            returnedInstallationSportive.setImage_url(rs.getString("image_url"));
            returnedInstallationSportive.setTypeInstallation(TypeInstallation.valueOf(rs.getString("typeInstallation")));
            returnedInstallationSportive.setManager(new ServiceUtilisateur().get(rs.getInt("manager_id")));

            returnedInstallationSportive.setEntrainments(new ServiceEntrainment().getEntrainmentsParInstallationId(rs.getInt("id")));

            installationSportives.add(returnedInstallationSportive);

        }
        return installationSportives;
    }

    public List<InstallationSportive> getInstallationSportivesByManagerId(int manager_id) throws SQLException {
        List<InstallationSportive> installationSportives = new ArrayList<>();
        String sql = "SELECT * FROM installationsportive WHERE manager_id = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, manager_id);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            InstallationSportive returnedInstallationSportive = new InstallationSportive();
            returnedInstallationSportive.setId(rs.getInt("id"));
            returnedInstallationSportive.setNom(rs.getString("nom"));
            returnedInstallationSportive.setAdresse(rs.getString("adresse"));
            returnedInstallationSportive.setDisponible(rs.getBoolean("isDisponible"));
            returnedInstallationSportive.setCapacite(rs.getInt("capacite"));
            returnedInstallationSportive.setImage_url(rs.getString("image_url"));
            returnedInstallationSportive.setTypeInstallation(TypeInstallation.valueOf(rs.getString("typeInstallation")));
            returnedInstallationSportive.setManager(new ServiceUtilisateur().get(rs.getInt("manager_id")));

            returnedInstallationSportive.setEntrainments(new ServiceEntrainment().getEntrainmentsParInstallationId(rs.getInt("id")));

            installationSportives.add(returnedInstallationSportive);

        }
        return installationSportives;
    }
}
