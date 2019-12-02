package clientpkg;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
        //JPanel panel = new JPanel();
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
        //window.setLayout(null);
        window.setContentPane(backImage);
        
        
        
        try {
            bufferedTypeImage = ImageIO.read(new File(GUI.IMG_PATH + "ready_png.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        imageType = new JLabel(new ImageIcon(bufferedTypeImage));
        
        Box vBox = Box.createVerticalBox();
        Box hBox1 = Box.createHorizontalBox();
        hBox1.add(lyricsTextLabel);
        Box hBox2 = Box.createHorizontalBox();
        hBox2.add(imageType);

        

        vBox.add(hBox1);
        vBox.add(hBox2);

        window.getContentPane().add(vBox);


        
        draw();
    }
    
    public void updateImageType(String type){
        try {
            bufferedTypeImage = ImageIO.read(new File(GUI.IMG_PATH + type + "_png.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        imageType.setIcon(new ImageIcon(bufferedTypeImage));
        draw();
    }

    public void updateText(String newText, String type, int id) {
        String textType = (type == LyricType.NORMAL.name() ? "" : System.lineSeparator() + "**" + type + "**");
        lyricsTextLabel.setText(newText + textType);
        lyricsTextLabel.setForeground(idToColor(id));
        draw();
    }


    public void draw() {
        window.revalidate();
        window.repaint();
        SwingUtilities.updateComponentTreeUI(window);
    }

    private Color idToColor(int id) {
        if(id == 0)
            return Color.orange;
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
