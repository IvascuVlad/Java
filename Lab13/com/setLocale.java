package com;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

public class setLocale {
    public setLocale() {
        String baseName = "res.Messages";
        ResourceBundle messages = ResourceBundle.getBundle(baseName, Locale.getDefault());
        System.out.println(messages.getString("locale.newLocale"));

        Scanner keyboard = new Scanner(System.in);
        String newLocale = keyboard.next();
        Locale.setDefault(new Locale(newLocale.substring(0,2),newLocale.substring(3)));

        String pattern = messages.getString("locale.set");
        Object[] arguments = {Locale.getDefault().getDisplayCountry()};
        String result = new MessageFormat(pattern).format(arguments);
        System.out.println(result);
    }
}
