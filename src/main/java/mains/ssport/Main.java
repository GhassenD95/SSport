package mains.ssport;

import models.module1.Equipe;
import models.module2.Entrainment;
import services.jdbc.DbConnection;
import services.jdbc.module1.ServiceEquipe;
import services.jdbc.module1.ServiceUtilisateur;
import services.jdbc.module2.ServiceEntrainment;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            System.out.println(new ServiceUtilisateur().getAll());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
