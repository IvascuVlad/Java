package com;

import java.util.Locale;
import java.util.ResourceBundle;

public class displayLocales {
    public displayLocales() {
        String baseName = "res.Messages";
        ResourceBundle messages = ResourceBundle.getBundle(baseName, Locale.getDefault());
        System.out.println(messages.getString("locales"));

        Locale available[] = Locale.getAvailableLocales();
        for(Locale locale : available) {
            System.out.println(locale.getDisplayCountry() + "\t" +
                    locale.getDisplayLanguage(locale));
        }

    }
}
