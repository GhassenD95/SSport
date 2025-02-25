package services.module2;

import models.module2.ExerciceEntrainment;
import services.BaseService;
import services.IService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceExerciceEntrainment extends BaseService implements IService<ExerciceEntrainment> {
    @Override
    public void add(ExerciceEntrainment exerciceEntrainment) throws SQLException {
        String sql = "INSERT INTO exercice_entrainment(entrainment_id, exercice_id) VALUES(?,?)";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, exerciceEntrainment.getEntrainment().getId());
        stmt.setInt(2, exerciceEntrainment.getExercice().getId());
        stmt.executeUpdate();
        System.out.println("Entrainment " + exerciceEntrainment + " ajoute");

    }

    @Override
    public void edit(ExerciceEntrainment exerciceEntrainment) throws SQLException {
        String sql = "UPDATE exercice_entrainment SET exercice_id = ?, entrainment_id = ?  WHERE id = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, exerciceEntrainment.getEntrainment().getId());
        stmt.setInt(2, exerciceEntrainment.getExercice().getId());
        stmt.setInt(3, exerciceEntrainment.getId());
        stmt.executeUpdate();
        System.out.println("Exercice_Entrainment " + exerciceEntrainment + " modifie");

    }

    @Override
    public void delete(ExerciceEntrainment exerciceEntrainment) throws SQLException {
        String sql = "DELETE FROM exercice_entrainment WHERE id = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, exerciceEntrainment.getId());
        stmt.executeUpdate();
        System.out.println("Exercice_Entrainment " + exerciceEntrainment + " suprrime");
    }

    @Override
    public ExerciceEntrainment get(ExerciceEntrainment exerciceEntrainment) throws SQLException {
        return get(exerciceEntrainment.getId());
    }

    @Override
    public ExerciceEntrainment get(int id) throws SQLException {
        String sql = "SELECT * FROM exercice_entrainment WHERE id = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            ExerciceEntrainment exerciceEntrainment = new ExerciceEntrainment();
            exerciceEntrainment.setId(rs.getInt("id"));
            exerciceEntrainment.setEntrainment(new ServiceEntrainment().get(rs.getInt("entrainment_id")));
            exerciceEntrainment.setExercice(new ServiceExercice().get(rs.getInt("exercice_id")));
            return exerciceEntrainment;
        }
        return null;
    }

    @Override
    public List<ExerciceEntrainment> getAll() throws SQLException {
        List<ExerciceEntrainment> exerciceEntrainments = new ArrayList<>();

        String sql = "SELECT * FROM exercice_entrainment";
        PreparedStatement stmt = con.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            ExerciceEntrainment exerciceEntrainment = new ExerciceEntrainment();
            exerciceEntrainment.setId(rs.getInt("id"));
            exerciceEntrainment.setEntrainment(new ServiceEntrainment().get(rs.getInt("entrainment_id")));
            exerciceEntrainment.setExercice(new ServiceExercice().get(rs.getInt("exercice_id")));
            exerciceEntrainments.add(exerciceEntrainment);
        }
        return exerciceEntrainments;
    }

}
