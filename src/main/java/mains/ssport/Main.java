package mains.ssport;

import models.module2.Entrainment;
import services.jdbc.DbConnection;
import services.jdbc.module2.ServiceEntrainment;

import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            System.out.println(new ServiceEntrainment().get(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
