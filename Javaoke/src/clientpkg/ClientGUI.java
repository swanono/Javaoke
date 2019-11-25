package clientpkg;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import common.Constants.GUI;

public class ClientGUI {

    JFrame window;
    JLabel lyricsTextLabel;
    ImageBackground backImage;

    public ClientGUI(String musicTitle) {
        window = new JFrame();
        window.setTitle("Javaoke - " + musicTitle);
        window.setSize(GUI.WINDOW_W, GUI.WINDOW_H);
        window.setLocationRelativeTo(null);
        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setBackground(Color.black);
        window.setVisible(true);

        backImage = new ImageBackground(GUI.BACKGROUND_PATH);
        
        lyricsTextLabel = new JLabel(musicTitle);
        lyricsTextLabel.setFont(new Font(GUI.FONT_NAME, 0, GUI.FONT_SIZE));
        lyricsTextLabel.setForeground(Color.white);
        lyricsTextLabel.setVerticalAlignment(JLabel.CENTER);

        window.setContentPane(backImage);
        window.add(lyricsTextLabel);

        draw();
    }

    public void updateText(String newText) {
        lyricsTextLabel.setText(newText);
        draw();
    }

    public void draw() {
        window.repaint();
        SwingUtilities.updateComponentTreeUI(window);
    }

}