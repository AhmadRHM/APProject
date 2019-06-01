package shuttles;

import main.Drawable;
import main.Main;
import main.MainFrame;

import java.awt.*;

public class HeatBar extends Drawable {
    private Shuttle shuttle;
    private MainFrame mainFrame;
    private int tmp;
    private final double height = 40, cnt=40, width = 200/cnt, space = 3;
    public HeatBar(MainFrame mainFrame)
    {
        this.mainFrame = mainFrame;
        this.shuttle = mainFrame.getMainPanel().getShuttle();
    }

    @Override
    public void update(double time) {
        tmp = (int)(shuttle.getTemperature() * cnt / shuttle.getMaxDegree());
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.drawRect(3, 3, (int)(cnt * (width + space) - 10 + 4), (int)(height + 4));
        for(int i=0; i<tmp; i++){
            g.setColor(new Color((int)(255*i/cnt) , (int)(255 - 255*i/cnt) , 0));
            g.fillRect((int)(5 + i*(width + space)), 5, (int)width, (int)height);
        }
        g.setColor(Color.WHITE);
        g.setFont(new Font("Dialog", Font.BOLD, 25));
        g.drawString("Score: "+ String.valueOf(mainFrame.getUser().getScore()), (int)(3 + cnt * (width + space) - 10 + 4 + 10),(int)(3 + height/2 + 8) );

    }
}
