package org.isma.dirmanager;

import org.isma.utils.io.FileUtils;

import java.util.prefs.Preferences;
public class PreferencesManager {

    public String get(String key, String defaultValue) {
        Preferences preferences = Preferences.userNodeForPackage(getClass());
        String value = preferences.get(key, defaultValue);
        value = FileUtils.suppressBackSlashes(value);
        System.out.printf("loading preference (%s, default=%s) = %s\n", key, defaultValue, value);
        return value;
    }


    public void put(String key, String value) {
        Preferences preferences = Preferences.userNodeForPackage(getClass());
        preferences.put(key, value);
        System.out.printf("saving preference (%s, %s)\n", key, value);
    }


    public void remove(String key) {
        Preferences preferences = Preferences.userNodeForPackage(getClass());
        preferences.remove(key);
    }
}
