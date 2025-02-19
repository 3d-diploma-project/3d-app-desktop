package org.cmps.tetrahedron.controller;

import javafx.stage.FileChooser;

import java.io.File;
import java.util.prefs.Preferences;

public class FileChooserController {
    private static final String LAST_USED_DIRECTORY_KEY = "last_used_directory";
    private final Preferences prefs;
    private static FileChooserController instance;

    private FileChooserController() {
        this.prefs = Preferences.userNodeForPackage(FileChooserController.class);
    }

    public static FileChooserController getInstance() {
        if (instance == null) {
            instance = new FileChooserController();
        }
        return instance;
    }

    public FileChooser createFileChooser() {
        FileChooser fileChooser = new FileChooser();
        String lastDir = prefs.get(LAST_USED_DIRECTORY_KEY, null);

        if (lastDir != null) {
            File lastDirFile = new File(lastDir);
            if (lastDirFile.exists() && lastDirFile.isDirectory()) {
                fileChooser.setInitialDirectory(lastDirFile);
            }
        }
        return fileChooser;
    }

    public void saveLastUsedDirectory(File file) {
        if (file != null && file.getParentFile() != null) {
            prefs.put(LAST_USED_DIRECTORY_KEY, file.getParent());
        }
    }
}
