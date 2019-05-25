package main;

import assets.Assets;
import chickenGroups.*;
import shuttles.DataBar;
import shuttles.Shuttle;
import shuttles.Tir;
import shuttles.HeatBar;
import users.MenuPanel;
import users.User;
import users.Users;
import users.UsersPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;

public class MainFrame extends JFrame {
    private int width = 1600, height = 1000;
    private Assets assets;
    private MainPanel mainPanel;
    private double backgroundSpeed;
    private Users users;
    private User user;
    private EsqFrame esqFrame;
    private Items items;

    MainFrame() {
        super();
        init();
    }

    private void init() {
        this.setTitle("Chicken Invaders AhmadRH Edition :D");

        this.setSize(new Dimension(width, height));

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setLocationRelativeTo(null);

        this.setResizable(false);

        this.assets = new Assets();

        mainPanel = new MainPanel(this);
        this.add(mainPanel);

        users = new Users();
//        users.addUser("ahmad", this);
//        users.addUser("rahim", this);
//        users.addUser("ali", this);

        initForUserSelection();

        backgroundSpeed = 1;

        items = new Items();
//        removeList = new ArrayList<>();
    }

    public void setBackgroundSpeed(double backgroundSpeed) {
        this.backgroundSpeed = backgroundSpeed;
    }

    public Assets getAssets() {
        return assets;
    }

    private Thread animationThread;

    private void initAnimationThread() {
        animationThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        mainPanel.setYOfBackground((int) (2.5 * backgroundSpeed));

                        if (mainPanel.isInGameMode()) {
                            //deleting bad items
                            for (Drawable drawable : items.getItems()) {
                                if ((drawable instanceof Tir) || (drawable instanceof Egg) || (drawable instanceof TirBooster) || (drawable instanceof TirChanger) || (drawable instanceof MaxTempBooster)){
                                    if (drawable.isOutOfPage())
                                        items.remove(drawable);
                                } else if (drawable instanceof ChickenGroup) {
                                    if (((ChickenGroup) drawable).getChickens().size() == 0)
                                        items.remove(drawable);
                                }
                            }
//                            System.out.println("killing bad items finished");
                            //Update
                            for (Drawable drawable : items.getItems()) {
                                drawable.update(0.005);
                            }
//                            System.out.println("updating finished");

                            //killing things! :D
                            Iterator<Drawable> chickenGroupIterator = items.getItems().iterator();
                            for (Drawable drawable : items.getItems()) {
                                if (drawable instanceof ChickenGroup) {

                                    ChickenGroup chickenGroup = (ChickenGroup) drawable;
                                    synchronized (chickenGroup.getChickens()) {
                                        Iterator<Chicken> chickenIterator = chickenGroup.getChickens().iterator();
                                        while (chickenIterator.hasNext()) {
                                            Chicken chicken = chickenIterator.next();
                                            boolean shouldTheChickenDie = false;
                                            //death of chickens
                                            for (Drawable drwbl : items.getItems()) {
                                                if (drwbl instanceof Tir) {
                                                    Tir tir = (Tir) drwbl;
//                                                    if (isIn(chicken.getX(), chicken.getY(), chicken.getSize().width, chicken.getSize().height, tir.getX(), tir.getY(), tir.getSize().width, tir.getSize().height)) {
                                                    if (conflict(chicken, tir)) {
                                                        chicken.reduceLives(tir.getPower());
                                                        items.remove(tir);
                                                        if (chicken.getLife() <= 0) {
                                                            chicken.killed();
                                                            shouldTheChickenDie = true;
//                                                            chickenIterator.remove();
//                                                            drawableIterator.remove();
                                                        }
                                                    }
                                                }
                                            }
                                            //death of shuttle
                                            Shuttle shuttle = mainPanel.getShuttle();
//                                            if(!shuttle.isDead() && isIn(shuttle.getX(), shuttle.getY(), shuttle.getSize().width, shuttle.getSize().height, chicken.getX(), chicken.getY(), chicken.getSize().width, chicken.getSize().height)){
                                            if (!shuttle.isDead() && conflict(shuttle, chicken)) {
//                                                chickenIterator.remove();
                                                shouldTheChickenDie = true;
                                                shuttle.dead();
                                                shuttleDied();
                                            }
                                            if (shouldTheChickenDie) {
                                                chickenIterator.remove();
                                                chickenGroup.removeChicken(chicken);
                                            }
                                        }
                                    }
                                } else if (drawable instanceof Egg) {
                                    Egg egg = (Egg) drawable;
                                    Shuttle shuttle = mainPanel.getShuttle();
//                                    if (!shuttle.isDead() && isIn(shuttle.getX(), shuttle.getY(), shuttle.getSize().width, shuttle.getSize().height, egg.getX(), egg.getY(), egg.getSize().width, egg.getSize().height)) {
                                    if (!shuttle.isDead() && conflict(shuttle, egg)) {
                                        shuttleDied();
                                        shuttle.dead();
                                        items.remove(drawable);
                                    }
                                }
                            }
//                            System.out.println("killing things finished");
                            //getting boosters
                            try {
                                Shuttle shuttle = mainPanel.getShuttle();
                                if (shuttle != null && !shuttle.isDead()) {
                                    for (Drawable drawable : items.getItems()) {
                                        if (drawable.hasImage() && conflict(shuttle, drawable)) {
                                            if (drawable instanceof MaxTempBooster) {
                                                shuttle.setMaxDegree(shuttle.getMaxDegree() + 5);
                                                items.remove(drawable);
                                            } else if (drawable instanceof TirBooster) {
                                                shuttle.setFirePower(shuttle.getFirePower() + 1);
                                                items.remove(drawable);
                                            } else if (drawable instanceof TirChanger) {
                                                if(shuttle.getFireType() == ((TirChanger)drawable).getType()){
                                                    shuttle.setFirePower(shuttle.getFirePower() + 1);
                                                }
                                                else
                                                {
                                                    shuttle.changeTir(((TirChanger) drawable).getType());
                                                }
                                                items.remove(drawable);
                                            }
                                        }
                                    }
                                }
                            }catch (Exception e){

                                e.printStackTrace();
                            }
                        }
                        mainPanel.revalidate();
                        mainPanel.repaint();
                        animationThread.sleep(5);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void startAnimation() {
        if (animationThread == null)
            initAnimationThread();
        animationThread.start();
    }

    public void stopAnimation() {
        if (animationThread != null) {
            animationThread.stop();
        }
    }

    public void userSelected(String user) {
        //TODO fill this shit :D
        System.out.println("User " + user + " is selected! :))");
    }

    public Users getUsers() {
        return users;
    }

    private void initForUserSelection() {
        mainPanel.setLayout(new BorderLayout());
        UsersPanel usersPanel = new UsersPanel(this);
        mainPanel.setUsersPanel(usersPanel);
        mainPanel.add(usersPanel, BorderLayout.NORTH);
        System.out.println("UsersPanel added to mainPanel");
        MenuPanel menuPanel = new MenuPanel(this);
        mainPanel.setMenuPanel(menuPanel);
        mainPanel.add(menuPanel, BorderLayout.SOUTH);
    }

    public void initForStartGameMenu() {
        mainPanel.clear();
        revalidate();
        repaint();

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.LINE_AXIS));
        main.MenuPanel menuPanel = new main.MenuPanel(this);
        mainPanel.setMainMenuPanel(menuPanel);
        mainPanel.add(Box.createGlue());
        mainPanel.add(mainPanel.getMainMenuPanel());
        mainPanel.add(Box.createGlue());
    }

    private boolean conflict(Drawable a, Drawable b){
        return  isIn(a.getX(), a.getY(), a.getSize().width, a.getSize().height, b.getX(), b.getY(), b.getSize().width, b.getSize().height);
    }
    private boolean isIn(double x1, double y1, double w1, double h1, double x2, double y2, double w2, double h2) {
        return !((x1 + w1 / 2 < x2 - w2 / 2) || (x2 + w2 / 2 < x1 - w1 / 2) || (y1 + h1 / 2 < y2 - h2 / 2) || (y2 + h2 / 2 < y1 - h1 / 2));
    }

    public void initForGame() {
        mainPanel.clear();
        revalidate();
        repaint();

        mainPanel.setInGameMode(true);
        mainPanel.setShuttle(new Shuttle(user.getShuttleType(), assets, this, user.getFireType(), user.getFirePower()));
        items.add(new HeatBar(mainPanel.getShuttle()));
        items.add(mainPanel.getShuttle());
        items.add(new DataBar(this));

//        Chicken c = new Chicken(500, 500, 20, 0, 1, assets.getChicken(1, 0), this);
//        items.add(c);
//        c.setNext(1000, 500 );

//        items.add(new RectangleGroup(40, 1, this));
//        items.add(new CircularGroup(36, 1, this));
//        items.add(new CircularGroup(20, 1, this));
//        items.add(new RotationalGroup(20, 1, this));
//        items.add(new SuicideGroup(15, 1, this));

        //mouse listeners
        mainPanel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                mainPanel.getShuttle().fire();
                mouseMoved(e);
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                mainPanel.getShuttle().setX(e.getX());
                mainPanel.getShuttle().setY(e.getY());
            }
        });
        mainPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
