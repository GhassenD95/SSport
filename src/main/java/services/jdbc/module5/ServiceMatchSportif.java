package services.module5;

import models.module5.MatchSportif;
import services.BaseService;
import services.IService;
import services.module1.ServiceEquipe;
import services.module4.ServiceTournois;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceMatchSportif extends BaseService implements IService<MatchSportif> {
    @Override
    public void add(MatchSportif matchSportif) throws SQLException {
        //	tournois_id	equipe1_id	equipe2_id	date	lieu

        String sql = "INSERT INTO matchsportif(tournois_id, equipe1_id, equipe2_id, date, lieu ) VALUES(?, ?, ?, ?, ?)";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, matchSportif.getTournois().getId());
        stmt.setInt(2, matchSportif.getEquipe1().getId());
        stmt.setInt(3, matchSportif.getEquipe2().getId());
        stmt.setObject(4, matchSportif.getDate());
        stmt.setString(5, matchSportif.getLieu());

        stmt.executeUpdate();
        System.out.println("match ajoute");
    }

    @Override
    public void edit(MatchSportif matchSportif) throws SQLException {

    }

    @Override
    public void delete(MatchSportif matchSportif) throws SQLException {
        String sql = "DELETE FROM matchsportif WHERE id = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, matchSportif.getId());
        stmt.executeUpdate();
        System.out.println("match supprimer");
    }

    @Override
    public MatchSportif get(MatchSportif matchSportif) throws SQLException {
        return get(matchSportif.getId());
    }

    @Override
    public MatchSportif get(int id) throws SQLException {
        String sql = "SELECT * FROM matchsportif WHERE id = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            MatchSportif matchSportif = new MatchSportif();
            matchSportif.setId(rs.getInt("id"));
            matchSportif.setEquipe1(new ServiceEquipe().get(rs.getInt("equipe1_id")));
            matchSportif.setEquipe2(new ServiceEquipe().get(rs.getInt("equipe2_id")));
            matchSportif.setTournois(new ServiceTournois().get(rs.getInt("tournois_id")));
            matchSportif.setDate(rs.getTimestamp("date").toLocalDateTime());
            matchSportif.setLieu(rs.getString("lieu"));
            return matchSportif;
        }
        return null;
    }

    @Override
    public List<MatchSportif> getAll() throws SQLException {
        List<MatchSportif> matchSportifs = new ArrayList<>();
        String sql = "SELECT * FROM matchsportif";
        PreparedStatement stmt = con.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            MatchSportif matchSportif = new MatchSportif();
            matchSportif.setId(rs.getInt("id"));
            matchSportif.setEquipe1(new ServiceEquipe().get(rs.getInt("equipe1_id")));
            matchSportif.setEquipe2(new ServiceEquipe().get(rs.getInt("equipe2_id")));
            matchSportif.setTournois(new ServiceTournois().get(rs.getInt("tournois_id")));
            matchSportif.setDate(rs.getTimestamp("date").toLocalDateTime());
            matchSportif.setLieu(rs.getString("lieu"));

            matchSportifs.add(matchSportif);
        }
        return matchSportifs;
    }

    public List<MatchSportif> getMatchSportifsByTournoisId(int tournois_id) throws SQLException {
        List<MatchSportif> matchSportifs = new ArrayList<>();
        String sql = "SELECT * FROM matchsportif WHERE tournois_id = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, tournois_id);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            MatchSportif matchSportif = new MatchSportif();
            matchSportif.setId(rs.getInt("id"));
            matchSportif.setEquipe1(new ServiceEquipe().get(rs.getInt("equipe1_id")));
            matchSportif.setEquipe2(new ServiceEquipe().get(rs.getInt("equipe2_id")));
            matchSportif.setTournois(new ServiceTournois().get(rs.getInt("tournois_id")));
            matchSportif.setDate(rs.getTimestamp("date").toLocalDateTime());
            matchSportif.setLieu(rs.getString("lieu"));

            matchSportifs.add(matchSportif);

        }
        return matchSportifs;
    }
}
