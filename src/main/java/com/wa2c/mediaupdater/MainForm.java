package com.wa2c.mediaupdater;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ResourceBundle;

/**
 * Main Form
 */
public class MainForm extends JFrame {
    private JPanel mainContentPanel;

    public MainForm() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle(ResourceBundle.getBundle("resource").getString("title.App"));
        setContentPane(mainContentPanel);
        pack();
        setVisible(true);

        // settings
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                Settings.writeSettings(Program.Pref);
            }
        });
        Program.Pref = Settings.readSettings();
    }

}
