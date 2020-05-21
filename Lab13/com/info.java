package com;

import sun.security.util.ArrayUtil;

import java.lang.reflect.Array;
import java.text.DateFormatSymbols;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Currency;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

public class info {
    public info() {
        String baseName = "res.Messages";
        ResourceBundle messages = ResourceBundle.getBundle(baseName, Locale.getDefault());

        System.out.println(messages.getString("tag"));

        Scanner keyboard = new Scanner(System.in);
        String input = keyboard.next();
        Locale newLocale;
        if(input.equals("default"))
            newLocale = Locale.getDefault();
        else
            newLocale =  new Locale(input.substring(0,2),input.substring(3));

        String pattern = messages.getString("info");
        Object[] arguments = {newLocale.getDisplayCountry()};
        String result = new MessageFormat(pattern).format(arguments);
        System.out.println(result);

        System.out.print(messages.getString("country") + ":  ");
        System.out.println(newLocale.getDisplayCountry() + " ("+ newLocale.getDisplayCountry(newLocale) + ")");

        System.out.print(messages.getString("language") + ":  ");
        System.out.println(newLocale.getDisplayLanguage() + " ("+ newLocale.getDisplayLanguage(newLocale) + ")");

        System.out.print(messages.getString("currency") + ":  ");
        Currency c3 = Currency.getInstance(Currency.getInstance(newLocale).toString());
        System.out.print(Currency.getInstance(newLocale));
        System.out.println(" (" + c3.getDisplayName() + ")");

        Locale defaultLocale = Locale.getDefault();
        Locale.setDefault(newLocale);
        System.out.print(messages.getString("week_days") + ":  ");
        for (int i = 0; i < DateFormatSymbols.getInstance().getWeekdays().length; i++) {
            if(i+1 == DateFormatSymbols.getInstance().getWeekdays().length)
                System.out.print(DateFormatSymbols.getInstance().getWeekdays()[i]);
            else if(!DateFormatSymbols.getInstance().getWeekdays()[i].equals(""))
                System.out.print(DateFormatSymbols.getInstance().getWeekdays()[i] + ", ");
        }
        System.out.println("");

        System.out.print(messages.getString("months") + ":  ");
        for (int i = 0; i < 12; i++) {
            if(i == 11)
                System.out.print(DateFormatSymbols.getInstance().getMonths()[i]);
            else
                System.out.print(DateFormatSymbols.getInstance().getMonths()[i] + ", ");
        }
        System.out.println("");

        System.out.print(messages.getString("today") + ":  ");
        LocalDateTime today = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd");
        System.out.print(  today.format(formatter) + ", " + today.getYear() );
        Locale.setDefault(defaultLocale);

        formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).withLocale(Locale.getDefault());
        System.out.println( " (" + today.format(formatter) + ")");

    }
}
