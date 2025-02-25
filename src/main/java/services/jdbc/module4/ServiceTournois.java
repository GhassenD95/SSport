package services.module4;

import enums.Sport;
import models.module4.Tournois;
import services.BaseService;
import services.IService;
import services.module5.ServiceMatchSportif;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceTournois extends BaseService implements IService<Tournois> {

    @Override
    public void add(Tournois tournois) throws SQLException {
        //nom	sport	dateDebut	dateFin	adresse
        String sql = "INSERT INTO tournois(nom, sport, dateDebut, dateFin, adresse)";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, tournois.getNom());
        stmt.setString(2, tournois.getSport().name());
        stmt.setObject(3, tournois.getDateDebut());
        stmt.setObject(4, tournois.getDateFin());
        stmt.setString(5, tournois.getAdresse());

        stmt.executeUpdate();
        System.out.println("Tournois " + tournois.getNom() + " ajoute");

    }

    @Override
    public void edit(Tournois tournois) throws SQLException {

    }

    @Override
    public void delete(Tournois tournois) throws SQLException {
        String sql = "DELETE FROM tournois WHERE id = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, tournois.getId());
        stmt.executeUpdate();

        System.out.println("Tournois " + tournois.getNom() + " supprime");
    }

    @Override
    public Tournois get(Tournois tournois) throws SQLException {
        return get(tournois.getId());
    }

    @Override
    public Tournois get(int id) throws SQLException {
        String sql = "SELECT * FROM tournois WHERE id = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            Tournois tournois = new Tournois();
            tournois.setId(rs.getInt("id"));
            tournois.setNom(rs.getString("nom"));
            tournois.setAdresse(rs.getString("adresse"));
            tournois.setDateDebut(rs.getTimestamp("dateDebut").toLocalDateTime());
            tournois.setDateFin(rs.getTimestamp("dateFin").toLocalDateTime());
            tournois.setSport(Sport.valueOf(rs.getString("sport")));


            //fill list
            tournois.setPerformanceEquipes(new ServicePerformanceEquipe().getPerformanceEquipesByTournoisId(tournois.getId()));
            tournois.setMatches(new ServiceMatchSportif().getMatchSportifsByTournoisId(tournois.getId()));

            return tournois;
        }
        return null;
    }

    @Override
    public List<Tournois> getAll() throws SQLException {
        String sql = "SELECT * FROM tournois";
        PreparedStatement stmt = con.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        List<Tournois> tournoisList = new ArrayList<>();
        while (rs.next()) {
            Tournois tournois = new Tournois();
            tournois.setId(rs.getInt("id"));
            tournois.setNom(rs.getString("nom"));
            tournois.setAdresse(rs.getString("adresse"));
            tournois.setDateDebut(rs.getTimestamp("dateDebut").toLocalDateTime());
            tournois.setDateFin(rs.getTimestamp("dateFin").toLocalDateTime());
            tournois.setSport(Sport.valueOf(rs.getString("sport")));



            //fill list
            tournois.setPerformanceEquipes(new ServicePerformanceEquipe().getPerformanceEquipesByTournoisId(tournois.getId()));
            tournois.setMatches(new ServiceMatchSportif().getMatchSportifsByTournoisId(tournois.getId()));

            tournoisList.add(tournois);
        }
        return tournoisList;
    }
}
