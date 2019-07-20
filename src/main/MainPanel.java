package main;

import assets.Assets;
import shuttles.DataBar;
import shuttles.HeatBar;
import shuttles.Shuttle;
import users.MenuPanel;
import users.Users;
import users.UsersPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

public class MainPanel extends JPanel {
    private MainFrame mainFrame;
    private Assets assets;
    private int yOfBackground = 2000;
    private UsersPanel usersPanel;
    private MenuPanel menuPanel;
    private main.MenuPanel mainMenuPanel;
    private boolean inGameMode;
    private Shuttle shuttle;
    MainPanel(MainFrame mainFrame){
        super();
        this.mainFrame = mainFrame;
        inGameMode = false;
        init();
    }
    private void init(){
        assets = mainFrame.getAssets();
        this.setPreferredSize(new Dimension(mainFrame.getWidth(), mainFrame.getHeight()));
        this.setFocusable(true);
        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
//                System.out.println(e.getKeyChar());
            }

            @Override
            public void keyPressed(KeyEvent e) {
                //38->up  40->down 10->enter 32->space
//                System.out.println(e.getKeyCode());
                int code = e.getKeyCode();
                if(!inGameMode) {
                    if (code == 38)
                        usersPanel.focusDown();
                    if (code == 40)
                        usersPanel.focusUp();
                    if (code == 10 || code == 32)
                        usersPanel.setSelected();
                }
                if(inGameMode) {
                    if (code == 27) {
                        inGameMode = false;
                        mainFrame.getEsqFrame().initForInGameMenu();
                        int x = MouseInfo.getPointerInfo().getLocation().x, y = MouseInfo.getPointerInfo().getLocation().y;
                        mainFrame.setLastMouseX(x);
                        mainFrame.setLastMouseY(y);
                        mainFrame.getEsqFrame().setVisible(true);
                    }
                    if(code == 10){
//                        System.out.println("dooo");
                        shuttle.shootRocket();
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

//        Toolkit toolkit = Toolkit.getDefaultToolkit();
//        BufferedImage img = null;
//        try {
//            img = ImageIO.read(new File("assets/mouse.png"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        Cursor c = toolkit.createCustomCursor(img, new Point(getX(), getY()), "image");
//        setCursor(c);
    }
    public MainFrame getMainFrame() {
        return mainFrame;
    }

    public void setYOfBackground(int deltaYOfBackground) {
        this.yOfBackground -= deltaYOfBackground;
        int height = assets.getBackgroundImage().getHeight();
        if(yOfBackground<=0)
            yOfBackground += 2000;
    }

    @Override
    public void paintComponent(Graphics G){
        super.paintComponent(G);
        Graphics2D g2 = (Graphics2D)G;
        g2.drawImage(assets.getBackgroundImage(), 0, -1*yOfBackground, this);
//        if(inGameMode){
        if(!mainFrame.isMultiplayer() || mainFrame.getId() == 0) {
            for (Drawable drawable : mainFrame.getItems().getItems())
                if (!(drawable instanceof DataBar || drawable instanceof HeatBar))
                    drawable.draw(g2);
            for (Drawable drawable : mainFrame.getItems().getItems())
                if (drawable instanceof DataBar || drawable instanceof HeatBar)
                    drawable.draw(g2);
//        }
        }else{
            ArrayList<String> list;


            for(String st : (ArrayList<String>)mainFrame.getTirs().clone()){
                list = tajzie(st);
                int x = Integer.valueOf(list.get(0));
                int y = Integer.valueOf(list.get(1));
                int type = Integer.valueOf(list.get(2));
                draw(g2, assets.getFire(type), x, y);
            }

            for(String st : (ArrayList<String>)mainFrame.getChickens().clone()){
                list = tajzie(st);
                int x = Integer.valueOf(list.get(0));
                int y = Integer.valueOf(list.get(1));
                int type = Integer.valueOf(list.get(2));
                int frame = Integer.valueOf(list.get(3));
                if (frame >= 7)
                    frame = 12 - frame;
                draw(g2, assets.getChicken(type, frame), x, y);
            }

            for(String st : (ArrayList<String>)mainFrame.getBigegg().clone()){
                list = tajzie(st);
                int x = Integer.valueOf(list.get(0));
                int y = Integer.valueOf(list.get(1));
                draw(g2, assets.getBigEgg(), x, y);
            }

            for(String st : (ArrayList<String>)mainFrame.getSpaceships().clone()){
                list = tajzie(st);
                int x = Integer.valueOf(list.get(0));
                int y = Integer.valueOf(list.get(1));
                int type = Integer.valueOf(list.get(2));
                draw(g2, assets.getShuttle(type), x, y);
            }

            for(String st : (ArrayList<String>)mainFrame.getEggs().clone()){
                list = tajzie(st);
                int x = Integer.valueOf(list.get(0));
                int y = Integer.valueOf(list.get(1));
                draw(g2, assets.getEgg(), x, y);
            }

            for(String st : (ArrayList<String>)mainFrame.getMaxtempBoosters().clone()){
                list = tajzie(st);
                int x = Integer.valueOf(list.get(0));
                int y = Integer.valueOf(list.get(1));
                draw(g2, assets.getMaxTempBooster(), x, y);
            }

            for(String st : (ArrayList<String>)mainFrame.getTirBoosters().clone()){
                list = tajzie(st);
                int x = Integer.valueOf(list.get(0));
                int y = Integer.valueOf(list.get(1));
                draw(g2, assets.getTirBooster(), x, y);
            }

            for(String st : (ArrayList<String>)mainFrame.getTirChangers().clone()){
                list = tajzie(st);
                int x = Integer.valueOf(list.get(0));
                int y = Integer.valueOf(list.get(1));
                int type = Integer.valueOf(list.get(2));
                draw(g2, assets.getTirChanger(type), x, y);
            }

            for(String st : (ArrayList<String>)mainFrame.getCoins().clone()){
                list = tajzie(st);
                int x = Integer.valueOf(list.get(0));
                int y = Integer.valueOf(list.get(1));
                draw(g2, assets.getCoin(), x, y);
            }

            for(String st : (ArrayList<String>)mainFrame.getRockets().clone()){
                list = tajzie(st);
                int x = Integer.valueOf(list.get(0));
                int y = Integer.valueOf(list.get(1));
                draw(g2, assets.getRocket(), x, y);
            }

            for(String st:(ArrayList<String>)mainFrame.getIngameTextes().clone()) {
                list = tajzie(st);
                int x = Integer.valueOf(list.get(0));
                int y = Integer.valueOf(list.get(1));
                String text = list.get(2);
                g2.setFont(new Font("Diwani", Font.PLAIN, 100));
                g2.setColor(Color.WHITE);
                int width = G.getFontMetrics().stringWidth(text);
                g2.drawString(text, (int)(x - width/2), (int)(y - 5));
            }

            for(String st:(ArrayList<String>)mainFrame.getDatabar().clone()){
                list = tajzie(st);
//                System.out.println("yuhahaha" + st);
                int lives = Integer.valueOf(list.get(0));
                int rockets = Integer.valueOf(list.get(1));
                int money = Integer.valueOf(list.get(2));
                BufferedImage heart, rocket, meat, coin;
                heart = assets.getHeart();
                rocket = assets.getRocketLogo();
                coin = assets.getCoinLogo();

                int width = heart.getWidth(), height = heart.getHeight(), y = mainFrame.getMainPanel().getHeight() - 5 - height;
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Dialog", Font.BOLD, 25));
                g2.drawImage(heart, null, 5, y);
                g2.drawString(String.valueOf(lives), 5 + width + 5, y + height/2 + 5);
                g2.drawImage(rocket, null , 5 + width + 5 + width + 5, y);
                g2.drawString(String.valueOf(rockets), 5 + width + 5 + width + 5 + width + 5, y + height/2 + 5);
                g2.drawImage(coin, null,    5 + width + 5 + width + 5 + width + 5 + width + 5, y - 10);
                g2.drawString(String.valueOf(money),   5 + width + 5 + width + 5 + width + 5 + width + 5 + width + 5, y + height/2 + 5);
            }

            for(String st:(ArrayList<String>)mainFrame.getHeatbar().clone()){
                list = tajzie(st);
                int tmp = Integer.valueOf(list.get(0));
                int score = Integer.valueOf(list.get(1));
                g2.setColor(Color.WHITE);
                g2.drawRect(3, 3, (int)(40 * (8) - 10 + 4), (int)(40 + 4));
                for(int i=0; i<tmp; i++){
                    g2.setColor(new Color((int)(255*i/40) , (int)(255 - 255*i/40) , 0));
                    g2.fillRect((int)(5 + i*(8)), 5, (int)5, (int)40);
                }
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Dialog", Font.BOLD, 25));
                g2.drawString("Score: "+ String.valueOf(score), (int)(3 + 40 * (8) - 10 + 4 + 10),(int)(3 + 40/2 + 8) );

            }

        }
    }

    private ArrayList<String> tajzie(String st){
        String tmp = "";
        ArrayList ans = new ArrayList();
        for(int i=0; i<st.length(); i++)
            if(st.charAt(i) != ' ')
                tmp += st.charAt(i);
            else{
                ans.add(tmp);
                tmp = "";
            }
        if(tmp != "")
            ans.add(tmp);
        return ans;
    }
    private void draw(Graphics2D G, BufferedImage image, int x, int y){
        G.drawImage(image, (int)(x - image.getWidth()/2), (int)(y - image.getHeight()/2), null);
    }

    public UsersPanel getUsersPanel() {
        return usersPanel;
    }

    public void setUsersPanel(UsersPanel usersPanel) {
        this.usersPanel = usersPanel;
    }

    public void setMenuPanel(MenuPanel menuPanel) {
        this.menuPanel = menuPanel;
    }
    public void clear(){
        this.removeAll();
    }
    public MenuPanel getMenuPanel() {
        return menuPanel;
    }

    public main.MenuPanel getMainMenuPanel() {
        return mainMenuPanel;
    }

    public void setMainMenuPanel(main.MenuPanel mainMenuPanel) {
        this.mainMenuPanel = mainMenuPanel;
    }

    public void setShuttle(Shuttle shuttle) {
        this.shuttle = shuttle;
    }

    public void setInGameMode(boolean inGameMode) {
        this.inGameMode = inGameMode;
    }

    public boolean isInGameMode(){
        return inGameMode;
    }

    public Shuttle getShuttle(){
        return  shuttle;

    }
}
