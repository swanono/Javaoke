package clientpkg;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import java.awt.Graphics;
import javax.imageio.ImageIO;
import java.awt.Toolkit;
import java.awt.Image;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import common.LyricType;
import common.Constants.GUI;

public class ClientGUI {

    JFrame window;
    JLabel lyricsTextLabel;
    ImageBackground backImage;
    JLabel imageType;
    BufferedImage bufferedTypeImage;

    public ClientGUI(String musicTitle) {
        window = new JFrame();
        window.setTitle("Javaoke - " + musicTitle);
        window.setSize(GUI.WINDOW_W, GUI.WINDOW_H);
        window.setLocationRelativeTo(null);
        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // window.setBackground(Color.black);
        window.setVisible(true);

        
        
        //backImage = new ImageBackground(GUI.BACKGROUND_PATH);
        backImage = new ImageBackground(GUI.IMG_PATH + "javaoke_background.png");

        lyricsTextLabel = new JLabel(musicTitle);
        lyricsTextLabel.setFont(new Font(GUI.FONT_NAME, 0, GUI.FONT_SIZE));
        lyricsTextLabel.setForeground(Color.white);
        lyricsTextLabel.setVerticalAlignment(JLabel.CENTER);

        window.setContentPane(backImage);

        /*Icon imgIcon = new ImageIcon(this.getClass().getResource("/chuchoter_gif.gif"));
        //Icon icon = new ImageIcon(url);
        JLabel label = new JLabel(imgIcon);
        window.setUndecorated(true);
        window.getContentPane().add(label);
        window.pack();


        /*
        try {
            bufferedTypeImage = ImageIO.read(new File(GUI.IMG_PATH + "normal_gif.gif"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        imageType = new JLabel(new ImageIcon(bufferedTypeImage));
        window.add(imageType);*/
        //window.add(new ImagePanel());

        window.add(lyricsTextLabel);
        draw();
    }
    /*
    public void updateImageType(String type){
        try {
            bufferedTypeImage = ImageIO.read(new File(GUI.IMG_PATH + type + ".gif"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        imageType.setIcon(new ImageIcon(GUI.IMG_PATH + type + ".gif"));
        draw();
    }*/

    public void updateText(String newText, String type, int id) {
        String textType = (type == LyricType.NORMAL.name() ? "" : System.lineSeparator() + "**" + type + "**");
        lyricsTextLabel.setText(newText + textType);
        lyricsTextLabel.setForeground(idToColor(id));
        draw();
    }


    public void draw() {
        window.repaint();
        SwingUtilities.updateComponentTreeUI(window);
    }

    private Color idToColor(int id) {
        if(id == 0)
            return Color.white;
        else {
            int div = (id - 1)/6;
            int rest = (id - 1)%6;
            int[] fract = getFract(div + 1);
            int r = 255*(rest == 0 || rest == 3 || rest == 4 ? 1 : 0)*fract[0]/fract[1];
            int g = 255*(rest == 1 || rest == 3 || rest == 5 ? 1 : 0)*fract[0]/fract[1];
            int b = 255*(rest == 2 || rest == 4 || rest == 5 ? 1 : 0)*fract[0]/fract[1];
            return new Color(r, g, b);
        }
    }

    private int[] getFract(int idDivided) {
        int[] res = new int[2];
        res[0] = 0;
        res[1] = 1;

        for(int n = 1; n < idDivided; n++) {
            res[0]++;
            if(res[0] == res[1]) {
                res[0] = 1;
                res[1]++;
            }
            while(gcd(res[0], res[1]) != 1 && res[0] != res[1]) {
                res[0]++;
            }
        }
        if(res[0] == 0)
            res[0] = 1;

        return res;
    }

    private int gcd(int a, int b) { return (b==0 ? a : gcd(b, a%b)); }

}
/*
class ImagePanel extends JPanel {

    Image image;
  
    public ImagePanel() {
      image = Toolkit.getDefaultToolkit().createImage(GUI.IMG_PATH + "normal.gif");
    }
  
    @Override
    public void paintComponent(Graphics g) {
      super.paintComponent(g);
      if (image != null) {
        g.drawImage(image, 0, 0, this);
      }
    }
  
  }*/