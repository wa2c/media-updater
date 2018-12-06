package com.wa2c.mediaupdater;

import javax.swing.*;
import java.awt.*;

/**
 * Created by wa2c on 2016/02/24.
 */
public class Program {

    public static Settings Pref;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    MainForm f = new MainForm();
                    f.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
