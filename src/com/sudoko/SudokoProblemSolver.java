package com.sudoko;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;

public class SudokoProblemSolver {

	public static void main (String[] args) {
		if (args.length==0) {
			System.out.println("Please enter the full path");
		}
		String fullPath = args[0];
	    File file = new File(fullPath);
	    try {
		    if (file.isDirectory()) {
		        File[] files = file.listFiles(new FilenameFilter() {
		            public boolean accept(File dir, String fileName) {
		              return ((fileName.endsWith(".txt")) && !fileName.contains("sln") ); 
		            }
		        });    	        
		        
		        if (files != null && files.length > 0) {
		            for (File f : files) {
		            	
		            	String outputFileName = f.getParent()+ File.separator+ f.getName().replaceAll(".txt", ".sln.txt");
		            	
		            	String[] matrix = new String[9];
						FileReader fr = new FileReader(f);
						String numberLine = "";
						int i;
						while ((i = fr.read()) != -1) {
							numberLine += (char) i;				
						}						
						matrix = numberLine.split("\\r\\n");
		            	
						Solver solver = new Solver();
						String[] result = solver.process(matrix);
						String hitMissInfo = solver.getHitMissInfo();
						if (null!= hitMissInfo){
							String[] info = hitMissInfo.split("\\,");
							int iGuess = Integer.valueOf(info[0]);
							int xGuess = Integer.valueOf(info[1]);
							String guessValue = info[2];
							for (int idx = 0; idx <guessValue.length(); idx++) {
								String value = guessValue.substring(idx, idx+1);
								String guessResult = new String(iGuess+","+xGuess+","+value);
								solver.setHitMissInfo(guessResult);
								result = solver.process(matrix);
								if (null==solver.getHitMissInfo()) break;
							}
							if (null!=solver.getHitMissInfo()) {
								result = null;
								System.out.println("Cannot solve "+f.getName());
							}
						}
						
						if (null!=result) {
							System.out.println(outputFileName);
							File outputFile = new File(outputFileName);
							if (outputFile.exists()) outputFile.delete();
							BufferedWriter out = new BufferedWriter(new FileWriter(outputFileName, true));
			            	for(int idx = 0 ; idx<9 ; idx++) {
			            		out.write(result[idx]);
			            		out.newLine();
			            	}
			            	out.close();
						}
		            }
		        } else {
		        	System.out.println("No files found");
		        }
		    }
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		} catch (IOException e) {
			System.out.println("Failed reading the files");
		}	    
	}
	
}
