package assets;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Assets {
    //Background
    private BufferedImage backgroundImage;

    //shuttles
    private  BufferedImage[] shuttle;

    //fire
    private BufferedImage[] fire;

    //Button Images
    private ImageIcon addPlayerButtonDefault, addPlayerButtonHovered, addPlayerButtonPressed;
    private ImageIcon deletePlayerButtonDefault, deletePlayerButtonHovered, deletePlayerButtonPressed;
    private ImageIcon selectPlayerButtonDefault, selectPlayerButtonHovered, selectPlayerButtonPressed;
    private ImageIcon okButtonDefault, okButtonHovered, okButtonPressed;
    private ImageIcon newGameButtonDefault, newGameButtonHovered, newGameButtonPressed;
    private ImageIcon loadGameButtonDefault, loadGameButtonHovered, loadGameButtonPressed, loadGameButtonDisabled;
    private ImageIcon settingsButtonDefault, settingsButtonHovered, settingsButtonPressed;
    private ImageIcon rankingButtonDefault, rankingButtonHovered, rankingButtonPressed;
    private ImageIcon quitButtonDefault, quitButtonHovered, quitButtonPressed;
    private ImageIcon continueButtonDefault, continueButtonHovered, continueButtonPressed;
    private ImageIcon backButtonDefault, backButtonHovered, backButtonPressed;
    private ImageIcon singlePlayerButtonDefault, singlePlayerButtonHovered, singlePlayerButtonPressed;
    private ImageIcon multiplayerButtonDefault, multiplayerButtonHovered, multiplayerButtonPressed;
    private ImageIcon serverButtonDefault, serverButtonHovered, serverButtonPressed;
    private ImageIcon clientButtonDefault, clientButtonHovered, clientButtonPressed;
    private ImageIcon connectButtonDefault, connectButtonHovered, connectButtonPressed;
    private ImageIcon makegameButtonDefault, makegameButtonHovered, makegameButtonPressed;
    private ImageIcon startgameButtonDefault, startgameButtonHovered, startgameButtonPressed;
    private ImageIcon addBossButtonDefault, addBossButtonHovered, addBossButtonPressed;
    private ImageIcon addGroupButtonDefault, addGroupButtonHovered, addGroupButtonPressed;

    //Logos
    private ImageIcon chickenInvadersLogo;

    //Rockets
    private BufferedImage rocket;

    //mini images for data
    private BufferedImage heart, rocketLogo, meat, coinLogo;

    //Chicken images
    private BufferedImage egg;
    private BufferedImage[][] chickens;
    private BufferedImage bigEgg;

    //booster images
    private BufferedImage maxTempBooster;
    private  BufferedImage tirBooster;
    private BufferedImage[] tirChanger;

    //Coin
    private BufferedImage coin;

    public Assets(){
        this.shuttle = new BufferedImage[4];
        this.fire = new BufferedImage[5];
        this.chickens = new BufferedImage[5][14];
        this.tirChanger = new BufferedImage[4];
    }

    public BufferedImage getBackgroundImage() {
//        System.out.println("hahaha");
        if(backgroundImage == null)
            setBackgroundImage();
        return backgroundImage;
    }

    private void setBackgroundImage() {
        try {
            this.backgroundImage = ImageIO.read(new File("assets/BackgroundImage.jpg"));
//            System.out.println("Background Image read! :))");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void setShuttle(int id){
        try {
            this.shuttle[id] = ImageIO.read(new File("assets/shuttle" + id + ".png"));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public BufferedImage getShuttle(int id){
        if(shuttle[id] == null)
            setShuttle(id);
        return shuttle[id];
    }

    private void setFire(int id){
        try {
            System.out.println("assets/fire" + id + ".png");
            this.fire[id] = ImageIO.read(new File("assets/tir" + id + ".png"));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public BufferedImage getFire(int id){
        if(fire[id] == null)
            setFire(id);
        return fire[id];
    }

    private void setAddPlayerButtonDefault() {
        try {
            addPlayerButtonDefault = new ImageIcon(ImageIO.read(new File("assets/buttons/AddPlayerButton/Default.png")));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public ImageIcon getAddPlayerButtonDefault() {
        if(addPlayerButtonDefault == null)
            setAddPlayerButtonDefault();
        return addPlayerButtonDefault;
    }
    private void setAddPlayerButtonHovered() {
        try {
            addPlayerButtonHovered = new ImageIcon(ImageIO.read(new File("assets/buttons/AddPlayerButton/Hovered.png")));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public ImageIcon getAddPlayerButtonHovered() {
        if(addPlayerButtonHovered == null)
            setAddPlayerButtonHovered();
        return addPlayerButtonHovered;
    }
    private void setAddPlayerButtonPressed() {
        try {
            addPlayerButtonPressed = new ImageIcon(ImageIO.read(new File("assets/buttons/AddPlayerButton/Pressed.png")));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public ImageIcon getAddPlayerButtonPressed() {
        if(addPlayerButtonPressed == null)
            setAddPlayerButtonPressed();
        return addPlayerButtonPressed;
    }
    private void setDeletePlayerButtonDefault(){
        try {
            deletePlayerButtonDefault = new ImageIcon(ImageIO.read(new File("assets/buttons/DeletePlayerButton/Default.png")));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public ImageIcon getDeletePlayerButtonDefault(){
        if(deletePlayerButtonDefault == null)
            setDeletePlayerButtonDefault();
        return deletePlayerButtonDefault;
    }
    private void setDeletePlayerButtonHovered(){
        try {
            deletePlayerButtonHovered = new ImageIcon(ImageIO.read(new File("assets/buttons/DeletePlayerButton/Hovered.png")));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public ImageIcon getDeletePlayerButtonHovered(){
        if(deletePlayerButtonHovered == null)
            setDeletePlayerButtonHovered();
        return deletePlayerButtonHovered;
    }
    private void setDeletePlayerButtonPressed(){
        try {
            deletePlayerButtonPressed = new ImageIcon(ImageIO.read(new File("assets/buttons/DeletePlayerButton/Pressed.png")));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public ImageIcon getDeletePlayerButtonPressed(){
        if(deletePlayerButtonPressed == null)
            setDeletePlayerButtonPressed();
        return deletePlayerButtonPressed;
    }
    private void setSelectPlayerButtonDefault(){
        try{
            selectPlayerButtonDefault = new ImageIcon(ImageIO.read(new File("assets/buttons/SelectPlayerButton/Default.png")));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public ImageIcon getSelectPlayerButtonDefault(){
        if(selectPlayerButtonDefault == null)
            setSelectPlayerButtonDefault();
        return selectPlayerButtonDefault;
    }
    private void setSelectPlayerButtonHovered(){
        try{
            selectPlayerButtonHovered = new ImageIcon(ImageIO.read(new File("assets/buttons/SelectPlayerButton/Hovered.png")));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public ImageIcon getSelectPlayerButtonHovered(){
        if(selectPlayerButtonHovered == null)
            setSelectPlayerButtonHovered();
        return selectPlayerButtonHovered;
    }
    private void setSelectPlayerButtonPressed(){
        try{
            selectPlayerButtonPressed = new ImageIcon(ImageIO.read(new File("assets/buttons/SelectPlayerButton/Pressed.png")));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public ImageIcon getSelectPlayerButtonPressed(){
        if(selectPlayerButtonPressed == null)
            setSelectPlayerButtonPressed();
        return selectPlayerButtonPressed;
    }
    private void setOkButtonDefault(){
        try {
            okButtonDefault = new ImageIcon(ImageIO.read(new File("assets/buttons/OkButton/Default.png")));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public ImageIcon getOkButtonDefault(){
        if(okButtonDefault == null)
            setOkButtonDefault();
        return okButtonDefault;
    }
    private void setOkButtonHovered(){
        try {
            okButtonHovered = new ImageIcon(ImageIO.read(new File("assets/buttons/OkButton/Hovered.png")));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public ImageIcon getOkButtonHovered(){
        if(okButtonHovered == null)
            setOkButtonHovered();
        return okButtonHovered;
    }
    private void setOkButtonPressed(){
        try {
            okButtonPressed = new ImageIcon(ImageIO.read(new File("assets/buttons/OkButton/Pressed.png")));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public ImageIcon getOkButtonPressed(){
        if(okButtonPressed == null)
            setOkButtonPressed();
        return okButtonPressed;
    }
    private void setNewGameButtonDefault(){
        try {
            newGameButtonDefault= new ImageIcon(ImageIO.read(new File("assets/buttons/NewGameButton/Default.png")));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public ImageIcon getNewGameButtonDefault(){
        if(newGameButtonDefault == null)
            setNewGameButtonDefault();
        return newGameButtonDefault;
    }
    private void setNewGameButtonHovered(){
        try {
            newGameButtonHovered = new ImageIcon(ImageIO.read(new File("assets/buttons/NewGameButton/Hovered.png")));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public ImageIcon getNewGameButtonHovered(){
        if(newGameButtonHovered == null)
            setNewGameButtonHovered();
        return newGameButtonHovered;
    }
    private void setNewGameButtonPressed(){
        try {
            newGameButtonPressed = new ImageIcon(ImageIO.read(new File("assets/buttons/NewGameButton/Pressed.png")));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public ImageIcon getNewGameButtonPressed(){
        if(newGameButtonPressed == null)
            setNewGameButtonPressed();
        return newGameButtonPressed;
    }
    private void setLoadGameButtonDefault(){
        try {
            loadGameButtonDefault = new ImageIcon(ImageIO.read(new File("assets/buttons/LoadGameButton/Default.png")));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public ImageIcon getLoadGameButtonDefault(){
        if(loadGameButtonDefault == null)
            setLoadGameButtonDefault();
        return loadGameButtonDefault;
    }
    private void setLoadGameButtonHovered(){
        try {
            loadGameButtonHovered = new ImageIcon(ImageIO.read(new File("assets/buttons/LoadGameButton/Hovered.png")));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public ImageIcon getLoadGameButtonHovered(){
        if(loadGameButtonHovered == null)
            setLoadGameButtonHovered();
        return loadGameButtonHovered;
    }
    private void setLoadGameButtonPressed(){
        try {
            loadGameButtonPressed = new ImageIcon(ImageIO.read(new File("assets/buttons/LoadGameButton/Pressed.png")));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public ImageIcon getLoadGameButtonPressed(){
        if(loadGameButtonPressed == null)
            setLoadGameButtonPressed();
        return loadGameButtonPressed;
    }
    private void setLoadGameButtonDisabled(){
        try {
            loadGameButtonDisabled = new ImageIcon(ImageIO.read(new File("assets/buttons/LoadGameButton/Disabled.png")));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public ImageIcon getLoadGameButtonDisabled(){
        if(loadGameButtonDisabled == null)
            setLoadGameButtonDisabled();
        return loadGameButtonDisabled;
    }
    private void setSettingsButtonDefault(){
        try {
            settingsButtonDefault = new ImageIcon(ImageIO.read(new File("assets/buttons/SettingsButton/Default.png")));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public ImageIcon getSettingsButtonDefault(){
        if(settingsButtonDefault == null)
            setSettingsButtonDefault();
        return settingsButtonDefault;
    }
    private void setSettingsButtonPressed(){
        try {
            settingsButtonPressed = new ImageIcon(ImageIO.read(new File("assets/buttons/SettingsButton/Pressed.png")));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public ImageIcon getSettingsButtonPressed(){
        if(settingsButtonPressed == null)
            setSettingsButtonPressed();
        return settingsButtonPressed;
    }
    private void setSettingsButtonHovered(){
        try {
            settingsButtonHovered = new ImageIcon(ImageIO.read(new File("assets/buttons/SettingsButton/Hovered.png")));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public ImageIcon getSettingsButtonHovered(){
        if(settingsButtonHovered == null)
            setSettingsButtonHovered();
        return settingsButtonHovered;
    }
    private void setRankingButtonDefault(){
        try {
            rankingButtonDefault = new ImageIcon(ImageIO.read(new File("assets/buttons/RankingButton/Default.png")));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public ImageIcon getRankingButtonDefault(){
        if(rankingButtonDefault == null)
            setRankingButtonDefault();
        return rankingButtonDefault;
    }
    private void setRankingButtonHovered(){
        try {
            rankingButtonHovered = new ImageIcon(ImageIO.read(new File("assets/buttons/RankingButton/Hovered.png")));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public ImageIcon getRankingButtonHovered(){
        if(rankingButtonHovered == null)
            setRankingButtonHovered();
        return rankingButtonHovered;
    }
    private void setRankingButtonPressed(){
        try {
            rankingButtonPressed = new ImageIcon(ImageIO.read(new File("assets/buttons/RankingButton/Pressed.png")));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public ImageIcon getRankingButtonPressed(){
        if(rankingButtonPressed == null)
            setRankingButtonPressed();
        return rankingButtonPressed;
    }
    private void setBackButtonDefault(){
        try {
            backButtonDefault = new ImageIcon(ImageIO.read(new File("assets/buttons/BackButton/Default.png")));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public ImageIcon getBackButtonDefault(){
        if(backButtonDefault == null)
            setBackButtonDefault();
        return backButtonDefault;
    }
    private void setBackButtonHovered(){
        try {
            backButtonHovered = new ImageIcon(ImageIO.read(new File("assets/buttons/BackButton/Hovered.png")));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public ImageIcon getBackButtonHovered(){
        if(backButtonHovered == null)
            setBackButtonHovered();
        return backButtonHovered;
    }
    private void setBackButtonPressed(){
        try {
            backButtonPressed = new ImageIcon(ImageIO.read(new File("assets/buttons/BackButton/Pressed.png")));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public ImageIcon getBackButtonPressed(){
        if(backButtonPressed == null)
            setBackButtonPressed();
        return backButtonPressed;
    }
    private void setChickenInvadersLogo(){
        try {
            chickenInvadersLogo = new ImageIcon(ImageIO.read(new File("assets/ChickenInvadersLogo.png")));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public ImageIcon getChickenInvadersLogo(){
        if(chickenInvadersLogo == null)
            setChickenInvadersLogo();
        return chickenInvadersLogo;
    }
    private void setQuitButtonDefault(){
        try {
            quitButtonDefault = new ImageIcon(ImageIO.read(new File("assets/buttons/QuitButton/Default.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public ImageIcon getQuitButtonDefault(){
        if(quitButtonDefault == null)
            setQuitButtonDefault();
        return quitButtonDefault;
    }
    private void setQuitButtonHovered(){
        try {
            quitButtonHovered = new ImageIcon(ImageIO.read(new File("assets/buttons/QuitButton/Hovered.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public ImageIcon getQuitButtonHovered(){
        if(quitButtonHovered == null)
            setQuitButtonHovered();
        return quitButtonHovered;
    }
    private void setQuitButtonPressed(){
        try {
            quitButtonPressed = new ImageIcon(ImageIO.read(new File("assets/buttons/QuitButton/Pressed.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public ImageIcon getQuitButtonPressed(){
        if(quitButtonPressed == null)
            setQuitButtonPressed();
        return quitButtonPressed;
    }
    private void setContinueButtonDefault(){
        try {
            continueButtonDefault = new ImageIcon(ImageIO.read(new File("assets/buttons/ContinueButton/Default.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public ImageIcon getContinueButtonDefault(){
        if(continueButtonDefault == null)
            setContinueButtonDefault();
        return continueButtonDefault;
    }
    private void setContinueButtonHovered(){
        try {
            continueButtonHovered = new ImageIcon(ImageIO.read(new File("assets/buttons/ContinueButton/Hovered.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public ImageIcon getContinueButtonHovered(){
        if(continueButtonHovered == null)
            setContinueButtonHovered();
        return continueButtonHovered;
    }
    private void setContinueButtonPressed(){
        try {
            continueButtonPressed = new ImageIcon(ImageIO.read(new File("assets/buttons/ContinueButton/Pressed.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public ImageIcon getContinueButtonPressed(){
        if(continueButtonPressed == null)
            setContinueButtonPressed();
        return continueButtonPressed;
    }
    private void setRocket(){
        try {
            rocket = ImageIO.read(new File("assets/rocket.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public BufferedImage getRocket(){
        if(rocket == null)
            setRocket();
        return rocket;
    }
    private void setMeat(){
        try {
            meat = ImageIO.read(new File("assets/meat.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public BufferedImage getMeat(){
        if(meat == null)
            setMeat();
        return meat;
    }
    private void setHeart(){
        try {
            heart = ImageIO.read(new File("assets/heart.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public BufferedImage getHeart(){
        if(heart == null)
            setHeart();
        return heart;
    }
    private void setRocketLogo(){
        try {
            rocketLogo = ImageIO.read(new File("assets/rocketLogo.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public BufferedImage getRocketLogo(){
        if(rocketLogo == null)
            setRocketLogo();
        return rocketLogo;
    }

    private void setCoinLogo(){
        try {
            coinLogo = ImageIO.read(new File("assets/coinLogo.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public BufferedImage getCoinLogo(){
        if(coinLogo == null)
            setCoinLogo();
        return  coinLogo;
    }

    public BufferedImage getEgg() {
        if(egg == null) {
            setEgg();
        }
        return egg;
    }

    private void setEgg() {
        try {
            egg = ImageIO.read(new File("assets/egg.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BufferedImage getChicken(int type, int frame){
        if(chickens[type][frame] == null){
            setChicken(type, frame);
        }
        return chickens[type][frame];
    }
    private void setChicken(int type, int frame){
//        System.out.println("dishdiri didin mashala");
        try {
            chickens[type][frame] = ImageIO.read(new File("assets/chicken"+type+"_"+frame+".png"));
        } catch (IOException e) {
            System.out.println("Failed to load : assets/chicken"+type+"_"+frame+".png");
            e.printStackTrace();
        }
    }

    private void setMaxTempBooster(){
        try {
            maxTempBooster = ImageIO.read(new File("assets/maxTempBooster.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public BufferedImage getMaxTempBooster(){
        if(maxTempBooster == null)
            setMaxTempBooster();
        return maxTempBooster;
    }

    private void setTirBooster(){
        try {
            tirBooster = ImageIO.read(new File("assets/tirBooster.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public BufferedImage getTirBooster(){
        if(tirBooster == null)
            setTirBooster();
        return tirBooster;
    }

    private void setTirChanger(int type){
        try {
            tirChanger[type] = ImageIO.read(new File("assets/tirChanger"+type+".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public BufferedImage getTirChanger(int type){
        if(tirChanger[type] == null)
            setTirChanger(type);
        return tirChanger[type];
    }

    private void setCoin(){
        try {
            coin = ImageIO.read(new File("assets/coin.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public BufferedImage getCoin(){
        if(coin == null)
            setCoin();
        return coin;
    }
    private void setBigEgg(){
        try {
            bigEgg = ImageIO.read(new File("assets/BigEgg.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public BufferedImage getBigEgg(){
        if(bigEgg == null)
            setBigEgg();
        return bigEgg;
    }
    private void setSinglePlayerButtonDefault(){
        try {
            singlePlayerButtonDefault = new ImageIcon(ImageIO.read(new File("assets/buttons/singlePlayerButton/Default.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public ImageIcon getSinglePlayerButtonDefault(){
        if(singlePlayerButtonDefault == null)
            setSinglePlayerButtonDefault();
        return singlePlayerButtonDefault;
    }
    private void setSinglePlayerButtonHovered(){
        try {
            singlePlayerButtonHovered = new ImageIcon(ImageIO.read(new File("assets/buttons/singlePlayerButton/Hovered.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public ImageIcon getSinglePlayerButtonHovered(){
        if(singlePlayerButtonHovered == null)
            setSinglePlayerButtonHovered();
        return singlePlayerButtonHovered;
    }
    private void setSinglePlayerButtonPressed(){
        try {
            singlePlayerButtonPressed = new ImageIcon(ImageIO.read(new File("assets/buttons/singlePlayerButton/Pressed.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public ImageIcon getSinglePlayerButtonPressed(){
        if(singlePlayerButtonPressed == null)
            setSinglePlayerButtonPressed();
        return singlePlayerButtonPressed;
    }
    private void setMultiplayerButtonDefault(){
        try {
            multiplayerButtonDefault= new ImageIcon(ImageIO.read(new File("assets/buttons/multiplayerButton/Default.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public ImageIcon getmultiplayerButtonDefault(){
        if(multiplayerButtonDefault == null)
            setMultiplayerButtonDefault();
        return multiplayerButtonDefault;
    }
    private void setMultiplayerButtonHovered(){
        try {
            multiplayerButtonHovered= new ImageIcon(ImageIO.read(new File("assets/buttons/multiplayerButton/Hovered.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public ImageIcon getmultiplayerButtonHovered(){
        if(multiplayerButtonHovered == null)
            setMultiplayerButtonHovered();
        return multiplayerButtonHovered;
    }
    private void setMultiplayerButtonPressed(){
        try {
            multiplayerButtonPressed= new ImageIcon(ImageIO.read(new File("assets/buttons/multiplayerButton/Pressed.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public ImageIcon getmultiplayerButtonPressed(){
        if(multiplayerButtonPressed == null)
            setMultiplayerButtonPressed();
        return multiplayerButtonPressed;
    }
    private void setserverButtonDefault(){
        try {
            serverButtonDefault= new ImageIcon(ImageIO.read(new File("assets/buttons/serverButton/Default.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public ImageIcon getserverButtonDefault(){
        if(serverButtonDefault == null)
            setserverButtonDefault();
        return serverButtonDefault;
    }
    private void setserverButtonHovered(){
        try {
            serverButtonHovered= new ImageIcon(ImageIO.read(new File("assets/buttons/serverButton/Hovered.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public ImageIcon getserverButtonHovered(){
        if(serverButtonHovered == null)
            setserverButtonHovered();
        return serverButtonHovered;
    }
    private void setserverButtonPressed(){
        try {
            serverButtonPressed= new ImageIcon(ImageIO.read(new File("assets/buttons/serverButton/Pressed.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public ImageIcon getserverButtonPressed(){
        if(serverButtonPressed == null)
            setserverButtonPressed();
        return serverButtonPressed;
    }
    private void setclientButtonDefault(){
        try {
            clientButtonDefault= new ImageIcon(ImageIO.read(new File("assets/buttons/clientButton/Default.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public ImageIcon getclientButtonDefault(){
        if(clientButtonDefault == null)
            setclientButtonDefault();
        return clientButtonDefault;
    }
    private void setclientButtonHovered(){
        try {
            clientButtonHovered= new ImageIcon(ImageIO.read(new File("assets/buttons/clientButton/Hovered.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public ImageIcon getclientButtonHovered(){
        if(clientButtonHovered == null)
            setclientButtonHovered();
        return clientButtonHovered;
    }
    private void setclientButtonPressed(){
        try {
            clientButtonPressed= new ImageIcon(ImageIO.read(new File("assets/buttons/clientButton/Pressed.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public ImageIcon getclientButtonPressed() {
        if (clientButtonPressed == null)
            setclientButtonPressed();
        return clientButtonPressed;
    }
    private void setconnectButtonDefault(){
        try {
            connectButtonDefault= new ImageIcon(ImageIO.read(new File("assets/buttons/connectButton/Default.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public ImageIcon getconnectButtonDefault(){
        if(connectButtonDefault == null)
            setconnectButtonDefault();
        return connectButtonDefault;
    }
    private void setconnectButtonHovered(){
        try {
            connectButtonHovered= new ImageIcon(ImageIO.read(new File("assets/buttons/connectButton/Hovered.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public ImageIcon getconnectButtonHovered(){
        if(connectButtonHovered == null)
            setconnectButtonHovered();
        return connectButtonHovered;
    }
    private void setconnectButtonPressed(){
        try {
            connectButtonPressed= new ImageIcon(ImageIO.read(new File("assets/buttons/connectButton/Pressed.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public ImageIcon getconnectButtonPressed(){
        if(connectButtonPressed == null)
            setconnectButtonPressed();
        return connectButtonPressed;
    }
    private void setmakegameButtonDefault(){
        try {
            makegameButtonDefault= new ImageIcon(ImageIO.read(new File("assets/buttons/makegameButton/Default.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public ImageIcon getmakegameButtonDefault(){
        if(makegameButtonDefault == null)
            setmakegameButtonDefault();
        return makegameButtonDefault;
    }
    private void setmakegameButtonHovered(){
        try {
            makegameButtonHovered= new ImageIcon(ImageIO.read(new File("assets/buttons/makegameButton/Hovered.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public ImageIcon getmakegameButtonHovered(){
        if(makegameButtonHovered == null)
            setmakegameButtonHovered();
        return makegameButtonHovered;
    }
    private void setmakegameButtonPressed(){
        try {
            makegameButtonPressed= new ImageIcon(ImageIO.read(new File("assets/buttons/makegameButton/Pressed.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public ImageIcon getmakegameButtonPressed(){
        if(makegameButtonPressed == null)
            setmakegameButtonPressed();
        return makegameButtonPressed;
    }
    private void setstartgameButtonDefault(){
        try {
            startgameButtonDefault= new ImageIcon(ImageIO.read(new File("assets/buttons/startgameButton/Default.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public ImageIcon getstartgameButtonDefault(){
        if(startgameButtonDefault == null)
            setstartgameButtonDefault();
        return startgameButtonDefault;
    }
    private void setstartgameButtonHovered(){
        try {
            startgameButtonHovered= new ImageIcon(ImageIO.read(new File("assets/buttons/startgameButton/Hovered.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public ImageIcon getstartgameButtonHovered(){
        if(startgameButtonHovered == null)
            setstartgameButtonHovered();
        return startgameButtonHovered;
    }
    private void setstartgameButtonPressed(){
        try {
            startgameButtonPressed= new ImageIcon(ImageIO.read(new File("assets/buttons/startgameButton/Pressed.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public ImageIcon getstartgameButtonPressed(){
        if(startgameButtonPressed == null)
            setstartgameButtonPressed();
        return startgameButtonPressed;
    }
    private void setAddBossButtonDefault(){
        try {
            addBossButtonDefault= new ImageIcon(ImageIO.read(new File("assets/buttons/addBossButton/Default.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public ImageIcon getAddBossButtonDefault(){
        if(addBossButtonDefault == null)
            setAddBossButtonDefault();
        return addBossButtonDefault;
    }
    private void setAddBossButtonHovered(){
        try {
            addBossButtonHovered= new ImageIcon(ImageIO.read(new File("assets/buttons/addBossButton/Hovered.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public ImageIcon getAddBossButtonHovered(){
        if(addBossButtonHovered == null)
            setAddBossButtonHovered();
        return addBossButtonHovered;
    }
    private void setAddBossButtonPressed(){
        try {
            addBossButtonPressed= new ImageIcon(ImageIO.read(new File("assets/buttons/addBossButton/Pressed.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public ImageIcon getAddBossButtonPressed(){
        if(addBossButtonPressed == null)
            setAddBossButtonPressed();
        return addBossButtonPressed;
    }
    private void setAddGroupButtonDefault(){
        try {
            addGroupButtonDefault= new ImageIcon(ImageIO.read(new File("assets/buttons/addGroupButton/Default.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public ImageIcon getAddGroupButtonDefault(){
        if(addGroupButtonDefault == null)
            setAddGroupButtonDefault();
        return addGroupButtonDefault;
    }
    private void setAddGroupButtonHovered(){
        try {
            addGroupButtonHovered= new ImageIcon(ImageIO.read(new File("assets/buttons/addGroupButton/Hovered.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public ImageIcon getAddGroupButtonHovered(){
        if(addGroupButtonHovered == null)
            setAddGroupButtonHovered();
        return addGroupButtonHovered;
    }
    private void setAddGroupButtonPressed(){
        try {
            addGroupButtonPressed= new ImageIcon(ImageIO.read(new File("assets/buttons/addGroupButton/Pressed.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public ImageIcon getAddGroupButtonPressed(){
        if(addGroupButtonPressed == null)
            setAddGroupButtonPressed();
        return addGroupButtonPressed;
    }
}
