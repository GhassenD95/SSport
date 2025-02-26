package services.jdbc.module4;

import models.module4.PerformanceEquipe;
import services.jdbc.BaseService;
import services.jdbc.IService;
import services.jdbc.module1.ServiceEquipe;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServicePerformanceEquipe extends BaseService implements IService<PerformanceEquipe> {

    @Override
    public void add(PerformanceEquipe performanceEquipe) throws SQLException {
        //equipe_id	tournois_id	victoires	pertes	rang
        String sql = "INSERT INTO performanceequipe(equipe_id, tournois_id, victoires, pertes, rang) VALUES (?,?,?,?,?)";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, performanceEquipe.getEquipe().getId());
        stmt.setInt(2, performanceEquipe.getTournois().getId());
        stmt.setInt(3, performanceEquipe.getVictoires());
        stmt.setInt(4, performanceEquipe.getPertes());
        stmt.setInt(5, performanceEquipe.getRang());

        stmt.executeUpdate();
        System.out.println("Performance equipe ajoute");
    }

    @Override
    public void edit(PerformanceEquipe performanceEquipe) throws SQLException {

    }

    @Override
    public void delete(PerformanceEquipe performanceEquipe) throws SQLException {
        String sql = "DELETE FROM performanceequipe WHERE equipe_id = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, performanceEquipe.getId());
        stmt.executeUpdate();
        System.out.println("Performance equipe supprimer");
    }

    @Override
    public PerformanceEquipe get(PerformanceEquipe performanceEquipe) throws SQLException {
        return get(performanceEquipe.getId());
    }

    @Override
    public PerformanceEquipe get(int id) throws SQLException {
        String sql = "SELECT * FROM performanceequipe WHERE equipe_id = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            PerformanceEquipe performanceEquipe = new PerformanceEquipe();
            performanceEquipe.setId(rs.getInt("id"));
            performanceEquipe.setEquipe(new ServiceEquipe().get(rs.getInt("equipe_id")));
            performanceEquipe.setTournois(new ServiceTournois().get(rs.getInt("tournois_id")));
            performanceEquipe.setVictoires(rs.getInt("victoires"));
            performanceEquipe.setPertes(rs.getInt("pertes"));
            performanceEquipe.setRang(rs.getInt("rang"));

            return performanceEquipe;

        }
        return null;
    }

    @Override
    public List<PerformanceEquipe> getAll() throws SQLException {
        List<PerformanceEquipe> performanceEquipes = new ArrayList<>();
        String sql = "SELECT * FROM performanceequipe";
        PreparedStatement stmt = con.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            PerformanceEquipe performanceEquipe = new PerformanceEquipe();
            performanceEquipe.setId(rs.getInt("id"));
            performanceEquipe.setEquipe(new ServiceEquipe().get(rs.getInt("equipe_id")));
            performanceEquipe.setTournois(new ServiceTournois().get(rs.getInt("tournois_id")));
            performanceEquipe.setVictoires(rs.getInt("victoires"));
            performanceEquipe.setPertes(rs.getInt("pertes"));
            performanceEquipe.setRang(rs.getInt("rang"));

            performanceEquipes.add(performanceEquipe);
        }
        return performanceEquipes;
    }



    public List<PerformanceEquipe> getPerformanceEquipesByTournoisId(int tournois_id) throws SQLException {
        List<PerformanceEquipe> performanceEquipes = new ArrayList<>();
        String sql = "SELECT * FROM performanceequipe WHERE tournois_id = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, tournois_id);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            PerformanceEquipe performanceEquipe = new PerformanceEquipe();
            performanceEquipe.setId(rs.getInt("id"));
            performanceEquipe.setEquipe(new ServiceEquipe().get(rs.getInt("equipe_id")));
            performanceEquipe.setTournois(new ServiceTournois().get(rs.getInt("tournois_id")));
            performanceEquipe.setVictoires(rs.getInt("victoires"));
            performanceEquipe.setPertes(rs.getInt("pertes"));
            performanceEquipe.setRang(rs.getInt("rang"));

            performanceEquipes.add(performanceEquipe);

        }
        return performanceEquipes;
    }
}
