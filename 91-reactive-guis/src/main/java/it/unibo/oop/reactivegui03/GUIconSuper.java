package it.unibo.oop.reactivegui03;

import it.unibo.oop.reactivegui02.ConcurrentGUI;

public class GUIconSuper extends ConcurrentGUI {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	GUIconSuper(){
		super();
		Control ctrl = new Control();
		new Thread(ctrl).start();
	}
	
private class Control implements Runnable{
    	
    	private boolean basta = false;
        
        public void run() {
            while (!this.basta ) {   
                    try {
						Thread.sleep(10000);
	                    this.stopCounting();
	                    
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                    
            }
        }
		public void stopCounting() {
        	this.basta=true;
        	stop();
		}
    	
    }
}
