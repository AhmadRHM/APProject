package overridedSwingComponents;

import main.MainFrame;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Button extends JLabel {
    private ImageIcon buttonImage;
    private ImageIcon buttonHovered;
    private ImageIcon buttonPressed;
    private MainFrame mainFrame;
    public Button(ImageIcon buttonImage, ImageIcon buttonHovered, ImageIcon buttonPressed, MainFrame mainFrame){
        super(buttonImage);
        this.buttonImage = buttonImage;
        this.buttonHovered = buttonHovered;
        this.buttonPressed = buttonPressed;
        this.mainFrame = mainFrame;
    }
    public void buttonHovered(){
        this.setIcon(buttonHovered);
    }
    public void setButtonPressed(){
        this.setIcon(buttonPressed);
    }
    public void resetImage(){
        this.setIcon(buttonImage);
    }
}
