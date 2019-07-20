package chickenGroups;

import main.Drawable;
import main.Main;
import main.MainFrame;

import java.awt.*;

public class InGameText extends Drawable {
    private String text;
    public InGameText(String st){
        this.x = Main.getMainFrame().getMainPanel().getWidth()/2;
        this.y = Main.getMainFrame().getMainPanel().getHeight()/2;

        this.vx = 0;
        this.vy = 0;

        this.text = st;
    }
    @Override
    public String toString(){
        return super.toString() + " " + text;
    }
    public InGameText(String st, double x, double y){
        this(st);
        this.x = x;
        this.y = y;
    }
    @Override
    public void draw(Graphics2D g){
        g.setFont(new Font("Diwani", Font.PLAIN, 100));
        g.setColor(Color.WHITE);
        int width = ((Graphics)g).getFontMetrics().stringWidth(text);
        g.drawString(text, (int)(x - width/2), (int)(y - 5));

    }
}
