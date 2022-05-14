import java.sql.*;

public class Movie_database{
    Connection con = null;

    //connecting to mysql database on localhost with database name "movie_database"
    public Movie_database(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/movie_database","root","Manoj@250");
            System.out.println("Connection Successful");
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
    
    //creating the table movies
    public void create_table(){
        try{
            Statement stmt = con.createStatement();
            String sql = "CREATE TABLE movies(id INT NOT NULL AUTO_INCREMENT,name VARCHAR(50) NOT NULL,actor varchar(20) NOT NULL,actress varchar(20) NOT NULL,director varchar(20) NOT NULL, year INT NOT NULL, PRIMARY KEY(id))";
            stmt.executeUpdate(sql);
            System.out.println("Table created");
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    //inserting data into the table
    public void data_inserter(String name,String actor,String actress,String director,int year){
        try{
            Statement stmt = con.createStatement();
            String sql = String.format("insert into movie_database.movies(name,actor,actress,director,year) values('%s','%s','%s','%s','%d')",name,actor,actress,director,year);
            stmt.executeUpdate(sql);
            System.out.println("Data inserted");
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    //retrieving data from the table
    //if all=true, then all the data is retrieved
    //if name is null then actor name is used to retrieve data
    //if actor is null then movie name is used to retrieve data
    public void get_data(boolean all,String name,String actor){
        try{
            Statement stmt = con.createStatement();
            String sql;
            if(all){
                sql = "select * from  movie_database.movies";
                ResultSet rs = stmt.executeQuery(sql);
                while(rs.next()){
                    System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getString(3)+" "+rs.getString(4)+" "+rs.getString(5)+" "+rs.getInt(6));
                }
            }
            else{
                if(name==null){
                     sql = String.format("select * from  movie_database.movies where actor='%s'",actor);
                }
                else{
                     sql = String.format("select * from  movie_database.movies where name='%s'",name);
                }
                
                
                ResultSet rs = stmt.executeQuery(sql);
                while(rs.next()){
                    System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getString(3)+" "+rs.getString(4)+" "+rs.getString(5)+" "+rs.getInt(6));
                }
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    //closing the connection
    public void close_connection(){
        try{
            con.close();
            System.out.println("Connection closed");
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    public static void main(String[] args){
        Movie_database m = new Movie_database();
        
        System.out.println("-".repeat(30));
        
        //creating the table
        m.create_table();
        System.out.println("-".repeat(30));

        //inserting data
        m.data_inserter("KGF Chapter 2","Yash","Shreenidhi shetty","Prashant neel",2022);

        m.data_inserter("Jhon Wick","Keanu Reeves","Helen Wick","David Leitch",2014);

        m.data_inserter("Baahubali 2: The Conclusion","Prabhas","Anushka Sharma","S.S. Rajamouli",2017);

        System.out.println("-".repeat(30));
        
        //retrieving data

        //without any parameter
        System.out.println("selcting data without any parameter");
        m.get_data(true,null,null);
        System.out.println("-".repeat(30));


        //with movie name
        System.out.println("selcting data with movie name");
        m.get_data(false,"Baahubali 2: The Conclusion",null);
        System.out.println("-".repeat(30));

        //with actor name
        System.out.println("selcting data with actor name");
        m.get_data(false,null,"Prabhas");
        System.out.println("-".repeat(30));

        //closing the connection
        m.close_connection();
    }
}