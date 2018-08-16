package com.sudoko;

public class Group {
	private static final int CELL_COUNT = 9;
	private Cell matrixGrp[] = new Cell[CELL_COUNT];
	private String takenNumbers ="";


	public Group(Row[] rows, int idx) {
		super();
		for (int i=0; i<CELL_COUNT; ++i) {
			for (int j=0; j<CELL_COUNT; ++j) {
				int g = getGroupNumner(i,j);
				if (g!=idx) continue;
				Cell c = rows[i].getCell(j);
				matrixGrp[g] = new Cell();
				if (!"X".equals(c.getCellNumber())) {
					matrixGrp[g].setCellNumber(c.getCellNumber());
					takenNumbers = takenNumbers+c.getCellNumber();
				}
			}
		}
	}

	public static int getGroupNumner(int x, int y) {
		if (x/3==0 && y/3==0) return 0;
		if (x/3==1 && y/3==0) return 1;
		if (x/3==2 && y/3==0) return 2;
		
		if (x/3==0 && y/3==1) return 3;
		if (x/3==1 && y/3==1) return 4;
		if (x/3==2 && y/3==1) return 5;	
		
		if (x/3==0 && y/3==2) return 6;
		if (x/3==1 && y/3==2) return 7;
		if (x/3==2 && y/3==2) return 8;		
		
		return 0;
	}
	
	public String getTakenNumbers() {
		return takenNumbers;
	}
	
	public void setTakenNumbers(String takenNumbers) {
		this.takenNumbers = takenNumbers;
	}
	
}
