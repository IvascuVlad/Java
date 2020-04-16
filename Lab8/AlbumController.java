import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class AlbumController {
    Database database;

    public AlbumController(Database database) {
        this.database = database;
    }

    public void create(String name, int artistId, int releaseYear){
        try{
            String sql = "select max(id) from ALBUMS";
            Statement stmt1=database.con.createStatement();
            ResultSet rs1=stmt1.executeQuery(sql);
            int id = 0;
            while(rs1.next())
                id = rs1.getInt(1);
            id++;

            sql = "insert into ALBUMS values (" + id + ", ' " + name + " ','" + artistId + "' ," + releaseYear + ")";
            Statement stmt2=database.con.createStatement();
            stmt2.executeQuery(sql);
        }catch(Exception e){ System.out.println(e);}
    }

    public int findByArtist(int artistId){
        try{
            String sql = "select * from ALBUMS where ARTIST_ID = ?";
            PreparedStatement stmt=database.con.prepareStatement(sql);
            stmt.setInt(1,artistId);
            ResultSet rs=stmt.executeQuery();
            return rs.getInt(1);
            /*while(rs.next())
                System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getInt(3)+"  "+rs.getInt(4));*/

        }catch(Exception e){ System.out.println(e);}
        return 0;
    }
}
