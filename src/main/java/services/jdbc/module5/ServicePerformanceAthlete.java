package services.module5;

import models.module5.PerformanceAthlete;
import services.BaseService;
import services.IService;
import services.module1.ServiceUtilisateur;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServicePerformanceAthlete extends BaseService implements IService<PerformanceAthlete> {
    @Override
    public void add(PerformanceAthlete performanceAthlete) throws SQLException {
        //athlete_id	match_id	minutesJouees	buts	passesDecisives	tirs	interceptions	fautes	cartonsJaunes	cartonsRouges	rebonds
        String sql = "INSERT INTO performanceathlete(athlete_id, match_id, minutesJouees, buts, passesDecisives, tirs, interceptions, fautes, cartonsJaunes, cartonsRouges, rebonds) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = con.prepareStatement(sql);

        // Non-nullable fields (athlete_id and match_id)
        stmt.setInt(1, performanceAthlete.getAthlete().getId());
        stmt.setInt(2, performanceAthlete.getMatchSportif().getId());
        stmt.setObject(3, performanceAthlete.getMinutesJouees(), java.sql.Types.INTEGER);
        stmt.setObject(4, performanceAthlete.getButs(), java.sql.Types.INTEGER);
        stmt.setObject(5, performanceAthlete.getPassesDecisives(), java.sql.Types.INTEGER);
        stmt.setObject(6, performanceAthlete.getTirs(), java.sql.Types.INTEGER);
        stmt.setObject(7, performanceAthlete.getInterceptions(), java.sql.Types.INTEGER);
        stmt.setObject(8, performanceAthlete.getFautes(), java.sql.Types.INTEGER);
        stmt.setObject(9, performanceAthlete.getCartonsJaunes(), java.sql.Types.INTEGER);
        stmt.setObject(10, performanceAthlete.getCartonsRouges(), java.sql.Types.INTEGER);
        stmt.setObject(11, performanceAthlete.getRebonds(), java.sql.Types.INTEGER);


        stmt.executeUpdate();
        System.out.println("PerformanceAthlete record added successfully.");

    }

    @Override
    public void edit(PerformanceAthlete performanceAthlete) throws SQLException {

    }

    @Override
    public void delete(PerformanceAthlete performanceAthlete) throws SQLException {
        String sql = "DELETE FROM performanceathlete WHERE athlete_id = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, performanceAthlete.getId());
        stmt.executeUpdate();
        System.out.println("PerformanceAthlete record deleted successfully.");
    }

    @Override
    public PerformanceAthlete get(PerformanceAthlete performanceAthlete) throws SQLException {
        return get(performanceAthlete.getId());
    }

    @Override
    public PerformanceAthlete get(int id) throws SQLException {
        String sql = "SELECT * FROM performanceathlete WHERE athlete_id = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            PerformanceAthlete performanceAthlete = new PerformanceAthlete();
            performanceAthlete.setId(rs.getInt("id"));
            performanceAthlete.setAthlete(new ServiceUtilisateur().get(rs.getInt("athlete_id")));
            performanceAthlete.setMatchSportif(new ServiceMatchSportif().get(rs.getInt("match_id")));

            // Handling nullable fields
            int value;

            value = rs.getInt("cartonsJaunes");
            performanceAthlete.setCartonsJaunes(rs.wasNull() ? null : value);

            value = rs.getInt("cartonsRouges");
            performanceAthlete.setCartonsRouges(rs.wasNull() ? null : value);

            value = rs.getInt("interceptions");
            performanceAthlete.setInterceptions(rs.wasNull() ? null : value);

            value = rs.getInt("fautes");
            performanceAthlete.setFautes(rs.wasNull() ? null : value);

            value = rs.getInt("minutesJouees");
            performanceAthlete.setMinutesJouees(rs.wasNull() ? null : value);

            value = rs.getInt("buts");
            performanceAthlete.setButs(rs.wasNull() ? null : value);

            value = rs.getInt("rebonds");
            performanceAthlete.setRebonds(rs.wasNull() ? null : value);

            value = rs.getInt("tirs");
            performanceAthlete.setTirs(rs.wasNull() ? null : value);

            value = rs.getInt("passesDecisives");
            performanceAthlete.setPassesDecisives(rs.wasNull() ? null : value);


            return performanceAthlete;
        }
        return null;


    }

    @Override
    public List<PerformanceAthlete> getAll() throws SQLException {
        List<PerformanceAthlete> performanceAthletes = new ArrayList<>();

        String sql = "SELECT * FROM performanceathlete";
        PreparedStatement stmt = con.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            PerformanceAthlete performanceAthlete = new PerformanceAthlete();

            performanceAthlete.setId(rs.getInt("id"));
            performanceAthlete.setAthlete(new ServiceUtilisateur().get(rs.getInt("athlete_id")));
            performanceAthlete.setMatchSportif(new ServiceMatchSportif().get(rs.getInt("match_id")));

            // Handling nullable fields
            int value;

            value = rs.getInt("cartonsJaunes");
            performanceAthlete.setCartonsJaunes(rs.wasNull() ? null : value);

            value = rs.getInt("cartonsRouges");
            performanceAthlete.setCartonsRouges(rs.wasNull() ? null : value);

            value = rs.getInt("interceptions");
            performanceAthlete.setInterceptions(rs.wasNull() ? null : value);

            value = rs.getInt("fautes");
            performanceAthlete.setFautes(rs.wasNull() ? null : value);

            value = rs.getInt("minutesJouees");
            performanceAthlete.setMinutesJouees(rs.wasNull() ? null : value);

            value = rs.getInt("buts");
            performanceAthlete.setButs(rs.wasNull() ? null : value);

            value = rs.getInt("rebonds");
            performanceAthlete.setRebonds(rs.wasNull() ? null : value);

            value = rs.getInt("tirs");
            performanceAthlete.setTirs(rs.wasNull() ? null : value);

            value = rs.getInt("passesDecisives");
            performanceAthlete.setPassesDecisives(rs.wasNull() ? null : value);


            performanceAthletes.add(performanceAthlete);
        }

        return performanceAthletes;

    }

    public List<PerformanceAthlete> getPerformanceAthletesById(int athlete_id) throws SQLException {
        List<PerformanceAthlete> performanceAthletes = new ArrayList<>();
        String sql = "SELECT * FROM performanceathlete WHERE athlete_id = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, athlete_id);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            PerformanceAthlete performanceAthlete = new PerformanceAthlete();

            performanceAthlete.setId(rs.getInt("id"));
            performanceAthlete.setAthlete(new ServiceUtilisateur().get(rs.getInt("athlete_id")));
            performanceAthlete.setMatchSportif(new ServiceMatchSportif().get(rs.getInt("match_id")));

            // Handling nullable fields
            int value;

            value = rs.getInt("cartonsJaunes");
            performanceAthlete.setCartonsJaunes(rs.wasNull() ? null : value);

            value = rs.getInt("cartonsRouges");
            performanceAthlete.setCartonsRouges(rs.wasNull() ? null : value);

            value = rs.getInt("interceptions");
            performanceAthlete.setInterceptions(rs.wasNull() ? null : value);

            value = rs.getInt("fautes");
            performanceAthlete.setFautes(rs.wasNull() ? null : value);

            value = rs.getInt("minutesJouees");
            performanceAthlete.setMinutesJouees(rs.wasNull() ? null : value);

            value = rs.getInt("buts");
            performanceAthlete.setButs(rs.wasNull() ? null : value);

            value = rs.getInt("rebonds");
            performanceAthlete.setRebonds(rs.wasNull() ? null : value);

            value = rs.getInt("tirs");
            performanceAthlete.setTirs(rs.wasNull() ? null : value);

            value = rs.getInt("passesDecisives");
            performanceAthlete.setPassesDecisives(rs.wasNull() ? null : value);

            performanceAthletes.add(performanceAthlete);


        }
        return performanceAthletes;
    }

    public List<PerformanceAthlete> getPerformanceAthletesByMatchId(int match_id) throws SQLException {
        List<PerformanceAthlete> performanceAthletes = new ArrayList<>();
        String sql = "SELECT * FROM performanceathlete WHERE match_id = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, match_id);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            PerformanceAthlete performanceAthlete = new PerformanceAthlete();

            performanceAthlete.setId(rs.getInt("id"));
            performanceAthlete.setAthlete(new ServiceUtilisateur().get(rs.getInt("athlete_id")));
            performanceAthlete.setMatchSportif(new ServiceMatchSportif().get(rs.getInt("match_id")));

            // Handling nullable fields
            int value;

            value = rs.getInt("cartonsJaunes");
            performanceAthlete.setCartonsJaunes(rs.wasNull() ? null : value);

            value = rs.getInt("cartonsRouges");
            performanceAthlete.setCartonsRouges(rs.wasNull() ? null : value);

            value = rs.getInt("interceptions");
            performanceAthlete.setInterceptions(rs.wasNull() ? null : value);

            value = rs.getInt("fautes");
            performanceAthlete.setFautes(rs.wasNull() ? null : value);

            value = rs.getInt("minutesJouees");
            performanceAthlete.setMinutesJouees(rs.wasNull() ? null : value);

            value = rs.getInt("buts");
            performanceAthlete.setButs(rs.wasNull() ? null : value);

            value = rs.getInt("rebonds");
            performanceAthlete.setRebonds(rs.wasNull() ? null : value);

            value = rs.getInt("tirs");
            performanceAthlete.setTirs(rs.wasNull() ? null : value);

            value = rs.getInt("passesDecisives");
            performanceAthlete.setPassesDecisives(rs.wasNull() ? null : value);

            performanceAthletes.add(performanceAthlete);

        }

        return performanceAthletes;
    }
}
