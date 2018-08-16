package com.sudoko;

public class Row {
	private static final int CELL_COUNT = 9;
	private int cellsFilled = 0;
	private Cell matrixRow[] = new Cell[CELL_COUNT];	
	private String takenNumbers = "";
	private int xCount = 0;
	
	public Row(String numbers) {
		super();
		for (int i=0; i<CELL_COUNT; ++i) {
			String c = numbers.substring(i, i+1);
			matrixRow[i] = new Cell();
			if (!"X".equals(c)) {
				matrixRow[i].setCellNumber(c);
				takenNumbers = takenNumbers+c;
			} else setxCount(getxCount() + 1);
		}
	}	
	
	public Cell getCell(int idx) {
		return matrixRow[idx];
	}
	
	public int getCellsFilled() {
		return cellsFilled;
	}

	public void setCellsFilled() {
		this.cellsFilled++;
	}
	
	public Cell[] getMatrixRow() {
		return matrixRow;
	}
	public void setMatrixRow(Cell[] matrixRow) {
		this.matrixRow = matrixRow;
	}
	public String getTakenNumbers() {
		return takenNumbers;
	}
	public void setTakenNumbers(String takenNumbers) {
		this.takenNumbers = takenNumbers;
	}

	public int getxCount() {
		return xCount;
	}

	public void setxCount(int xCount) {
		this.xCount = xCount;
	}
	
	
	
}
