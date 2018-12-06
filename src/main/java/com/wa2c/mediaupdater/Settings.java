package com.wa2c.mediaupdater;

import com.google.gson.Gson;

import java.io.*;

public class Settings {

    public int windowX;
    public int windowY;
    public int windowW;
    public int windowH;





    private static File SETTINGS_FILE = new File(System.getProperty("user.home"), "settings.pref");

    public static Settings readSettings() {
        try (BufferedReader br = new BufferedReader(new FileReader(SETTINGS_FILE))) {
            Gson gson = new Gson();
            return gson.fromJson(br, Settings.class);
        } catch(IOException e){
            return new Settings();
        }
    }


    public static void writeSettings(Settings settings) {
        Gson gson = new Gson();
        String json = gson.toJson(settings);

        try (FileWriter fw = new FileWriter(SETTINGS_FILE, false)) {
            fw.write(json);
        } catch (IOException e) {
        }
    }

}
