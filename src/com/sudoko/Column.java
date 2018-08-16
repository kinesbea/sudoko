package com.sudoko;

public class Column {
	
	private static final int CELL_COUNT = 9;
	private Cell matrixCol[] = new Cell[CELL_COUNT];
	private String takenNumbers ="";

	public Column(Row[] rows, int index) {
		super();
		for (int i=0; i<CELL_COUNT; ++i) {
			Cell c = rows[i].getCell(index);
			matrixCol[i] = new Cell();
			if (!"X".equals(c.getCellNumber())) {
				matrixCol[i].setCellNumber(c.getCellNumber());
				takenNumbers = takenNumbers+c.getCellNumber();
			}
		}
	}
	
	public Cell getCell(int idx) {
		return matrixCol[idx];
	}	
	public String getTakenNumbers() {
		return takenNumbers;
	}

	public void setTakenNumbers(String takenNumbers) {
		this.takenNumbers = takenNumbers;
	}	
}
