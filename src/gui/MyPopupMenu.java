package gui;


import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;


public class MyPopupMenu extends JPopupMenu {

	JMenuItem menuItemLoeschen = null;
	JMenuItem menuItemAendern = null;
	ActionListener listener = null;

	public MyPopupMenu(ActionListener listener) {

		this.listener = listener;

		menuItemLoeschen = new JMenuItem("Löschen");
		menuItemLoeschen.setActionCommand("ContexteMenuLoeschen");
		menuItemLoeschen.addActionListener(this.listener);

		menuItemAendern = new JMenuItem("Ändern");
		menuItemAendern.setActionCommand("ContexteMenuAendern");
		menuItemAendern.addActionListener(this.listener);

		this.add(menuItemLoeschen);
		this.add(menuItemAendern);

	}

}

class PopupListener extends MouseAdapter {
	JPopupMenu popup;

	PopupListener(JPopupMenu popupMenu) {
		popup = popupMenu;
	}

	public void mousePressed(MouseEvent e) {
		maybeShowPopup(e);
	}

	public void mouseReleased(MouseEvent e) {
		maybeShowPopup(e);
	}

	private void maybeShowPopup(MouseEvent e) {
		if (e.isPopupTrigger()) {
			popup.show(e.getComponent(), e.getX(), e.getY());
		}
	}
}