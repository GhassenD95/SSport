package services;



public abstract class BaseService {
    protected Connection con;


    public BaseService() {
        con = DbConnection.getInstance().getConn();
    }
}
