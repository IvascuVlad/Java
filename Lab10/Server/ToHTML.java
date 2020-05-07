import com.jcraft.jsch.*;

import javax.swing.text.html.HTMLDocument;
import java.io.*;
import java.util.Properties;

public class ToHTML {
    Game game;

    public ToHTML(Game game) {
        this.game = game;
        try {
            File myObj = new File("ToHTML.HTML");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }

            FileWriter myWriter = new FileWriter("ToHTML.HTML");
            myWriter.write("<!DOCTYPE html><html><body><ol>");


            /*for (int[] ints : game.board.getBoard()) {
                String table = "<li>";
                for (int anInt : ints) {
                    if (anInt == game.player1.getPlayerPort()) {
                        table += 1;
                        table += " ";
                    }
                    else if (anInt == game.player2.getPlayerPort()){
                        table += 2;
                        table += " ";
                    }
                    else{
                        table += 0;
                        table += " ";
                    }
                }
                table += "</li>";
                myWriter.write(table);
            }*/
            myWriter.write("</ol></body></html>");
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        try{
            String user = "ivascu";
            String pass = "vlad";
            Properties config = new Properties();
            config.put("StrictHostChecking","no");
            String host = "127.0.0.1";

            JSch jSch = new JSch();
            Session session = jSch.getSession(user,host);
            session.setPassword(pass);
            session.setConfig(config);
            session.connect();
            ChannelSftp channelSftp = (ChannelSftp) session.openChannel("sftp");
            channelSftp.connect();
            channelSftp.get("Java\\test.txt");
            channelSftp.put("C:\\Users\\ivasc\\Desktop\\Anul_II\\semestrul 2\\Java\\lab10\\ToHTML.html","Java\\ToHTML.html");
            System.out.println("Session connected: " + session.isConnected());
            channelSftp.disconnect();
            session.disconnect();
        } catch (JSchException | SftpException e) {
            e.printStackTrace();
        }
    }
}
