package services.module3;

import enums.EtatAthlete;
import models.module3.DossierMedical;
import services.BaseService;
import services.IService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceDossierMedical extends BaseService implements IService<DossierMedical> {
    @Override
    public void add(DossierMedical dossierMedical) throws SQLException {
        //	athlete_id	dernierCheckup	allergies	vaccinations	etatAthlete	description
        String sql = "INSERT INTO dossiermedical(athlete_id, dernierCheckup, allergies, vaccinations, etatAthlete, description) VALUES(?,?,?,?,?,?)";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, dossierMedical.getAthlete().getId());
        stmt.setObject(2, dossierMedical.getDernierCheckup());
        stmt.setString(3, dossierMedical.getAllergies());
        stmt.setString(4, dossierMedical.getVaccinations());
        stmt.setString(5, dossierMedical.getEtatAthlete().name());
        stmt.setString(6, dossierMedical.getDescription());
        stmt.executeUpdate();

        System.out.println("DossierMedical " + dossierMedical.getId() + " ajoute");
    }

    @Override
    public void edit(DossierMedical dossierMedical) throws SQLException {

    }

    @Override
    public void delete(DossierMedical dossierMedical) throws SQLException {
        String sql = "DELETE FROM dossiermedical WHERE id = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, dossierMedical.getId());
        stmt.executeUpdate();
        System.out.println("DossierMedical " + dossierMedical.getId() + " supprimer");
    }

    @Override
    public DossierMedical get(DossierMedical dossierMedical) throws SQLException {
        return get(dossierMedical.getId());
    }

    @Override
    public DossierMedical get(int id) throws SQLException {
        String sql = "SELECT * FROM dossiermedical WHERE id = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            DossierMedical dossierMedical = new DossierMedical();
            dossierMedical.setId(rs.getInt("id"));
            dossierMedical.setAllergies(rs.getString("allergies"));
            dossierMedical.setVaccinations(rs.getString("vaccinations"));
            dossierMedical.setDescription(rs.getString("description"));
            dossierMedical.setEtatAthlete(EtatAthlete.valueOf(rs.getString("etatAthlete")));
            dossierMedical.setDernierCheckup(rs.getTimestamp("dernierCheckup").toLocalDateTime());

            return dossierMedical;
        }
        return null;
    }

    @Override
    public List<DossierMedical> getAll() throws SQLException {
        List<DossierMedical> dossierMedicals = new ArrayList<DossierMedical>();
        String sql = "SELECT * FROM dossiermedical";
        PreparedStatement stmt = con.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            DossierMedical dossierMedical = new DossierMedical();
            dossierMedical.setId(rs.getInt("id"));
            dossierMedical.setAllergies(rs.getString("allergies"));
            dossierMedical.setVaccinations(rs.getString("vaccinations"));
            dossierMedical.setDescription(rs.getString("description"));
            dossierMedical.setEtatAthlete(EtatAthlete.valueOf(rs.getString("etatAthlete")));
            dossierMedical.setDernierCheckup(rs.getTimestamp("dernierCheckup").toLocalDateTime());

            dossierMedicals.add(dossierMedical);
        }
        return dossierMedicals;
    }

    public List<DossierMedical> getDossierMedicalsByAthleteId(int athlete_id) throws SQLException {
        String sql = "SELECT * FROM dossiermedical WHERE athlete_id = ?";
        List<DossierMedical> dossierMedicals = new ArrayList<DossierMedical>();

        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, athlete_id);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            DossierMedical dossierMedical = new DossierMedical();
            dossierMedical.setId(rs.getInt("id"));
            dossierMedical.setAllergies(rs.getString("allergies"));
            dossierMedical.setVaccinations(rs.getString("vaccinations"));
            dossierMedical.setDescription(rs.getString("description"));
            dossierMedical.setEtatAthlete(EtatAthlete.valueOf(rs.getString("etatAthlete")));
            dossierMedical.setDernierCheckup(rs.getTimestamp("dernierCheckup").toLocalDateTime());

            dossierMedicals.add(dossierMedical);

        }
        return dossierMedicals;
    }
}