//                mainPanel.getShuttle().fire();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                mainPanel.getShuttle().fire();
            }
        });
        //key listener
        mainPanel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyChar() == 'a' || e.getKeyCode() == 37)
                    mainPanel.getShuttle().toLeft();
                if (e.getKeyChar() == 's' || e.getKeyCode() == 40)
                    mainPanel.getShuttle().toDown();
                if (e.getKeyChar() == 'd' || e.getKeyCode() == 39)
                    mainPanel.getShuttle().toRight();
                if (e.getKeyChar() == 'w' || e.getKeyCode() == 38)
                    mainPanel.getShuttle().toUp();
            }

            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
            }
        });
        mainPanel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyChar() == ' ')
                    mainPanel.getShuttle().fire();
            }
        });
    }

    public MainPanel getMainPanel() {
        return mainPanel;
    }

    public EsqFrame getEsqFrame() {
        return esqFrame;
    }

    public void setEsqFrame(EsqFrame esqFrame) {
        this.esqFrame = esqFrame;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Items getItems() {
        return items;
    }

    public void save() {
        System.out.println("saving");
        users.save();
    }

    public void rocketBoomed() {
        //TODO
        System.out.println("rocket Boomeddd...!! :D");
    }

    public void shuttleDied() {
        user.setLives(user.getLives() - 1);
        if (user.getLives() == 0)
            endGame();
    }

    public void endGame() {
        //TODO
        System.out.println("game ended");
    }
}
