package services.jdbc.module1;

import enums.Role;
import enums.Status;
import models.module1.Utilisateur;
import services.jdbc.BaseService;
import services.jdbc.IService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class ServiceUtilisateur extends BaseService implements IService<Utilisateur> {
    @Override
    public void add(Utilisateur utilisateur) throws SQLException {
        String sql = "INSERT INTO utilisateur (nom, prenom, role, email, hashedPassword, adresse, telephone, status, image_url, equipe_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement stmt = con.prepareStatement(sql);

        stmt.setString(1, utilisateur.getNom());
        stmt.setString(2, utilisateur.getPrenom());
        stmt.setString(3, utilisateur.getRole().toString()); // Convert enum to String
        stmt.setString(4, utilisateur.getEmail());
        stmt.setString(5, utilisateur.getHashedPassword());
        stmt.setString(6, utilisateur.getAdresse());
        stmt.setString(7, utilisateur.getTelephone());
        stmt.setString(8, utilisateur.getStatus().toString()); // Convert enum to String
        stmt.setString(9, utilisateur.getImage_url());

        //
        ServiceEquipe serviceEquipe = new ServiceEquipe();
        if (utilisateur.getEquipe() != null && serviceEquipe.get(utilisateur.getEquipe()) != null) {
            stmt.setInt(10, serviceEquipe.get(utilisateur.getEquipe()).getId());
        } else {
            stmt.setNull(10, Types.INTEGER); // Set NULL for nullable column
        }


        stmt.executeUpdate();
        System.out.println("utilisateur ajoutée.");
    }

    @Override
    public void edit(Utilisateur utilisateur) throws SQLException {
        String sql = "UPDATE utilisateur SET nom = ?, prenom = ?, role = ?, email = ?, hashedPassword = ?, adresse = ?, telephone = ?, status = ?, image_url = ?, equipe_id = ? WHERE id = ?";

        PreparedStatement stmt = con.prepareStatement(sql);


        stmt.setString(1, utilisateur.getNom());
        stmt.setString(2, utilisateur.getPrenom());
        stmt.setString(3, utilisateur.getRole().toString()); // Convert enum to String
        stmt.setString(4, utilisateur.getEmail());
        stmt.setString(5, utilisateur.getHashedPassword());
        stmt.setString(6, utilisateur.getAdresse());
        stmt.setString(7, utilisateur.getTelephone());
        stmt.setString(8, utilisateur.getStatus().toString()); // Convert enum to String
        stmt.setString(9, utilisateur.getImage_url());

        //
        ServiceEquipe serviceEquipe = new ServiceEquipe();
        if (utilisateur.getEquipe() != null && serviceEquipe.get(utilisateur.getEquipe()) != null) {
            stmt.setInt(10, serviceEquipe.get(utilisateur.getEquipe()).getId());
        } else {
            stmt.setNull(10, Types.INTEGER); // Set NULL for nullable column
        }

        stmt.setInt(11, utilisateur.getId());
        stmt.executeUpdate();
        System.out.println("Utilisateur modifié.");
    }

    @Override
    public void delete(Utilisateur utilisateur) throws SQLException {
        String sql = "DELETE FROM utilisateur WHERE id = ?";

        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, utilisateur.getId());
        stmt.executeUpdate();
        System.out.println("Utilisateur supprimé.");

    }

    @Override
    public Utilisateur get(Utilisateur utilisateur) throws SQLException {
        return get(utilisateur.getId());
    }

    @Override
    public Utilisateur get(int id) throws SQLException {
        String sql = "SELECT * FROM utilisateur WHERE id = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, id);
        Utilisateur returnedUtilisateur = new Utilisateur();
        ResultSet rs = stmt.executeQuery();
        if(rs.next()) {
            returnedUtilisateur.setId(rs.getInt("id"));
            returnedUtilisateur.setNom(rs.getString("nom"));
            returnedUtilisateur.setPrenom(rs.getString("prenom"));
            returnedUtilisateur.setEmail(rs.getString("email"));
            returnedUtilisateur.setHashedPassword(rs.getString("hashedPassword"));
            returnedUtilisateur.setAdresse(rs.getString("adresse"));
            returnedUtilisateur.setTelephone(rs.getString("telephone"));
            returnedUtilisateur.setImage_url(rs.getString("image_url"));
            returnedUtilisateur.setStatus(Status.valueOf(rs.getString("status")));
            returnedUtilisateur.setRole(Role.valueOf(rs.getString("role")));
            int equipe_id = rs.getInt("equipe_id");
            if(rs.wasNull()) {
                returnedUtilisateur.setEquipe(null);

            }else{
                ServiceEquipe se = new ServiceEquipe();
                returnedUtilisateur.setEquipe(se.get(equipe_id));

            }

            //fill lists
/*
            returnedUtilisateur.setPerformances(new ServicePerformanceAthlete().getPerformanceAthletesById(returnedUtilisateur.getId()));
            returnedUtilisateur.setEquipesEntrainees(new ServiceEquipe().getEquipesByCoachId(returnedUtilisateur.getId()));
            returnedUtilisateur.setBlessures(new ServiceBlessure().getBlessuresByAthleteId(returnedUtilisateur.getId()));
            returnedUtilisateur.setInstallationSportives(new ServiceInstallationSportive().getInstallationSportivesByManagerId(returnedUtilisateur.getId()));
            returnedUtilisateur.setDossierMedicals(new ServiceDossierMedical().getDossierMedicalsByAthleteId(returnedUtilisateur.getId()));
*/
            return  returnedUtilisateur;
        }
        return null;
    }

    @Override
    public List<Utilisateur> getAll() throws SQLException {
        List<Utilisateur> returnedUtilisateurs = new ArrayList<>();
        String sql = "SELECT * FROM utilisateur";
        PreparedStatement stmt = con.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        // Use while(rs.next()) to iterate over all rows in the result set
        while (rs.next()) {
            Utilisateur returnedUtilisateur = new Utilisateur();
            returnedUtilisateur.setId(rs.getInt("id"));
            returnedUtilisateur.setNom(rs.getString("nom"));
            returnedUtilisateur.setPrenom(rs.getString("prenom"));
            returnedUtilisateur.setEmail(rs.getString("email"));
            returnedUtilisateur.setHashedPassword(rs.getString("hashedPassword"));
            returnedUtilisateur.setAdresse(rs.getString("adresse"));
            returnedUtilisateur.setTelephone(rs.getString("telephone"));
            returnedUtilisateur.setImage_url(rs.getString("image_url"));
            returnedUtilisateur.setRole(Role.valueOf(rs.getString("role")));
            returnedUtilisateur.setStatus(Status.valueOf(rs.getString("status")));

            int equipe_id = rs.getInt("equipe_id");
            if (rs.wasNull()) {
                returnedUtilisateur.setEquipe(null);
            } else {
                ServiceEquipe se = new ServiceEquipe();
                returnedUtilisateur.setEquipe(se.get(equipe_id));
            }

            //fill lists
/*
            returnedUtilisateur.setPerformances(new ServicePerformanceAthlete().getPerformanceAthletesById(returnedUtilisateur.getId()));
            returnedUtilisateur.setEquipesEntrainees(new ServiceEquipe().getEquipesByCoachId(returnedUtilisateur.getId()));
            returnedUtilisateur.setBlessures(new ServiceBlessure().getBlessuresByAthleteId(returnedUtilisateur.getId()));
            returnedUtilisateur.setInstallationSportives(new ServiceInstallationSportive().getInstallationSportivesByManagerId(returnedUtilisateur.getId()));
            returnedUtilisateur.setDossierMedicals(new ServiceDossierMedical().getDossierMedicalsByAthleteId(returnedUtilisateur.getId()));
*/

            returnedUtilisateurs.add(returnedUtilisateur);
        }

        return returnedUtilisateurs;

    }


    public List<Utilisateur> getAthletesByEquipeId(int equipeId) throws SQLException {
        String sql = "SELECT * FROM utilisateur WHERE equipe_id = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, equipeId);
        List<Utilisateur> returnedUtilisateurs = new ArrayList<>();
        ResultSet rs = stmt.executeQuery();

        // First, fetch all users associated with the given equipeId
        while (rs.next()) {
            Utilisateur returnedUtilisateur = new Utilisateur();
            returnedUtilisateur.setId(rs.getInt("id"));
            returnedUtilisateur.setNom(rs.getString("nom"));
            returnedUtilisateur.setPrenom(rs.getString("prenom"));
            returnedUtilisateur.setEmail(rs.getString("email"));
            returnedUtilisateur.setHashedPassword(rs.getString("hashedPassword"));
            returnedUtilisateur.setAdresse(rs.getString("adresse"));
            returnedUtilisateur.setTelephone(rs.getString("telephone"));
            returnedUtilisateur.setImage_url(rs.getString("image_url"));
            returnedUtilisateur.setRole(Role.valueOf(rs.getString("role")));

            int equipe_id = rs.getInt("equipe_id");
            if (rs.wasNull()) {
                returnedUtilisateur.setEquipe(null);
            } else {
                ServiceEquipe se = new ServiceEquipe();
                returnedUtilisateur.setEquipe(se.get(equipe_id));
            }

            //fill lists
           /* returnedUtilisateur.setPerformances(new ServicePerformanceAthlete().getPerformanceAthletesById(returnedUtilisateur.getId()));
            returnedUtilisateur.setEquipesEntrainees(new ServiceEquipe().getEquipesByCoachId(returnedUtilisateur.getId()));
            returnedUtilisateur.setBlessures(new ServiceBlessure().getBlessuresByAthleteId(returnedUtilisateur.getId()));
            returnedUtilisateur.setInstallationSportives(new ServiceInstallationSportive().getInstallationSportivesByManagerId(returnedUtilisateur.getId()));
            returnedUtilisateur.setDossierMedicals(new ServiceDossierMedical().getDossierMedicalsByAthleteId(returnedUtilisateur.getId()));*/
            returnedUtilisateurs.add(returnedUtilisateur);
        }
        return returnedUtilisateurs;
    }

    public Utilisateur getUtilisateurByEmailPassword(String email, String password) throws SQLException {
        String sql = "SELECT * FROM utilisateur WHERE email = ? AND hashedPassword = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, email);
        stmt.setString(2, password);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            Utilisateur returnedUtilisateur = new Utilisateur();
            returnedUtilisateur.setId(rs.getInt("id"));
            returnedUtilisateur.setNom(rs.getString("nom"));
            returnedUtilisateur.setPrenom(rs.getString("prenom"));
            returnedUtilisateur.setEmail(rs.getString("email"));
            returnedUtilisateur.setHashedPassword(rs.getString("hashedPassword"));
            returnedUtilisateur.setAdresse(rs.getString("adresse"));
            returnedUtilisateur.setTelephone(rs.getString("telephone"));
            returnedUtilisateur.setImage_url(rs.getString("image_url"));
            returnedUtilisateur.setRole(Role.valueOf(rs.getString("role")));
            int equipe_id = rs.getInt("equipe_id");
            if (rs.wasNull()) {
                returnedUtilisateur.setEquipe(null);
            } else {
                ServiceEquipe se = new ServiceEquipe();
                returnedUtilisateur.setEquipe(se.get(equipe_id));


            }
            return returnedUtilisateur;
        }
        return null;
    }

}
