package bo;

import javax.swing.JPanel;

public class slidePane {
		
	public slidePane(JPanel panel) {
		int x = panel.getX();
		int y = panel.getY();
		Thread thread = new Thread() {
			public void run() {
				for(int i = 0; i <= y; i++) {
					try {
						Thread.sleep(1);
						panel.setSize(x, i);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};thread.start();
	}
}
