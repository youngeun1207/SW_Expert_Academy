package algorithm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 백준 17070 파이프 옮기기 1
 * @author youngeun
 * 
 * 11644 KB
 * 88 ms
 * 
 * <DP>
 * 1. 현재 칸에 도달할 수 있는 모양의 가짓수는 총 3가지 -> 가로, 세로, 대각선 모양
 * 	- 가로: 나의 왼쪽 칸에 가로 혹은 대각선 모양 파이프가 있어야 함
 *	- 세로: 나의 윗 칸에 세로 혹은 대각선 모양 파이프가 있어야 함
 * 	- 대각: 나의 대각선 칸에 가로, 세로, 대각선 모양 파이프가 있어야 함 + 나의 왼, 위 칸이 비어야 함
 * 
 * 2. 파이프를 2칸으로 생각하지 않고 파이프의 끝부분(도 착점에 도달해야 하는 부분)만 기록
 * 	- 고로 첫 열은 모두 0으로 초기화 된다.
 * 
 * 3. 현재 도달한 모양의 가짓수만큼 배열을 만들어서 관리
 * 	- 0: 가로로 도달한 경우의 수
 *	- 1: 세로로 도달한 경우의 수
 *	- 2: 대각선으로 도달한 경우의 수
 *	=> 이렇게 저장을 해야 직전 모양을 알 수 있다.
 */
public class BJ_17070_파이프옮기기1_신영은 {
	static int N;
	static int[][] map;
	static int[][][] dp;
	static int[][] dirs = new int[][] {{0, -1}, {-1, 0}, {-1,-1}}; // 왼, 위, 대각선왼쪽위
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		
		map = new int[N+1][N+1];
		for(int i = 1; i <= N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			for(int j = 1; j <= N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		// DP 수행
		// 현재 칸이 0(빈 공간)이면 현재 나의 위치에서 올 수 있는 경우 구하기
		// 0.가로		1.세로	2.대각선
		// 만약 해당 위치의 해당 인덱스가 가능한 경우면 값 들고 내려오기
		dp = new int[N+1][N+1][3];
		dp[1][2][0] = 1; // 초기 파이프 위치
		for(int i = 1; i <= N; i++) {
			for(int j = 2; j <= N; j++) { // 파이프 길이가 2칸이기 때문에 맨 첫 열은 불가능하다..
				if((i==1 && j==2) || map[i][j] == 1) continue; // 처음 초기화 한 값은 변경ㄴㄴ + 벽에는 올 수 없어
				// 현재 위치에 올 수 있는 모양의 경우의 수는 총 3가지
				// 0. 가로모양으로 도착하려면		-> 왼쪽에 가로모양 + 왼쪽에 대각선모양
				dp[i][j][0] = dp[i][j-1][0] + dp[i][j-1][2];
				
				// 1. 세로모양으로 도착하려면		-> 위에 세로모양 + 위에 대각선모양
				dp[i][j][1] = dp[i-1][j][1] + dp[i-1][j][2];
				
				// 2. 대각선모양으로 도착하려면	-> 대각선 위에 가로모양 + 대각선 위에 세로모양 + 대각선 위에 대각선모양
				// 단 대각선 모양일 경우, 현재 칸의 위 + 왼쪽이 0이어야 함!
				if(map[i-1][j] + map[i][j-1] == 0) {
					dp[i][j][2] = dp[i-1][j-1][0] + dp[i-1][j-1][1] + dp[i-1][j-1][2];
				}
			}
		}
		// 세가지 모양 경우 합치기
		System.out.println(dp[N][N][0] + dp[N][N][1] + dp[N][N][2]);
	}

}
