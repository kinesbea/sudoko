package com.sudoko;

public class Cell {
	
	private String cellNumber = "X";
	private String possibleValues = "";
	
	public String getCellNumber() {
		return cellNumber;
	}
	public void setCellNumber(String cellNumber) {
		this.cellNumber = cellNumber;
	}
	public String getPossibleValues() {
		return possibleValues;
	}
	public void setPossibleValues(String possibleValues) {
		this.possibleValues = possibleValues;
	}

}
