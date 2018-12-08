package com.wa2c.mediaupdater;

import javax.swing.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Main Form
 */
public class MainForm extends JFrame {
    private JPanel mainContentPanel;
    private JTable fileTable;

    public MainForm() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle(Resource.get("app,title"));
        setContentPane(mainContentPanel);
        pack();
        setVisible(true);

        // settings
        this.setBounds(Program.Pref.windowX, Program.Pref.windowY, Program.Pref.windowW, Program.Pref.windowH);
        addWindowListener(windowAdatpter);

        initializeUI();
    }

    private void initializeUI() {
        mainContentPanel.setTransferHandler(new DropFileHandler());
    }

    private boolean searchFiles(File file) {
        if (file == null || !file.exists())
            return false;

        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                if (searchFiles(f))
                    break;
            }
            return false;
        }

        int point = file.getName().lastIndexOf(".");
        if (point < 1) {
            return false;
        }
        String ext = file.getName().substring(point + 1);
        if (!ext.toLowerCase().equals("mp3") && !ext.toLowerCase().equals("m4a") && !ext.toLowerCase().equals("ogg"))
            return false;

        updateFolder(file.getParentFile(), file.lastModified());
        return true;
    }

    private void updateFolder(File file, long time) {
        if (file == null || !file.exists())
            return;

        file.setLastModified(time);
        file.setReadOnly();

        Path path = Paths.get(file.getAbsolutePath());
        try {
            Files.setAttribute(path, "dos:readonly", true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                updateFolder(f, time);
            }
        }
    }



    private WindowAdapter windowAdatpter = new WindowAdapter() {
        public void windowClosing(WindowEvent e) {
            Program.Pref.windowX = MainForm.this.getBounds().x;
            Program.Pref.windowY = MainForm.this.getBounds().y;
            Program.Pref.windowW = MainForm.this.getBounds().width;
            Program.Pref.windowH = MainForm.this.getBounds().height;

            Settings.writeSettings(Program.Pref);
        }
    };


    /**
     * Drag & drop handler.
     */
    private class DropFileHandler extends TransferHandler  {

        private static final long serialVersionUID = 1L;

        /**
         * ドロップされたものを受け取るか判断 (ファイルのときだけ受け取る)
         */
        @Override
        public boolean canImport(TransferSupport support) {
            if (!support.isDrop()) {
                // ドロップ操作でない場合は受け取らない
                return false;
            }

            if (!support.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                // ドロップされたのがファイルでない場合は受け取らない
                return false;
            }

            return true;
        }



        /**
         * ドロップされたファイルを受け取る
         */
        @Override
        public boolean importData(TransferSupport support) {
            // 受け取っていいものか確認する
            if (!canImport(support)) {
                return false;
            }

            // ドロップ処理
            Transferable t = support.getTransferable();
            try {
                // ファイルを受け取る
                @SuppressWarnings("unchecked")
                List<File> files = (List<File>) t.getTransferData(DataFlavor.javaFileListFlavor);
                for (File f : files) {
                    searchFiles(f);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return true;
        }
    }
}
