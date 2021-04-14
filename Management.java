import java.sql.SQLException;
import java.io.*;
class Mananagement{
     //Credentials=======================================================================
     public static String MySQLURL = "databaseurl";
     public static String databaseUserName = "databaseusernane";
     public static String databasePassword = "databasepassword";
     //===================================================================================

     
     public static BufferedReader  br = new BufferedReader(new InputStreamReader(System.in));
     public static Database database=new Database(MySQLURL,databaseUserName,databasePassword);


     //Student Datas======================================================================
     public static int id;public static String name,section;
     public static float cgpa;
     static Student std;
     //===================================================================================
     
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


    public static void main(String[] args)throws SQLException,IOException{
        if(database.conn!=null){
            System.out.println(ANSI_CYAN+"\n Connection Successfull !"+ANSI_RESET);
            int loop=1;
            int Case;
            while(loop==1){
                System.out.print(ANSI_YELLOW+"\n =====COMMANDS=====\n \n 1 Create Student \n 2 Find by enroll_id \n 3 Delete Record \n 4 Print The Database \n 5 Print Students with CGPA condition \n 6 Update Student CGPA \n\n Option : "+ANSI_RESET);
                Case=Integer.parseInt(br.readLine());
                switch(Case){
                    case 1:CreateStudent();                           
                            break;
                    case 2:
                            System.out.print(ANSI_PURPLE+"\n Enter id : "+ANSI_RESET);
                            id=Integer.parseInt(br.readLine());
                            database.findById(id);                            
                            break;
                    case 3:
                            System.out.print(ANSI_PURPLE+"\n Enter ID To Delete Student : "+ANSI_RESET);
                            id=Integer.parseInt(br.readLine());
                            boolean res=database.deleteRecord(id);
                            System.out.println(
                                res?
                                ANSI_GREEN+"\n Student Deleted !"+ANSI_RESET:
                                ANSI_RED+"\n OPPS! Student Not Deleted !"+ANSI_RESET
                            );
                            break;
                    case 4:database.printDatabase();
                            break;
                    case 5:handleCgpaConditions();
                            break;
                    case 6:
                            System.out.print(ANSI_PURPLE+"\n Enter ID To Update CGPA : "+ANSI_RESET);
                            id=Integer.parseInt(br.readLine());
                            System.out.print(ANSI_PURPLE+" Enter New CGPA : "+ANSI_RESET);
                            cgpa=Float.parseFloat(br.readLine());
                            res = database.handleUpdate(id,cgpa);     
                            System.out.println(
                                res?
                                ANSI_GREEN+"\n Student Updated!"+ANSI_RESET:
                                ANSI_RED+"\n OPPS! Student Not Updated !"+ANSI_RESET
                            );                      
                            break;
                    default:
                        System.out.println(ANSI_RED+"\n Invalid Choice !"+ANSI_RESET);
                }
                System.out.print(ANSI_BLUE+"\n Press 1 to continue , else 0 to terminate : "+ANSI_RESET);
                loop=Integer.parseInt(br.readLine());
            }
            database.closeConnection();          
        }            
        else
            System.out.println("Connection Not Succesfull !");
    }

    public static void CreateStudent()throws IOException{    
        System.out.println(ANSI_PURPLE+"\n =====Enter Student Credentials====="+ANSI_RESET);
        System.out.print(ANSI_PURPLE+"\n Enter id : "+ANSI_RESET);
        id=Integer.parseInt(br.readLine());
        System.out.print(ANSI_PURPLE+" Enter Name : "+ANSI_RESET);
        name=br.readLine();
        System.out.print(ANSI_PURPLE+" Enter cgpa : "+ANSI_RESET);
        cgpa=Float.parseFloat(br.readLine());       
        System.out.print(ANSI_PURPLE+" Enter section : "+ANSI_RESET);
        section=br.readLine();
        std=new Student(id,name,cgpa,section);       
        boolean result =database.insertData(std);
        System.out.println(
            result?
            ANSI_GREEN+"\n Student Created !"+ANSI_RESET:
            ANSI_RED+"\n Student Not Created !"+ANSI_RESET
        );
    }
    
    public static void handleCgpaConditions()throws IOException, SQLException{
        System.out.println(ANSI_PURPLE+"\n =====Select Cgpa Conditions====="+ANSI_RESET);
        System.out.print(ANSI_YELLOW+"\n 1 - Greater Than \n 2 - Less Than \n\n Option : "+ANSI_RESET);
        int cgpaCase=Integer.parseInt(br.readLine());
        switch(cgpaCase){
            case 1:
                   System.out.print(ANSI_PURPLE+"\n Enter CGPA : "+ANSI_RESET);
                   cgpa=Float.parseFloat(br.readLine());
                   database.getByCgpaCondition(cgpa,"Greater");//greater than
                   break;
            case 2:
                    System.out.print(ANSI_PURPLE+"\n Enter CGPA : "+ANSI_RESET);
                    cgpa=Float.parseFloat(br.readLine());
                    database.getByCgpaCondition(cgpa,"Less");//less than
                    break;
            default:
                    System.out.println(ANSI_RED+"\n Invalid Choice !"+ANSI_RESET);
        }
    }
  
}
