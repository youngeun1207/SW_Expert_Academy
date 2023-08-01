import java.io.BufferedReader;
import java.io.InputStreamReader;
/**
 * 백준 2615 오목
 * @author youngeun
 * 16108kb
 * 148ms
 * 
 * 단순구현
 * 6목 주의
 */
public class Omok {

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String [][] grid = new String[19][19];
		for(int i = 0; i < 19; i++) {
			grid[i]=br.readLine().split(" ");
		}
		int[][] moves = {{-1, 1}, {0, 1}, {1, 1}, {1, 0}}; // 오위, 오, 오아, 아
		boolean win = false;
		Loop:
		for(int row = 0; row < 19; row++) {
			for(int col = 0; col < 19; col++) {
				if(!grid[row][col].equals("0")) {
					String prev = grid[row][col];
					for(int[] move : moves) {
						int i = 1;
						Move:
						
						for(; i <= 4; i++) {
							int newRow = row + (move[0]*i);
							int newCol = col + (move[1]*i);
							
							if(0 <= newRow && 19 > newRow && 0 <= newCol && 19 > newCol) {
								if(!grid[newRow][newCol].equals(prev)) {
									break;
								}
							}else {
								break;
							}
						}
						if(i == 5) {
							int newRow = row + (move[0]*5);
							int newCol = col + (move[1]*5);
							
							if(0 <= newRow && 19 > newRow && 0 <= newCol && 19 > newCol && grid[newRow][newCol].equals(prev)) {
								continue;
							}
							newRow = row - move[0];
							newCol = col - move[1];
							if(0 <= newRow && 19 > newRow && 0 <= newCol && 19 > newCol && grid[newRow][newCol].equals(prev)) {
								continue;
							}
							System.out.println(prev);
							System.out.println((row+1) + " " + (col+1));
							win = true;
							break Loop;
						}
					}
				}
			}
		}
		if(! win) {
			System.out.println("0");
		}
	}
}

