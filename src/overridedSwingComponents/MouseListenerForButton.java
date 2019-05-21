package overridedSwingComponents;

import overridedSwingComponents.Button;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public abstract class MouseListenerForButton implements MouseListener {
    private Button getSource(MouseEvent e){
        return (Button)e.getSource();
    }
    @Override
    public void mousePressed(MouseEvent e) {
        getSource(e).setButtonPressed();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        getSource(e).buttonHovered();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        getSource(e).buttonHovered();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        getSource(e).resetImage();
    }
}
