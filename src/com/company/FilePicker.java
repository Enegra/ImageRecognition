package com.company;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * Created by agnie on 10/7/2016.
 */
class FilePicker {

    File getFilePath() {
        JFrame frame = new JFrame();
        FileDialog fileDialog = new FileDialog(frame, "Import picture", FileDialog.LOAD);
        fileDialog.setVisible(true);
        frame.setVisible(true);
        String directory = fileDialog.getDirectory();
        String file = fileDialog.getFile();
        String fileDirectory = directory + file;
        File imageFile = new File(fileDirectory);
        frame.setVisible(false);
        frame.dispose();
        return imageFile;
    }

}
