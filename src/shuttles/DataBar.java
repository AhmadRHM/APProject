package shuttles;

import assets.Assets;
import main.Drawable;
import main.MainFrame;
import users.User;

import java.awt.*;
import java.awt.image.BufferedImage;

public class DataBar extends Drawable {
    private User user;
    private BufferedImage heart, rocket, meat;
    private MainFrame mainFrame;
    public DataBar(MainFrame mainFrame){
        this.mainFrame = mainFrame;
        user = mainFrame.getUser();
        Assets assets = mainFrame.getAssets();
        heart = assets.getHeart();
        rocket = assets.getRocketLogo();
        meat = assets.getMeat();
    }

    @Override
    public void draw(Graphics2D g) {
        int width = heart.getWidth(), height = heart.getHeight(), y = mainFrame.getMainPanel().getHeight() - 5 - height;
        g.setColor(Color.WHITE);
        g.setFont(new Font("Dialog", Font.BOLD, 25));
        g.drawImage(heart, null, 5, y);
        g.drawString(String.valueOf(user.getLives()), 5 + width + 5, y + height/2 + 5);
        g.drawImage(rocket, null , 5 + width + 5 + width + 5, y);
        g.drawString(String.valueOf(user.getRockets()), 5 + width + 5 + width + 5 + width + 5, y + height/2 + 5);
        g.drawImage(meat, null, 5 + width + 5 + width + 5 + width + 5 + width + 5, y);
        g.drawString(String.valueOf(user.getMoney()), 5 + width + 5 + width + 5 + width + 5 + width + 5 + width + 5, y + height/2 + 5);
//        System.out.println("drawing this shit");
    }
}
