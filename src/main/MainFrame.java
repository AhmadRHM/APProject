package main;

import assets.Assets;
import chickenGroups.*;
import overridedSwingComponents.MouseCorrectRobot;
import shuttles.DataBar;
import shuttles.Shuttle;
import shuttles.Tir;
import shuttles.HeatBar;
import users.*;
import users.MenuPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;

public class MainFrame extends JFrame {
    private int width = 1600, height = 1000;
    private int lastMouseX, lastMouseY;
    private Assets assets;
    private MainPanel mainPanel;
    private double backgroundSpeed;
    private Users users;
    private Records records;
    private User user;
    private EsqFrame esqFrame;
    private Items items;
    private boolean setText = false;
    private String textToBeShown;
    private long timeTextShown = -1;
    private InGameText inGameText;

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

        records = new Records();

        initForUserSelection();

        backgroundSpeed = 1;

        items = new Items();
    }

    public void setBackgroundSpeed(double backgroundSpeed) {
        this.backgroundSpeed = backgroundSpeed;
    }

    public Assets getAssets() {
        return assets;
    }

    private void makeRound(int round){
        if(round >= 21){
            endGame();
            return;
        }
        showText("Round " + round);
        int type = (round-1)/5 + 1;
        if(round % 5 == 1){
            items.add(new RectangleGroup(40, type, this));
        }else if(round % 5 == 2){
            items.add(new CircularGroup(36, type, this));
        }else if(round % 5 == 3){
            items.add(new RotationalGroup(36, type, this));
        }else if(round % 5 == 4){
            items.add(new SuicideGroup(10, type, this));
        }else{
            System.out.println("ghoule in marhale bayad biad");
            BigEgg bigEgg = new BigEgg(800, -500, 250 * (round/5 + 1), this);
            bigEgg.setNext(800, 500);
            items.add(bigEgg);
        }
    }
    private void nextRound(){
        user.setScore(user.getScore() + user.getMoney() * 3);
        user.setMoney(0);
        users.save();
        int round = user.getLastLevel();
        user.setLastLevel(round + 1);
        round += 2;
        if(round % 5 == 1 && round != 1) user.setRockets(user.getRockets()+1);
        makeRound(round);
    }

    private Thread animationThread;

    private void initAnimationThread() {
        animationThread = new Thread(() -> {
            try {
                while (true) {
                    mainPanel.setYOfBackground((int) (2.5 * backgroundSpeed));

                    if (mainPanel.isInGameMode()) {
                        //set the mouse place
                        Shuttle shuttle = mainPanel.getShuttle();
                        if(shuttle.isDead()) {
                            shuttle.setBeginingCoord();
                            try {
                                MouseCorrectRobot mouseCorrectRobot = new MouseCorrectRobot();
                                mouseCorrectRobot.myMouseMove((int) shuttle.getX(), (int) shuttle.getY());
                            } catch (AWTException e) {
                                e.printStackTrace();
                            }
                        }
                        //add to playing time :D
                        user.setTimePlayed(user.getTimePlayed() + 0.005);
                        //round check
                        boolean hasActiveChickenGroup = false;
                        for(Drawable drawable : items.getItems()) {
                            if (drawable instanceof ChickenGroup || drawable instanceof BigEgg)
                                hasActiveChickenGroup = true;
                        }
                        if(!hasActiveChickenGroup)
                            nextRound();

                        //Set text point
                        if(setText){
                            if(timeTextShown == -1){
                                timeTextShown = System.currentTimeMillis();
                                inGameText = new InGameText(textToBeShown);
                                items.add(inGameText);
                            }
                            if(System.currentTimeMillis() - timeTextShown > 5000){
                                setText = false;
                                timeTextShown = -1;
                                items.remove(inGameText);
                                inGameText = null;
                            }
                        }

                        //deleting bad items
                        for (Drawable drawable : items.getItems()) {
                            if ((drawable instanceof Tir) || (drawable instanceof Egg) ||
                                    (drawable instanceof TirBooster) || (drawable instanceof TirChanger)
                                    || (drawable instanceof MaxTempBooster) || (drawable instanceof Coin)){
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
                                                        user.setScore(user.getScore() + chicken.getType());
                                                        chicken.killed();
                                                        shouldTheChickenDie = true;
//                                                            chickenIterator.remove();
//                                                            drawableIterator.remove();
                                                    }
                                                }
                                            }
                                        }
                                        //death of shuttle
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
//                                    if (!shuttle.isDead() && isIn(shuttle.getX(), shuttle.getY(), shuttle.getSize().width, shuttle.getSize().height, egg.getX(), egg.getY(), egg.getSize().width, egg.getSize().height)) {
                                if (!shuttle.isDead() && conflict(shuttle, egg)) {
                                    shuttleDied();
                                    shuttle.dead();
                                    items.remove(drawable);
                                }
                            } else if(drawable instanceof  BigEgg){
                                BigEgg bigEgg = (BigEgg)drawable;
                                for(Drawable drwbl : items.getItems()){
                                    if(drwbl instanceof Tir){
                                        if(conflict(drawable, drwbl)) {
                                            bigEgg.reduceLives(((Tir) drwbl).getPower());
                                            items.remove(drwbl);
                                        }
                                    }else if(drwbl instanceof Shuttle){
                                        if(conflict(drawable, drwbl)) {
                                            bigEgg.reduceLives(20);
                                            shuttle.dead();
                                            shuttleDied();
                                        }
                                    }
                                }
                                if(bigEgg.getLife() <= 0){
                                    showText("Congratulations!");
                                    bigEgg.killed();
                                    items.remove(bigEgg);
                                }
                            }
                        }
//                            System.out.println("killing things finished");
                        //getting boosters
                        try {
                            if (shuttle != null && !shuttle.isDead()) {
                                for (Drawable drawable : items.getItems()) {
                                    if (drawable.hasImage() && conflict(shuttle, drawable)) {
                                        if (drawable instanceof MaxTempBooster) {
                                            shuttle.setMaxDegree(shuttle.getMaxDegree() + 5);
                                            items.remove(drawable);
                                        } else if (drawable instanceof TirBooster) {
                                            shuttle.setFirePower(shuttle.getFirePower() + 1);
                                            user.setFirePower(user.getFirePower()+1);
                                            items.remove(drawable);
                                        } else if (drawable instanceof TirChanger) {
                                            if(shuttle.getFireType() == ((TirChanger)drawable).getType()){
                                                shuttle.setFirePower(shuttle.getFirePower() + 1);
                                                user.setFirePower(user.getFirePower()+1);
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
                        //getting coins
                        if(shuttle != null && !shuttle.isDead()){
                            for(Drawable drawable:items.getItems())
                                if(drawable instanceof Coin && conflict(shuttle, drawable)){
                                    items.remove(drawable);
                                    user.setMoney(user.getMoney()+1);
                                }
                        }
                        //destroying coins
                        for(Drawable drawable1:items.getItems())
                            if(drawable1 instanceof Tir)
                                for(Drawable drawable2:items.getItems())
                                    if(drawable2 instanceof Coin && conflict(drawable1, drawable2)){
                                        items.remove(drawable1);
                                        items.remove(drawable2);
                                    }
                    }
                    mainPanel.revalidate();
                    mainPanel.repaint();
                    animationThread.sleep(5);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    public void showText(String textToBeShown){
        this.textToBeShown = textToBeShown;
        setText = true;
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
        mainPanel.setCursor(Cursor.getDefaultCursor());

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

        mainPanel.setCursor(Cursor.getDefaultCursor());

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

    public void initForRanking(){
        mainPanel.clear();
        revalidate();
        repaint();
        mainPanel.setCursor(Cursor.getDefaultCursor());
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.LINE_AXIS));
        Ranking ranking = new Ranking(this);
        mainPanel.add(ranking);
        for(Record record : records.getRecords()) {
            ranking.addRecord(record);
        }
        ranking.pack();
    }

    public void initForGame() {
        mainPanel.clear();
        revalidate();
        repaint();
//        setCursor(null);
//        /*
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image image = toolkit.getImage("icons/handwriting.gif");
        Cursor c = toolkit.createCustomCursor(image , new Point(mainPanel.getX(),
                mainPanel.getY()), "img");
        mainPanel.setCursor (c);
//         */


        mainPanel.setShuttle(new Shuttle(user.getShuttleType(), assets, this, user.getFireType(), user.getFirePower()));
        items.add(new HeatBar(this));
        items.add(mainPanel.getShuttle());
        items.add(new DataBar(this));


        try {
            MouseCorrectRobot mouseCorrectRobot = new MouseCorrectRobot();
            mouseCorrectRobot.myMouseMove((int)mainPanel.getShuttle().getX(), (int)mainPanel.getShuttle().getY());
        } catch (AWTException e) {
            e.printStackTrace();
        }

//        Chicken c = new Chicken(500, 500, 20, 0, 1, assets.getChicken(1, 0), this);
//        items.add(c);
//        c.setNext(1000, 500 );
//        items.add(new RotationalGroup(36, 1, this));
//        this.showText("Round 1");
        if(user.getLastLevel() != -1) {
            makeRound(user.getLastLevel() + 1);
        }

        mainPanel.setInGameMode(true);
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
                if(mainPanel.isInGameMode() && !mainPanel.getShuttle().isDead()) {
//                    System.out.println("oooops");
                    mainPanel.getShuttle().setX(e.getX());
                    mainPanel.getShuttle().setY(e.getY());
                }
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
//                System.out.println("dishdiri didin");
                mainPanel.getShuttle().fire();
            }
        });
        //key listener
        mainPanel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if(mainPanel.isInGameMode() && !mainPanel.getShuttle().isDead()) {
                    if (e.getKeyChar() == 'a' || e.getKeyCode() == 37)
                        mainPanel.getShuttle().toLeft();
                    if (e.getKeyChar() == 's' || e.getKeyCode() == 40)
                        mainPanel.getShuttle().toDown();
                    if (e.getKeyChar() == 'd' || e.getKeyCode() == 39)
                        mainPanel.getShuttle().toRight();
                    if (e.getKeyChar() == 'w' || e.getKeyCode() == 38)
                        mainPanel.getShuttle().toUp();
                }
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
        for(Drawable drawable:items.getItems())
            if(drawable instanceof ChickenGroup) {
                for(Chicken chicken:((ChickenGroup)drawable).getChickens()) {
                    chicken.killed();
                    user.setScore(user.getScore() + chicken.getType());
                }
                items.remove(drawable);
            }else if(drawable instanceof Egg)
                items.remove(drawable);

    }

    public void shuttleDied() {
        user.setLives(user.getLives() - 1);
        if (user.getLives() == 0)
            endGame();
        user.setMoney(0);
    }

    public void endGame() {
        //TODO
        System.out.println("game ended");
        records.addRecord(new Record(user.getUsername(), user.getTimePlayed(), user.getLastLevel(), user.getScore()));
        mainPanel.setInGameMode(false);
        initForRanking();
    }

    public int getLastMouseX() {
        return lastMouseX;
    }

    public void setLastMouseX(int lastMouseX) {
        this.lastMouseX = lastMouseX;
    }

    public int getLastMouseY() {
        return lastMouseY;
    }

    public void setLastMouseY(int lastMouseY) {
        this.lastMouseY = lastMouseY;
    }

    public Records getRecords() {
        return records;
    }
}
