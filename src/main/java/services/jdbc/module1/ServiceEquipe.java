package services.module1;

import enums.Division;
import enums.Sport;
import models.module1.Equipe;
import services.BaseService;
import services.IService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class ServiceEquipe extends BaseService implements IService<Equipe> {
    @Override
    public void add(Equipe equipe) throws SQLException {
        String sql = "INSERT INTO equipe(nom, division, sport, isLocal, coach_id) VALUES(?, ?, ?, ?, ?)";
        PreparedStatement stmt = con.prepareStatement(sql);

        stmt.setString(1, equipe.getNom());
        stmt.setString(2, equipe.getDivision().name());
        stmt.setString(3, equipe.getSport().name());
        stmt.setBoolean(4, equipe.isLocal());

        ServiceUtilisateur serviceUtilisateur = new ServiceUtilisateur();
        //check if coach exists in obj and checks if user exists in db to assign it or else excpetion constraint
        if(equipe.getCoach() != null && serviceUtilisateur.get(equipe.getCoach()) != null) {
            stmt.setInt(5, serviceUtilisateur.get(equipe.getCoach()).getId());
        }else{
            stmt.setNull(5, Types.INTEGER);
        }

        stmt.executeUpdate();
        System.out.println(equipe.getNom() +" ajouté.");



    }

    @Override
    public void edit(Equipe equipe) throws SQLException {
        String sql = "UPDATE equipe SET nom = ?, division = ?, sport = ?, isLocal = ?, coach_id = ? WHERE id = ?";
        PreparedStatement stmt = con.prepareStatement(sql);

        stmt.setString(1, equipe.getNom());
        stmt.setString(2, equipe.getDivision().name());
        stmt.setString(3, equipe.getSport().name());
        stmt.setBoolean(4, equipe.isLocal());

        //  nullable coach_id
        ServiceUtilisateur serviceUtilisateur = new ServiceUtilisateur();
        //check if coach exists in obj and checks if user exists in db to assign it or else excpetion constraint
        if(equipe.getCoach() != null && serviceUtilisateur.get(equipe.getCoach()) != null) {
            stmt.setInt(5, serviceUtilisateur.get(equipe.getCoach()).getId());
        } else {
            stmt.setNull(5, Types.INTEGER); // Set NULL if no coach
        }

        stmt.setInt(6, equipe.getId()); // Set the ID for WHERE condition

        stmt.executeUpdate();
        System.out.println(equipe.getNom() + " modifié.");

    }

    @Override
    public void delete(Equipe equipe) throws SQLException {
        String sql = "DELETE FROM equipe WHERE id = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, equipe.getId());
        stmt.executeUpdate();
        System.out.println(equipe.getNom() + " supprimé.");
    }

    @Override
    public Equipe get(Equipe equipe) throws SQLException {
        return get(equipe.getId());
    }

    @Override
    public Equipe get(int id) throws SQLException {
        ServiceUtilisateur serviceUtilisateur = new ServiceUtilisateur();
        String sql = "SELECT * FROM equipe WHERE id = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();

        if(rs.next()) {
            Equipe returnedEquipe = new Equipe();
            returnedEquipe.setId(rs.getInt("id"));
            returnedEquipe.setNom(rs.getString("nom"));
            returnedEquipe.setSport(Sport.valueOf(rs.getString("sport")));
            returnedEquipe.setIsLocal(rs.getBoolean("isLocal"));
            returnedEquipe.setDivision(Division.valueOf(rs.getString("division")));
            int coach_id = rs.getInt("coach_id");
            if(rs.wasNull()) {
                returnedEquipe.setCoach(null);
            }else{
                returnedEquipe.setCoach(serviceUtilisateur.get(coach_id));

            }
            //fill players list
            /*List<Utilisateur> utilisateurs = serviceUtilisateur.getAthletesByEquipeId(returnedEquipe.getId());
            List<Entrainment> entrainments = new ServiceEntrainment().getEntrainmentsByEquipeId(returnedEquipe.getId());
            returnedEquipe.setAthletes(utilisateurs);
            returnedEquipe.setEntrainments(entrainments);*/
            return returnedEquipe;

        }
        return null;
    }

    @Override
    public List<Equipe> getAll() throws SQLException {
        ServiceUtilisateur serviceUtilisateur = new ServiceUtilisateur();
        List<Equipe> returnedEquipes = new ArrayList<>();

        String sql = "SELECT * FROM equipe";
        PreparedStatement stmt = con.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        // Use while(rs.next()) to iterate over all rows in the result set
        while (rs.next()) {
            Equipe returnedEquipe = new Equipe();
            returnedEquipe.setId(rs.getInt("id"));
            returnedEquipe.setNom(rs.getString("nom"));
            returnedEquipe.setSport(Sport.valueOf(rs.getString("sport")));
            returnedEquipe.setIsLocal(rs.getBoolean("isLocal"));

            int coach_id = rs.getInt("coach_id");
            if (rs.wasNull()) {
                returnedEquipe.setCoach(null);
            } else {
                returnedEquipe.setCoach(serviceUtilisateur.get(coach_id));
            }

            /*//fill players list
            List<Utilisateur> utilisateurs = serviceUtilisateur.getAthletesByEquipeId(returnedEquipe.getId());
            List<Entrainment> entrainments = new ServiceEntrainment().getEntrainmentsByEquipeId(returnedEquipe.getId());
            returnedEquipe.setAthletes(utilisateurs);
            returnedEquipe.setEntrainments(entrainments);*/

            returnedEquipes.add(returnedEquipe);
        }
        return returnedEquipes;
    }
    /*//liste des equipes assigné a un utilisateur coach
    public List<Equipe> getEquipesByCoachId(int coach_id ) throws SQLException {
        ServiceUtilisateur serviceUtilisateur = new ServiceUtilisateur();
        List<Equipe> returnedEquipes = new ArrayList<>();

        String sql = "SELECT * FROM equipe WHERE coach_id = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, coach_id);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Equipe returnedEquipe = new Equipe();
            returnedEquipe.setId(rs.getInt("id"));
            returnedEquipe.setNom(rs.getString("nom"));
            returnedEquipe.setSport(Sport.valueOf(rs.getString("sport")));
            returnedEquipe.setIsLocal(rs.getBoolean("isLocal"));

            int coach_id_fromDB = rs.getInt("coach_id");
            if (rs.wasNull()) {
                returnedEquipe.setCoach(null);
            } else {
                returnedEquipe.setCoach(serviceUtilisateur.get(coach_id));
            }

            //fill players list
            List<Utilisateur> utilisateurs = serviceUtilisateur.getAthletesByEquipeId(returnedEquipe.getId());
            List<Entrainment> entrainments = new ServiceEntrainment().getEntrainmentsByEquipeId(returnedEquipe.getId());
            returnedEquipe.setAthletes(utilisateurs);
            returnedEquipe.setEntrainments(entrainments);

            returnedEquipes.add(returnedEquipe);

        }
        return returnedEquipes;
    }*/



}
