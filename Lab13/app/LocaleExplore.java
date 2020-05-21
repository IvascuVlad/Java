package app;
import com.displayLocales;
import com.info;
import com.setLocale;

import java.io.FileReader;
import java.io.IOException;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Scanner;

public class LocaleExplore {
    public static void displayMessages(Locale locale, String comandaDorita) {
        String baseName = "res.Messages";
        ResourceBundle messages = ResourceBundle.getBundle(baseName, locale);
        System.out.println(messages.getString(comandaDorita));
    }

    public static void main(String[] args) {
        boolean stop = true;
        String command;
        while ( stop ){
            displayMessages(Locale.getDefault(),"available");
            System.out.println("DisplayLocales, SetLocale, Info, Stop");
            displayMessages(Locale.getDefault(),"prompt");
            Scanner keyboard = new Scanner(System.in);
            command = keyboard.next();
            if(command.equals("DisplayLocales")) {
                new displayLocales();
            }else if(command.equals("SetLocale")) {
                new setLocale();
            }else if(command.equals("Info")) {
                new info();
            }else if(command.equals("Stop")){
                displayMessages(Locale.getDefault(),"bye");
                stop = false;
            }else{
                displayMessages(Locale.getDefault(),"invalid");
            }
            System.out.println("");
        }
    }
}
