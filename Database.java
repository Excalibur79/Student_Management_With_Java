import java.sql.*;
public class Database{
    Connection conn;
    public static String insertStatement="insert into `Students`(id,`name`,`cgpa`,`section`) values(?,?,?,?)";
    public static String printTableStatement="select * from `Students`";
    public static String findByIdStatement="select * from `Students` where `id`=?";
    public static String greaterThanCgpaStatement="select * from `Students` where `cgpa`>=?";
    public static String lessThanCgpaStatement="select * from `Students` where cgpa<=?";
    public static String deleteStatement="delete from `Students` where `id`=?";
    public static String updateStatement="update `Students` set cgpa=? where id=?";
    public static String getBySectionStatement="select * from `Students` where `section`=?";
     //Color Codes========================================================================
     final static  String ANSI_RESET = "\u001B[0m"; 
     final  static  String ANSI_BLACK = "\u001B[30m"; 
     final  static  String ANSI_RED = "\u001B[31m"; 
     final  static  String ANSI_GREEN = "\u001B[32m"; 
     final  static  String ANSI_YELLOW = "\u001B[33m"; 
     final  static  String ANSI_BLUE = "\u001B[34m"; 
     final  static  String ANSI_PURPLE = "\u001B[35m"; 
     final  static  String ANSI_CYAN = "\u001B[36m"; 
     final  static  String ANSI_WHITE = "\u001B[37m"; 
    //===================================================================================

    Database(String url,String user,String password){
        try{
            this.conn=DriverManager.getConnection(url, user, password);
        }
        catch (Exception e) {
            this.conn=null;
            e.printStackTrace();
         }
    }
    public void closeConnection() throws SQLException{
        this.conn.close();
        System.out.println(ANSI_CYAN+"\n Database Connection Closed!"+ANSI_RESET);
    }

    public boolean insertData(Student std){
        try{
            PreparedStatement statement=this.conn.prepareStatement(insertStatement);
            statement.setInt(1, std.id);
            statement.setString(2, std.name);
            statement.setFloat(3, std.cgpa);
            statement.setString(4,std.section);
            int rows=statement.executeUpdate();
            return rows>0?true:false;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public void findById(int id)throws SQLException{
        PreparedStatement statement=this.conn.prepareStatement(findByIdStatement ,ResultSet.TYPE_SCROLL_INSENSITIVE, 
        ResultSet.CONCUR_UPDATABLE);
        statement.setInt(1,id);
        ResultSet res=statement.executeQuery();
        int numberOfRows=0;
        while(res.next()){
            numberOfRows++;
        }
        if(numberOfRows>0){
            res.beforeFirst();
            System.out.println(ANSI_CYAN+"\n =====The Student is======"+ANSI_RESET);           
            res.next();
            System.out.println("\n ID : "+res.getString("id"));
            System.out.println(" Name : "+res.getString("name"));
            System.out.println(" CGPA : "+res.getString("cgpa"));
            System.out.println(" Section :"+res.getString("section"));
        }
        else
            System.out.println(ANSI_RED+"\n =====No Student Found======"+ANSI_RESET);
    }

    public boolean deleteRecord(int id)throws SQLException{
        PreparedStatement statement=this.conn.prepareStatement(deleteStatement);
        statement.setInt(1,id);
        return statement.executeUpdate()>0
        ?true:false;
    }

    public void printDatabase()throws SQLException{
        Statement statement=this.conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, 
        ResultSet.CONCUR_UPDATABLE);
        ResultSet res=statement.executeQuery(printTableStatement);
        printResultSet(res);       
    }

    public void getByCgpaCondition(float cgpa,String Cond)throws SQLException{
        String conditionStatement=Cond=="Greater"?greaterThanCgpaStatement:lessThanCgpaStatement;
        PreparedStatement statement=this.conn.prepareStatement(conditionStatement,ResultSet.TYPE_SCROLL_INSENSITIVE, 
        ResultSet.CONCUR_UPDATABLE);
        statement.setFloat(1, cgpa);
        ResultSet res=statement.executeQuery();
        printResultSet(res);       
    }

    public boolean handleUpdate(int id,float cgpa)throws SQLException{
        PreparedStatement statement=this.conn.prepareStatement(updateStatement);
        statement.setFloat(1,cgpa);
        statement.setInt(2,id);
        return statement.executeUpdate()>0
        ?true:false;
    }

    public void getBySection(String section)throws SQLException{
        PreparedStatement statement=this.conn.prepareStatement(getBySectionStatement,ResultSet.TYPE_SCROLL_INSENSITIVE, 
        ResultSet.CONCUR_UPDATABLE);
        statement.setString(1, section);
        ResultSet res=statement.executeQuery();
        printResultSet(res);
    }
    public void printResultSet(ResultSet res)throws SQLException{
        int numberOfRows=0;
        while(res.next()){
            numberOfRows++;
        }
        if(numberOfRows>0){
            res.beforeFirst();
            System.out.println(ANSI_CYAN+"\n =====The Students are======"+ANSI_RESET);    
            int counter=0;       
            while(res.next()){
                counter++;
                System.out.println(ANSI_CYAN+"\n STUDENT "+counter+ANSI_RESET);
                System.out.println(ANSI_YELLOW+"\n ID : "+res.getString("id")+ANSI_RESET);
                System.out.println(ANSI_YELLOW+" Name : "+res.getString("name")+ANSI_RESET);
                System.out.println(ANSI_YELLOW+" CGPA : "+res.getString("cgpa")+ANSI_RESET);
                System.out.println(ANSI_YELLOW+" Section :"+res.getString("section")+ANSI_RESET);
            }
        }
        else{
            System.out.println(ANSI_RED+"\n =====No Student Found======"+ANSI_RESET);
        }
    }
}