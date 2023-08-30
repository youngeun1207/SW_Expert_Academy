package algorithm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 백준 1600 말이 되고픈 원숭이
 * @author youngeun
 * 
 * 60144 KB
 * 576 ms
 * 
 * <BFS>
 * visited 배열을 저장할 때, 말처럼 움직인 횟수에 따라 별도의 visited 배열 저장함
 * 최단 경로이기 때문에 BFS 사용하였다.
 */
public class BJ_1600_말이되고픈원숭이_신영은 {
	static int K, W, H, min = Integer.MAX_VALUE, answer = -1;
	static boolean[][][] visited;
	static int[][] map;
	static int[][] dirs = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; // 평범하게 4방향
	static int[][] horse = new int[][]{{-2, -1}, {-1, -2}, {-2, 1}, {-1, 2}, {2, -1}, {1, -2}, {2, 1}, {1, 2}}; // 말처럼 8방향
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		K = Integer.parseInt(br.readLine());
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		W = Integer.parseInt(st.nextToken());
		H = Integer.parseInt(st.nextToken());
		
		// 지도 저장
		map = new int[H][W];
		visited = new boolean[H][W][K+1];
		for(int h = 0; h < H; h++) {
			st = new StringTokenizer(br.readLine());
			for(int w = 0; w < W; w++) {
				map[h][w] = Integer.parseInt(st.nextToken());
			}
		}
		
		// BFS
		Queue<int[]> queue = new ArrayDeque<>();
		queue.add(new int[] {0, 0, 0, 0}); // 현재 row, col, 말처럼 움직인 횟수, 움직인 횟수
		visited[0][0][0] = true;
		
		while(!queue.isEmpty()) {
			int[] node = queue.poll();
			
			if(node[0] == H-1 && node[1] == W-1) { // 도착점 도달
				answer = node[3];
				break;
			}
			
			int row, col;
			
			// 아직 말처럼 움직일 수 있음(횟수 남음)
			if(node[2] < K) { 
				for(int[] dir : horse) {
					row = node[0] + dir[0];
					col = node[1] + dir[1];
					if(isInRange(row, col, node[2]+1)) {
						queue.add(new int[] {row, col, node[2]+1, node[3]+1}); // 말처럼 이동한 횟수 추가
						visited[row][col][node[2]+1] = true; // 말처럼 이동한 횟수에 따라 visited 별도로 관리해야함!
					}
				}
			}
			// 평범하게 이동
			for(int[] dir : dirs) { 
				row = node[0] + dir[0];
				col = node[1] + dir[1];
				if(isInRange(row, col, node[2])) {
					queue.add(new int[] {row, col, node[2], node[3]+1}); // 이동 거리만 추가
					visited[row][col][node[2]] = true;
				}
			}
		}
		System.out.println(answer);
	}
	/**
	 * 이동 가능한지 확인
	 * @param h
	 * @param w
	 * @param k
	 * @return
	 */
	static boolean isInRange(int h, int w, int k) {
		// 말처럼 움직인 횟수가 동일한 visited 확인
		return 0<=h && h<H && 0<=w && w<W && !visited[h][w][k] && map[h][w]==0; 
	}

}
