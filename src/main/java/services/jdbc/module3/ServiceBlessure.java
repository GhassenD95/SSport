package services.module3;

import models.module3.Blessure;
import services.BaseService;
import services.IService;
import services.module1.ServiceUtilisateur;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceBlessure extends BaseService implements IService<Blessure> {
    @Override
    public void add(Blessure blessure) throws SQLException {
        //athlete_id	typeBlessure	description	dateBlessure	dateReprise
        String sql = "INSERT into blessure(athlete_id, typeBlessure, description, dateBlessure, dateReprise) VALUES(?,?,?,?,?)";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, blessure.getAthlete().getId());
        stmt.setString(2, blessure.getTypeBlessure().name());
        stmt.setString(3, blessure.getDescription());
        stmt.setObject(4, blessure.getDateBlessure());
        stmt.setObject(5, blessure.getDateReprise());
        stmt.executeUpdate();
        System.out.println("Blessure ajoute");
    }

    @Override
    public void edit(Blessure blessure) throws SQLException {

    }

    @Override
    public void delete(Blessure blessure) throws SQLException {
        String sql = "DELETE FROM blessure WHERE id = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, blessure.getId());
        stmt.executeUpdate();
        System.out.println("Blessure supprime");
    }

    @Override
    public Blessure get(Blessure blessure) throws SQLException {
        return get(blessure.getId());
    }

    @Override
    public Blessure get(int id) throws SQLException {
        String sql = "SELECT * FROM blessure WHERE id = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            Blessure blessure = new Blessure();
            blessure.setId(rs.getInt("id"));
            blessure.setDescription(rs.getString("description"));
            blessure.setId(rs.getInt("id"));
            blessure.setAthlete(new ServiceUtilisateur().get(rs.getInt("athlete_id")));
            blessure.setDateBlessure(rs.getTimestamp("dateBlessure").toLocalDateTime());
            blessure.setDateReprise(rs.getTimestamp("dateReprise").toLocalDateTime());
            return blessure;
        }
        return null;
    }

    @Override
    public List<Blessure> getAll() throws SQLException {
        List<Blessure> blessures = new ArrayList<>();

        String sql = "SELECT * FROM blessure";
        PreparedStatement stmt = con.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Blessure blessure = new Blessure();
            blessure.setId(rs.getInt("id"));
            blessure.setDescription(rs.getString("description"));
            blessure.setId(rs.getInt("id"));
            blessure.setAthlete(new ServiceUtilisateur().get(rs.getInt("athlete_id")));
            blessure.setDateBlessure(rs.getTimestamp("dateBlessure").toLocalDateTime());
            blessure.setDateReprise(rs.getTimestamp("dateReprise").toLocalDateTime());
            blessures.add(blessure);
        }
        return blessures;
    }

    public List<Blessure> getBlessuresByAthleteId(int athlete_id) throws SQLException {
        List<Blessure> blessures = new ArrayList<>();
        String sql = "SELECT * FROM blessure WHERE athlete_id = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, athlete_id);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Blessure blessure = new Blessure();
            blessure.setId(rs.getInt("id"));
            blessure.setDescription(rs.getString("description"));
            blessure.setId(rs.getInt("id"));
            blessure.setAthlete(new ServiceUtilisateur().get(rs.getInt("athlete_id")));
            blessure.setDateBlessure(rs.getTimestamp("dateBlessure").toLocalDateTime());
            blessure.setDateReprise(rs.getTimestamp("dateReprise").toLocalDateTime());
            blessures.add(blessure);

        }
        return blessures;
    }
}
