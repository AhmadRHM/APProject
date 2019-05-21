package shuttles;

import main.Drawable;

import java.awt.*;

public class HeatBar implements Drawable {
    private Shuttle shuttle;
    private int tmp;
    private final int width = 20, height = 40;
    public HeatBar(Shuttle shuttle){
        this.shuttle = shuttle;
    }

    @Override
    public void update(double time) {
        tmp = (int)(shuttle.getTemperature()/10);
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.drawRect(3, 3, 10 * (width + 10) - 10 + 4, height + 4);
        for(int i=0; i<tmp; i++){
            g.setColor(new Color(255 , 255 - 25*i , 255 - 25 * i));
            g.fillRect(5 + i*(width + 10), 5, width, height);
        }
    }
}
