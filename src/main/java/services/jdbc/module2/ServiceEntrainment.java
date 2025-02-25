package services.module2;

import models.module2.Entrainment;
import services.BaseService;
import services.IService;
import services.module1.ServiceEquipe;
import services.module6.ServiceInstallationSportive;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceEntrainment extends BaseService implements IService<Entrainment>  {
    @Override
    public void add(Entrainment entrainment) throws SQLException {
        //id	nom	equipe_id	installationSportive_id	description	dateDebut	dateFin
        String sql = "INSERT INTO entrainment(nom, equipe_id, installationSportive_id, description, dateDebut, dateFin) VALUES(?,?,?,?,?,?)";

        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, entrainment.getNom());
        stmt.setInt(2, entrainment.getEquipe().getId());
        stmt.setInt(3, entrainment.getInstallationSportive().getId());
        stmt.setString(4, entrainment.getDescription());
        stmt.setObject(5, entrainment.getDateDebut());
        stmt.setObject(6, entrainment.getDateFin());

        stmt.executeUpdate();
        System.out.println("Entrainment " + entrainment.getNom() + " ajoute.");

    }

    @Override
    public void edit(Entrainment entrainment) throws SQLException {
        String sql = "UPDATE entrainment SET nom = ?, equipe_id = ?, installationSportive_id = ?, description = ?, dateDebut = ?, dateFin = ? WHERE id = ?";

        // Prepare the statement and set the parameters
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, entrainment.getNom());
        stmt.setInt(2, entrainment.getEquipe().getId());
        stmt.setInt(3, entrainment.getInstallationSportive().getId());
        stmt.setString(4, entrainment.getDescription());

        // Use setTimestamp to update the date fields
        stmt.setTimestamp(5, java.sql.Timestamp.valueOf(entrainment.getDateDebut()));
        stmt.setTimestamp(6, java.sql.Timestamp.valueOf(entrainment.getDateFin()));

        // Set the id of the entrainment to update
        stmt.setInt(7, entrainment.getId());
        stmt.executeUpdate();

        System.out.println("Entrainment " + entrainment.getNom() + " modifie.");

    }

    @Override
    public void delete(Entrainment entrainment) throws SQLException {
        String sql = "DELETE from entrainment where id = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, entrainment.getId());
        stmt.executeUpdate();
        System.out.println("Entrainment " + entrainment.getNom() + " supprime.");

    }

    @Override
    public Entrainment get(Entrainment entrainment) throws SQLException {
        return get(entrainment.getId());
    }

    @Override
    public Entrainment get(int id) throws SQLException {
        String sql = "SELECT * FROM entrainment WHERE id = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            Entrainment returnedEntrainment = new Entrainment();
            returnedEntrainment.setId(rs.getInt("id"));
            returnedEntrainment.setNom(rs.getString("nom"));
            returnedEntrainment.setDescription(rs.getString("description"));
            returnedEntrainment.setDateDebut(rs.getTimestamp("dateDebut").toLocalDateTime());
            returnedEntrainment.setDateFin(rs.getTimestamp("dateFin").toLocalDateTime());
            returnedEntrainment.setEquipe(new ServiceEquipe().get(rs.getInt("equipe_id")));
            returnedEntrainment.setInstallationSportive(new ServiceInstallationSportive().get(rs.getInt("installationSportive_id")));

            return returnedEntrainment;
        }
        return null;
    }

    @Override
    public List<Entrainment> getAll() throws SQLException {
        List<Entrainment> returnedEntrainments = new ArrayList<>();

        String sql = "SELECT * FROM entrainment";
        PreparedStatement stmt = con.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Entrainment returnedEntrainment = new Entrainment();
            returnedEntrainment.setId(rs.getInt("id"));
            returnedEntrainment.setNom(rs.getString("nom"));
            returnedEntrainment.setDescription(rs.getString("description"));
            returnedEntrainment.setDateDebut(rs.getTimestamp("dateDebut").toLocalDateTime());
            returnedEntrainment.setDateFin(rs.getTimestamp("dateFin").toLocalDateTime());
            returnedEntrainment.setEquipe(new ServiceEquipe().get(rs.getInt("equipe_id")));
            returnedEntrainment.setInstallationSportive(new ServiceInstallationSportive().get(rs.getInt("installationSportive_id")));

            returnedEntrainments.add(returnedEntrainment);
        }
        return returnedEntrainments;
    }

    public List<Entrainment> getEntrainmentsParEquipeId(int equipe_id) throws SQLException {
        List<Entrainment> returnedEntrainments = new ArrayList<>();
        String sql = "SELECT * FROM entrainment WHERE equipe_id = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, equipe_id);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Entrainment returnedEntrainment = new Entrainment();
            returnedEntrainment.setId(rs.getInt("id"));
            returnedEntrainment.setNom(rs.getString("nom"));
            returnedEntrainment.setDescription(rs.getString("description"));
            returnedEntrainment.setDateDebut(rs.getTimestamp("dateDebut").toLocalDateTime());
            returnedEntrainment.setDateFin(rs.getTimestamp("dateFin").toLocalDateTime());
            returnedEntrainment.setEquipe(new ServiceEquipe().get(rs.getInt("equipe_id")));
            returnedEntrainment.setInstallationSportive(new ServiceInstallationSportive().get(rs.getInt("installationSportive_id")));

            returnedEntrainments.add(returnedEntrainment);
        }
        return returnedEntrainments;

    }

    public List<Entrainment> getEntrainmentsParInstallationId(int installationSportive_id) throws SQLException {
        List<Entrainment> returnedEntrainments = new ArrayList<>();
        String sql = "SELECT * FROM entrainment WHERE installationSportive_id = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, installationSportive_id);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Entrainment returnedEntrainment = new Entrainment();
            returnedEntrainment.setId(rs.getInt("id"));
            returnedEntrainment.setNom(rs.getString("nom"));
            returnedEntrainment.setDescription(rs.getString("description"));
            returnedEntrainment.setDateDebut(rs.getTimestamp("dateDebut").toLocalDateTime());
            returnedEntrainment.setDateFin(rs.getTimestamp("dateFin").toLocalDateTime());
            returnedEntrainment.setEquipe(new ServiceEquipe().get(rs.getInt("equipe_id")));
            returnedEntrainment.setInstallationSportive(new ServiceInstallationSportive().get(rs.getInt("installationSportive_id")));

            returnedEntrainments.add(returnedEntrainment);
        }
        return returnedEntrainments;

    }

    public List<Entrainment> getEntrainmentsByEquipeId(int equipe_id) throws SQLException {
        String sql = "SELECT * FROM entrainment WHERE equipe_id = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, equipe_id);
        ResultSet rs = stmt.executeQuery();
        List<Entrainment> returnedEntrainments = new ArrayList<>();
        while (rs.next()) {
            Entrainment returnedEntrainment = new Entrainment();
            returnedEntrainment.setId(rs.getInt("id"));
            returnedEntrainment.setNom(rs.getString("nom"));
            returnedEntrainment.setDescription(rs.getString("description"));
            returnedEntrainment.setDateDebut(rs.getTimestamp("dateDebut").toLocalDateTime());
            returnedEntrainment.setDateFin(rs.getTimestamp("dateFin").toLocalDateTime());
            returnedEntrainment.setEquipe(new ServiceEquipe().get(rs.getInt("equipe_id")));
            returnedEntrainment.setInstallationSportive(new ServiceInstallationSportive().get(rs.getInt("installationSportive_id")));

            returnedEntrainments.add(returnedEntrainment);
        }
        return returnedEntrainments;
    }

    public List<Entrainment> getEntrainmentsByInstallationSportiveId(int installationSportive_id) throws SQLException {
        List<Entrainment> returnedEntrainments = new ArrayList<>();
        String sql = "SELECT * FROM entrainment WHERE installationSportive_id = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, installationSportive_id);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Entrainment returnedEntrainment = new Entrainment();
            returnedEntrainment.setId(rs.getInt("id"));
            returnedEntrainment.setNom(rs.getString("nom"));
            returnedEntrainment.setDescription(rs.getString("description"));
            returnedEntrainment.setDateDebut(rs.getTimestamp("dateDebut").toLocalDateTime());
            returnedEntrainment.setDateFin(rs.getTimestamp("dateFin").toLocalDateTime());
            returnedEntrainment.setEquipe(new ServiceEquipe().get(rs.getInt("equipe_id")));
            returnedEntrainment.setInstallationSportive(new ServiceInstallationSportive().get(rs.getInt("installationSportive_id")));

            returnedEntrainments.add(returnedEntrainment);

        }
        return returnedEntrainments;
    }
}
