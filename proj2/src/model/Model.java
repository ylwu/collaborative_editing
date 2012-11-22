/**
 * 
 */
package model;

import controller.Controller;


public class Model {
	private final Controller c;
	public Model(Controller controller){
		c=controller;
	}

	/**
	 * @param text
	 * 
	 * update
	 */
    public void update(String text) {
	    // TODO Auto-generated method stub
	    
    	// at the end 
    	c.updateFontEnd();
    }

	/**
	 * @return
	 * get text saved in model
	 */
    public String getText() {
	    // TODO Auto-generated method stub
	    return null;
    }

}
