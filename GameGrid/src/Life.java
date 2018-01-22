import java.awt.Point;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import processing.core.PApplet;

/*

	Represents a Game Of Life grid.

	Coded by:
	Modified on:

*/

public class Life {
	
	private char[][] grid;
	// Constructs an empty grid
	public Life() {
		grid = new char [20][20];
	}

	// Constructs the grid defined in the file specified
	public Life(String filename) {
		grid = new char[20][20];
		readData(filename, grid);
	}

	// Runs a single turn of the Game Of Life
	public void step() {
		char[][] copy = new char [20][20];
		for (int i = 0; i < grid.length; i++) {
			for(int j = 0; j < grid[i].length; j++) {
				copy[i][j] = grid[i][j];
			}
		}
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				int neighbors = findNeighbors(i,j);
				if(grid[i][j] == ' ' && neighbors == 3) { 
					copy[i][j] = '*';
				}
				else if(grid[i][j] == ' ' && (neighbors <= 1 || neighbors >= 4)) {
					copy[i][j] = ' ';
					
				}
				

			}
		}
		grid = copy;

	}

	// Runs n turns of the Game Of Life
	public void step(int n) {
		
		//checking for neighbors
		
		
	}
	
	public int findNeighbors(int x, int y) {
		//FIX - ADD IN WHAT HAPPENS AT X = Last one but Y is anything 
		// 		Add in what happens at X = 0, but y is anything
		int count = 0;
		
		
		if(x+1 < grid.length-1) {
			if(grid[x+1][y] == '*') {
				count++;
			}
			
			if(y+1 < grid[0].length - 1) {
				if(grid[x+1][y+1] == '*') {
					count++;
				}
				if(grid[x][y+1] == '*') {
					count++;
				}
			}
			if(y-1 > 0) {
				if(grid[x][y-1] == '*') {
					count++;
				}
				if(grid[x+1][y-1] == '*') {
					count++;
				}
			}
		}
		if(x-1 > 0) {
			if(grid[x-1][y] == '*') {
				count++;
			}
			
			if (y+1 < grid[0].length- 1) {
				if(grid[x-1][y+1] == '*') {
					count++;
				}
				
			}
			if(y-1 > 0) {
				if(grid[x-1][y-1] == '*') {
					count++;
				}
				
			
		}

	
		
		}
		
		//for (int x = Math.max(i-1,0); x<= Math.min(i+1, grid.length-1); x++) {
		//	for (int y = Math.max(j, -1,0); y<= Math.min(j+1, grid[i].length-1); y++) {
		// if (!(x == i && y == j) && grid[x][y]) {
		// count++ }
		//	}
		// }
		return count;

	}
		
		
				
	
	
	// Formats this Life grid as a String to be printed (one call to this method returns the whole multi-line grid)
	public String toString() {
		String response = "";
		
		for (int i = 0; i < grid[0].length;i++) {
			for (int j = 0; j < grid.length; j ++) {
				if (grid[j][i] == '*') {
					response += '*';
				}
				else {
					response +=  ' ';
				}
			}
			response += "\n"; // to separate rows
		}
		
		return response;
	}

	// Reads in array data from a simple text file containing asterisks (*)
	public void readData (String filename, char[][] grid2) {
		File dataFile = new File(filename);

		if (dataFile.exists()) {
			int count = 0;

			FileReader reader = null;
			Scanner in = null;
			try {
					reader = new FileReader(dataFile);
					in = new Scanner(reader);

					while (in.hasNext()) {
						String line = in.nextLine();
						for(int i = 0; i < line.length(); i++)
							if (i < grid2.length && count < grid2[i].length && line.charAt(i)=='*')
								grid2[i][count] = '*';

						count++;
					}
			} catch (IOException ex) {
				throw new IllegalArgumentException("Data file " + filename + " cannot be read.");
			} finally {
				if (in != null)
					in.close();
			}
			
		} else {
			throw new IllegalArgumentException("Data file " + filename + " does not exist.");
		}
    }
	
	
	
	
	
	
	
	/**
	 * Optionally, complete this method to draw the grid on a PApplet.
	 * 
	 * @param marker The PApplet used for drawing.
	 * @param x The x pixel coordinate of the upper left corner of the grid drawing. 
	 * @param y The y pixel coordinate of the upper left corner of the grid drawing.
	 * @param width The pixel width of the grid drawing.
	 * @param height The pixel height of the grid drawing.
	 */
	public void draw(PApplet marker, float x, float y, float width, float height) {
		
		marker.pushStyle();
		
		float cellWidth = width / grid.length;
		float cellHeight = height / grid[0].length;

	//	marker.stroke(0);
		
		for (int i = 0; i < grid[0].length;i++) {
			for (int j = 0; j < grid.length; j ++) {
				if (grid[j][i] == '*') {
				//	marker.text('a');
					marker.text('*', cellHeight/4, cellWidth/2);
				}
				else {
					marker.fill(255);
					
				}
				marker.rect(cellWidth*j + x, cellHeight*i + y, cellWidth, cellHeight);

			}
		}

		marker.popStyle();
		
	}
	
	/**
	 * Optionally, complete this method to determine which element of the grid matches with a
	 * particular pixel coordinate.
	 * 
	 * @param p A Point object representing a graphical pixel coordinate.
	 * @param x The x pixel coordinate of the upper left corner of the grid drawing. 
	 * @param y The y pixel coordinate of the upper left corner of the grid drawing.
	 * @param width The pixel width of the grid drawing.
	 * @param height The pixel height of the grid drawing.
	 * @return A Point object representing a coordinate within the game of life grid.
	 */
	public Point clickToIndex(Point p, float x, float y, float width, float height) {
		float cellWidth = width / grid.length;
		float cellHeight = height / grid[0].length;

		int j = (int) ((p.x - x) / cellWidth);
		int i = (int) ((p.y - y) / cellHeight);

		if(j < 0 || j > grid.length-1) {
			return null;
		}
		if (i < 0 || i > grid[0].length - 1) {
			return null;
		}
		Point answer = new Point(j,i);

		return answer;
	}

	/**
	 * Optionally, complete this method to toggle a cell in the game of life grid
	 * between alive and dead.
	 * 
	 * @param i The x coordinate of the cell in the grid.
	 * @param j The y coordinate of the cell in the grid.
	 */
	public void toggleCell(int i, int j) {
		if (grid[i][j] == ' ') {
		grid[i][j] = '*';
		}
		else {
			grid[i][j] = ' ';
		}
	}
	
	
}