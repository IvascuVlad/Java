import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class ArtistController {
    Database database;

    public ArtistController(Database database) {
        this.database = database;
    }

    public void create(String name, String country){
        try{
            String sql = "select max(id) from ARTISTS";
            Statement stmt1=database.con.createStatement();
            ResultSet rs1=stmt1.executeQuery(sql);
            int id = 0;
            while(rs1.next())
                id = rs1.getInt(1);
            id++;
            System.out.println("Incerc sa inserez artistul cu id = "+id);

            sql = "insert into ARTISTS values (" + id + ", '" + name + "','" + country +"')";
            Statement stmt2=database.con.createStatement();
            stmt2.executeQuery(sql);
            System.out.println("Am creat utilizatorul "+ name +" cu id = "+id);
        }catch(Exception e){ System.out.println(e);}
    }
    public void findByName(String name){
        try{
            String sql = "select * from ARTISTS where NAME=?";
            PreparedStatement stmt=database.con.prepareStatement(sql);
            stmt.setString(1,name);
            ResultSet rs=stmt.executeQuery();
            System.out.println("Am gasit artistul");
            while(rs.next())
                System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3));

        }catch(Exception e){ System.out.println(e);}
    }
}
