import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

public class Charts {
    Database database;
    String name;
    List<Album> charts;
    /*Pentru a stoca chart-urile am creat tabela CHARTS avand camputile id si nume
    * si tabela CHARTS_ALBUMS cu campurile CHART_ID,ALBUM_ID si POSITION (potitia melodiei in chart)*/

    public Charts(Database database, String name, List<Album> charts) {
        this.database = database;
        this.name = name;
        this.charts = charts;
    }

    public void to_database(){
        try{
            /*calculez urmatorul id*/
            String sql = "select max(id) from CHARTS";
            Statement stmt1=database.con.createStatement();
            ResultSet rs1=stmt1.executeQuery(sql);
            int id = 0;
            while(rs1.next()) {
                id = rs1.getInt(1) + 1;
            }

            sql = "insert into CHARTS values (" + id + ", '" + name + "')";
            Statement stmt2=database.con.createStatement();
            stmt2.executeQuery(sql);

            /*inserez in CHARTS_ALBUMS valori cautand id-ul albumului */
            for (int i = 0; i < charts.size(); i++) {
                System.out.println(charts.get(i));
                sql = "select id from ALBUMS where name = ' " + charts.get(i).name + " ' and RELEASE_YEAR= " + charts.get(i).release_year;
                stmt1 = database.con.createStatement();
                rs1 = stmt1.executeQuery(sql);
                int album_id = 0;
                while(rs1.next()){
                    album_id = rs1.getInt(1);
                }
                System.out.println(album_id);
                sql = "insert into CHARTS_ALBUMS values ("+ id +" , "+ album_id +" , " + (i+1) +")";
                stmt1 = database.con.createStatement();
                ResultSet rs2 = stmt1.executeQuery(sql);
                System.out.println("Inserez in charts_albums");
            }
        }catch(Exception e){ System.out.println(e);}
    }

    public void artistsRanking(){
        /*selectez din baza de date ALBUM_ID si POSITION intr-un Array adunand rank-urile
        * fiecarui album astfel albumul in top va avea o suma cat mai mica el fiind in pozitii
        * cat mai mici*/
        List <album_rank> ranking = new ArrayList<album_rank>();
        int album = 0 , rank = 0;
        boolean first_time = true;
        try {
            String sql = "select ALBUM_ID, POSITION from CHARTS_ALBUMS order by ALBUM_ID asc";
            Statement stmt1 = database.con.createStatement();
            ResultSet rs1 = stmt1.executeQuery(sql);
            while (rs1.next()) {
                Object o;
                o = rs1.getInt(1);
                //System.out.println("Asta e  o " + o);
                if(first_time)
                {
                    album = rs1.getInt(1);
                    rank = rs1.getInt(2);
                    first_time = false;
                }
                else {
                    if(album != rs1.getInt(1)){
                        ranking.add(new album_rank(album,rank));
                        album = rs1.getInt(1);
                        rank = rs1.getInt(2);
                    }
                    else {
                        rank += rs1.getInt(2);
                    }
                }
            }
            if(ranking.get(ranking.size() - 1).album != album ){
                ranking.add(new album_rank(album,rank));
            }
        }catch(Exception e){ System.out.println(e);}
        ranking.sort(Comparator.comparing(album_rank::getOrder));
        System.out.println(ranking);
        /*pe baza acestui vector obtinut selectez din baza de date numele artistului dupa id-ul
        * albumului creat de acesta*/
        for (int i = 0; i < ranking.size(); i++) {
            try {
                String sql = "select A2.name from ALBUMS A1 join ARTISTS A2 on A1.ARTIST_ID = A2.ID where A1.id = ?";
                PreparedStatement stmt1 = database.con.prepareStatement(sql);
                stmt1.setInt(1,ranking.get(i).album);
                ResultSet rs=stmt1.executeQuery();
                while(rs.next()) {
                    System.out.println("Am artistul " + rs.getString(1) + " cu rankul total " + ranking.get(i).rank);
                }
            }catch(Exception e){ System.out.println(e);}
        }

    }

    @Override
    public String toString() {
        return "Charts{" +
                "name='" + name + '\'' +
                ", charts=" + charts +
                '}';
    }
}
