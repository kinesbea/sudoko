package com.sudoko;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;

public class SudokoProblemSolver {

	public static void main (String[] args) {
		System.out.println("start");
		

	    File file = new File("./data");
	    System.out.println(file.getAbsolutePath());
	    
	    if (file.isDirectory()) {
	        File[] files = file.listFiles(new FilenameFilter() {
	            public boolean accept(File dir, String fileName) {
	              return (fileName.endsWith(".TXT") || fileName.endsWith(".txt")); 
	            }
	        });    	        
	        
	        if (files != null && files.length > 0) {
	            for (File f : files) {
	            	System.out.println(f.getAbsolutePath());
	            	String[] matrix = new String[9];
	            	try {
						FileReader fr = new FileReader(f);
						String numberLine = "";
						int i;
						while ((i = fr.read()) != -1) {
							numberLine += (char) i;				
						}						
						matrix = numberLine.split("\\r\\n");
						System.out.println(numberLine);
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	            	
					Solver solver = new Solver();
					String[] result = solver.process(matrix);
					System.out.println("---------");
	            	for(int i = 0 ; i<9 ; i++) {
	            		System.out.println(result[i]);
	            	}
	            	
	            	System.out.println();
	            	System.out.println();
	            	System.out.println();
	            	//break;
	            }
	        }
	    }
	}
	
}
