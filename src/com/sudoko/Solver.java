package com.sudoko;

public class Solver {
	private static final int CELL_COUNT = 9;
	private Row[] rows = new Row[CELL_COUNT];
	private Column[] columns = new Column[CELL_COUNT];
	private Group[] groups = new Group[CELL_COUNT];
	int xCount = 0;
	
	public String[] process(String[] matrix) {
		initializeMatrix(matrix);	
		loadPossibleValues();
		String guessInfo ="";
		boolean geuessing = false;
		while (xCount>0) {
			boolean hachanged = false;
			for (int i=0; i<CELL_COUNT; i++) {			
				for (int x=0; x<CELL_COUNT; x++) {			
					String found = "";			
					Cell currentCell = rows[i].getCell(x);
					String possibleValues = currentCell.getPossibleValues();
					
					if (possibleValues.trim().length()==0)  continue;
					if (possibleValues.trim().length()==1) {
						found = possibleValues;
					} else {
						found = checkMatches(i, x, found, possibleValues);
					}
					if ( !"".equals(found)) {
						setCellValueAndAdjust(i, x, found);	
						--xCount;
						found = "";	
						hachanged = true;
					} else {
						currentCell.setPossibleValues(possibleValues);	
						if (possibleValues.length()==2 && "".equals(guessInfo)) {
							guessInfo = i+","+x+","+possibleValues;
						}
					}
				}
			}	
			if (!hachanged)	{
				String[] info = guessInfo.split("\\,");
				int iGuess = Integer.valueOf(info[0]);
				int xGuess = Integer.valueOf(info[1]);
				String guessValue = info[2].substring(0, 1);
				if (geuessing && guessValue.trim().length()==0) break;
				if (!geuessing) {
					geuessing = true;
					info[2] = info[2].replaceAll(guessValue, "");
					guessInfo = iGuess+","+xGuess+","+guessValue;
					
					setCellValueAndAdjust(iGuess, xGuess, guessValue);					
					if (info[2].trim().length()!=0) --xCount;
				}
			}

		}
		//debug();
		return convertRowsToArray(rows);
	}

	private void setCellValueAndAdjust(int iGuess, int xGuess, String guessValue) {
		Cell currentCell = rows[iGuess].getCell(xGuess);
		int groupNo = Group.getGroupNumner(iGuess,xGuess);
		currentCell.setCellNumber(guessValue);
		rows[iGuess].setTakenNumbers(rows[iGuess].getTakenNumbers()+guessValue); 					
		columns[xGuess].setTakenNumbers(columns[xGuess].getTakenNumbers()+guessValue);
		groups[groupNo].setTakenNumbers(groups[groupNo].getTakenNumbers()+guessValue);
		currentCell.setPossibleValues("");
		loadPossibleValues();
	}

	private String checkMatches(int i, int x, String found, String possibleValues) {
		for (int c = 0; c < possibleValues.length() ; c ++) {
			String value = possibleValues.substring(c, c+1);
			boolean isFound = true;
			String allPossibleValues = "";
			
			//check matches on neighboring rows
			int xBaseIndex = (i/3)*3;
			for (int xIndex = xBaseIndex; xIndex <= xBaseIndex+2 ; xIndex++ ) {
				if (xIndex != i) {
					if (rows[xIndex].getTakenNumbers().indexOf(value)==-1) isFound = false;
				}
			}
			//check matches on neighboring columns
			int yBaseIndex = (x/3)*3;
			for (int yIndex = yBaseIndex ; yIndex <= yBaseIndex+2 ; yIndex++ ) {
				if (yIndex != x) {
					if (columns[yIndex].getTakenNumbers().indexOf(value)==-1) isFound = false;
				}					
			}	
			if (isFound) {
				found += value;
			} else {
				//check matches to other cells in group
				for (int xIndex = xBaseIndex; xIndex <= xBaseIndex+2 ; xIndex++ ) {
					for (int yIndex = yBaseIndex ; yIndex <= yBaseIndex+2 ; yIndex++ ) {
						if (!(yIndex==x && xIndex==i)) {
							allPossibleValues += rows[xIndex].getCell(yIndex).getPossibleValues();	
						}
					}
				}
				if (allPossibleValues.indexOf(value)==-1) found = value;
			}
		}
		return found;
	}

	private String[] convertRowsToArray(Row[] numberRows) {
		String[] result = new String[9];
		for (int i=0; i<CELL_COUNT; i++) {
			String rowText = "";
			for (int x=0; x<CELL_COUNT; x++) {
				Cell c = rows[i].getCell(x);
				rowText += c.getCellNumber();;
			}	
			result[i] = rowText;
		}
		return result;
	}

	private void loadPossibleValues() {
		for (int i=0; i<CELL_COUNT; i++) {			
			for (int x=0; x<CELL_COUNT; x++) {
				Cell currentCell = rows[i].getCell(x);
				if (!"X".equals(currentCell.getCellNumber())) continue;
				
				int groupNo = Group.getGroupNumner(i, x);
				String allNumbers = rows[i].getTakenNumbers() + columns[x].getTakenNumbers() + groups[groupNo].getTakenNumbers();
				String possibleValues = "";
				for (int p = 1 ; p <= CELL_COUNT ; p++) {
					if (allNumbers.indexOf(String.valueOf(p).trim())==-1)
						possibleValues = possibleValues+String.valueOf(p).trim();
				}
				currentCell.setPossibleValues(possibleValues);	
			}		
		}
	}

	private void initializeMatrix(String[] matrix) {
		for (int x=0; x<CELL_COUNT; x++) {
			rows[x] = new Row(matrix[x]);
			xCount += rows[x].getxCount();
		}	
		for (int x=0; x<CELL_COUNT; x++) {
			columns[x] = new Column(rows,x);
		}		
		for (int x=0; x<CELL_COUNT; x++) {
			groups[x] = new Group(rows, x);
		}
	}
	
	private void debug() {
		for (int i=0; i<CELL_COUNT; i++) {	
			for (int x=0; x<CELL_COUNT; x++) {			
				Cell c = rows[i].getCell(x);
				String text = rows[i].getTakenNumbers();
				String spaces = "";
				for (int s = 0; s<(12 - text.length()); s++)
					spaces += " ";
				System.out.print(text+spaces);	
			}
			System.out.println();
			for (int x=0; x<CELL_COUNT; x++) {
				Cell c = columns[x].getCell(i) ; 
				String text = columns[x].getTakenNumbers();
				String spaces = "";
				for (int s = 0; s<(12 - text.length()); s++)
					spaces += " ";
				System.out.print(text+spaces);				
			}
			System.out.println();
			for (int x=0; x<CELL_COUNT; x++) {
				
				int groupNo = Group.getGroupNumner(i, x);
				String text = groups[groupNo].getTakenNumbers();
				String spaces = "";
				for (int s = 0; s<(12 - text.length()); s++)
					spaces += " ";
				System.out.print(text+spaces);				
			}
			System.out.println();		
			for (int x=0; x<CELL_COUNT; x++) {
				
				Cell c = rows[i].getCell(x);
				String text = "X";
				if (null != rows[i].getCell(x)) 
					text = c.getPossibleValues();
				String spaces = "";
				for (int s = 0; s<(12 - text.length()); s++)
					spaces += " ";
				System.out.print(text+spaces);	
			}	
			System.out.println();
			for (int x=0; x<CELL_COUNT; x++) {
				
				Cell c = rows[i].getCell(x);
				String text = "X";
				if (null != rows[i].getCell(x)) 
					text = c.getCellNumber();
				System.out.print("     "+text+"      ");
				
			}	
			System.out.println();
		}
	}	
}
