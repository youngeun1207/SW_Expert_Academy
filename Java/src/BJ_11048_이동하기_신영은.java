package algorithm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 백준 11048 이동하기
 * @author youngeun
 * 
 * 80580 KB
 * 444 ms
 * 
 * 3 방향으로 올 수 있으므로, 현재 칸에 오기까지 가능한 이전 칸의 개수도 3개
 * 나의 직전 위치 후보 3개 중 최대값 + 자기 자신의 사탕 개수를 dp에 저장
 * 이거를 1,1 부터 N,M 까지 반복한다
 */
public class BJ_11048_이동하기_신영은 {
	static int N, M;
	static int[][] map, dp;
	static int[][] dirs = new int[][] {{-1, -1}, {0, -1}, {-1, 0}};
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		map = new int[N+1][M+1];
		dp = new int[N+1][M+1];
		for(int n = 1; n <= N; n++) {
			st = new StringTokenizer(br.readLine());
			for(int m = 1; m <= M; m++) {
				map[n][m] = Integer.parseInt(st.nextToken());
			}
		}
		
		for(int n = 1; n <= N; n++) {
			for(int m = 1; m <= M; m++) {
				// 3방향으로 갈 수 있으므로, 나의 직전 값이 될 수 있는 위, 왼, 대각선 위왼 확인해서 그 중 최대값 넣기
				int max = 0;
				for(int[]dir : dirs) {
					max = Math.max(max, dp[n+dir[0]][m+dir[1]]);
				}
				dp[n][m] = max + map[n][m]; // 자기 자신에 있는 사탕 값도 잊지 말고 추가해주자
			}
		}
		System.out.println(dp[N][M]);
		
		
	}

}
