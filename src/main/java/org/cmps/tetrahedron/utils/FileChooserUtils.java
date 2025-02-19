package org.cmps.tetrahedron.utils;

import javafx.stage.FileChooser;

import java.io.File;
import java.util.prefs.Preferences;

public class FileChooserUtils {
    private static final String LAST_USED_DIRECTORY_KEY = "last_used_directory";
    private static final Preferences prefs = Preferences.userNodeForPackage(FileChooserUtils.class);

    public static FileChooser createFileChooser() {
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

    public static void saveLastUsedDirectory(File file) {
        if (file != null && file.getParentFile() != null) {
            prefs.put(LAST_USED_DIRECTORY_KEY, file.getParent());
        }
    }
}
