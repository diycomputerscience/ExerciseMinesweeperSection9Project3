package com.diycomputerscience.minesweeper.view;

import java.awt.Component;

public class MockOptionPane implements OptionPane {

	private Component component;
	private Object message;
	private String title;
	private int optionType;
	
	private int mockReturn; 
	
	public MockOptionPane(int mockReturn) {
		this.mockReturn = mockReturn;
	}
	
	@Override
	public int userConfirmation(Component component, 
								Object message,
								String title, 
								int optionType) {
		this.component = component;
		this.message = message;
		this.title = title;
		this.optionType = optionType;
		return this.mockReturn;
	}
	
	public void setMockReturn(int mockReturn) {
		this.mockReturn = mockReturn;
	}
	
	public int getMockReturn() {
		return this.mockReturn;
	}
	
	public Component getComponent() {
		return this.component;
	}
	
	public Object getMessage() {
		return this.message;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public int getOptionType() {
		return this.optionType;
	}	

}
