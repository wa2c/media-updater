package com.wa2c.mediaupdater;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;

public class Settings {

    public int windowX;
    public int windowY;
    public int windowW;
    public int windowH;


    // static

    private static File SETTINGS_DIR = new File(System.getenv("APPDATA"), Resource.get("app.dir"));
    private static File SETTINGS_FILE = new File(SETTINGS_DIR, "settings.pref");

    private static final Gson gson;
    static {
        SETTINGS_DIR.mkdirs();
        gson = new GsonBuilder().setPrettyPrinting().create();
    }

    static Settings read() {
        try (BufferedReader br = new BufferedReader(new FileReader(SETTINGS_FILE))) {
            return gson.fromJson(br, Settings.class);
        } catch(IOException e){
            Logger.e(e);
            return new Settings();
        }
    }


    static void write(Settings settings) {
        String json = gson.toJson(settings);
        try (FileWriter fw = new FileWriter(SETTINGS_FILE, false)) {
            fw.write(json);
        } catch (IOException e) {
            Logger.e(e);
        }
    }

}
