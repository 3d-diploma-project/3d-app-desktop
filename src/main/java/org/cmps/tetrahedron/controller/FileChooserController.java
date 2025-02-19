package org.cmps.tetrahedron.controller;

import javafx.stage.FileChooser;
import lombok.Getter;

import java.io.File;
import java.util.prefs.Preferences;

public class FileChooserController {
    private static final String LAST_USED_DIRECTORY_KEY = "last_used_directory";
    private final Preferences prefs;

    @Getter
    private static FileChooserController instance = new FileChooserController();

    private FileChooserController() {
        this.prefs = Preferences.userNodeForPackage(FileChooserController.class);
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
