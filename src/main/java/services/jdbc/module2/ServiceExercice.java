package services.module2;

import models.module2.Exercice;
import services.BaseService;
import services.IService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceExercice extends BaseService implements IService<Exercice> {
    @Override
    public void add(Exercice exercice) throws SQLException {
        //nom	typeExercice	dureeMinutes	sets	reps	image_url
        String sql = "INSERT INTO exercice(nom, typeExercice, dureeMinutes, sets, reps, image_url) VALUES (?,?,?,?,?,?)";
        PreparedStatement stmt = con.prepareStatement(sql);

        stmt.setString(1, exercice.getNom());
        stmt.setString(2, exercice.getTypeExercice().name());
        stmt.setInt(3, exercice.getDureeMinutes());
        stmt.setInt(4, exercice.getSets());
        stmt.setInt(5, exercice.getReps());
        stmt.setString(6, exercice.getImage_url());

        stmt.executeUpdate();
        System.out.println("Exercice " + exercice.getNom() + " ajoute.");
    }

    @Override
    public void edit(Exercice exercice) throws SQLException {
        String sql = "UPDATE exercice SET nom = ?, typeExercice = ?, dureeMinutes = ?, sets = ?, reps = ?, image_url = ? WHERE id = ?";
        PreparedStatement stmt = con.prepareStatement(sql);

        stmt.setString(1, exercice.getNom());
        stmt.setString(2, exercice.getTypeExercice().name());
        stmt.setInt(3, exercice.getDureeMinutes());
        stmt.setInt(4, exercice.getSets());
        stmt.setInt(5, exercice.getReps());
        stmt.setString(6, exercice.getImage_url());
        stmt.setInt(7, exercice.getId());

        stmt.executeUpdate();
        System.out.println("Exercice " + exercice.getNom() + " modifie.");

    }

    @Override
    public void delete(Exercice exercice) throws SQLException {
        String sql = "DELETE FROM exercice WHERE id = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, exercice.getId());
        stmt.executeUpdate();
        System.out.println("Exercice " + exercice.getNom() + " supprime.");
    }

    @Override
    public Exercice get(Exercice exercice) throws SQLException {
        return get(exercice.getId());
    }

    @Override
    public Exercice get(int id) throws SQLException {
        String sql = "SELECT * FROM exercice WHERE id = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            Exercice returnedExcercice = new Exercice();
            returnedExcercice.setId(rs.getInt("id"));
            returnedExcercice.setNom(rs.getString("nom"));
            returnedExcercice.setDureeMinutes(rs.getInt("dureeMinutes"));
            returnedExcercice.setSets(rs.getInt("sets"));
            returnedExcercice.setReps(rs.getInt("reps"));
            returnedExcercice.setImage_url(rs.getString("image_url"));
            return returnedExcercice;

        }
        return null;
    }

    @Override
    public List<Exercice> getAll() throws SQLException {
        List<Exercice> exercices = new ArrayList<>();

        String sql = "SELECT * FROM exercice";
        PreparedStatement stmt = con.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Exercice exercice = new Exercice();
            exercice.setId(rs.getInt("id"));
            exercice.setNom(rs.getString("nom"));
            exercice.setDureeMinutes(rs.getInt("dureeMinutes"));
            exercice.setSets(rs.getInt("sets"));
            exercice.setReps(rs.getInt("reps"));
            exercice.setImage_url(rs.getString("image_url"));
            exercices.add(exercice);

        }
        return exercices;
    }



}
