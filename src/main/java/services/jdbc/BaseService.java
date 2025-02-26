package services.jdbc;


import java.sql.Connection;

public abstract class BaseService {
    protected Connection con;


    public BaseService() {
        con = DbConnection.getInstance().getConn();
    }
}
