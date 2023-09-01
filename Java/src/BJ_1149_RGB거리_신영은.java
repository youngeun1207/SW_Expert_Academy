package algorithm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;
/**
 * 백준 1149 RGB 거리
 * @author youngeun
 * 
 * 12124 KB	
 * 88 ms
 * 
 * 집을 순서대로 3가지 색으로 칠하면서, 직전 dp 저장된 cost 중, 
 * 1. 나와 색이 다르고 
 * 2. cost가 더 작은 쪽 선택 
 * 	- dp[i][Red] += min(dp[i-1][Green], dp[i-1][Blue])
 * 	- dp[i][Green] += min(dp[i-1][Red], dp[i-1][Blue])
 * 	- dp[i][Blue] += min(dp[i-1][Green], dp[i-1][Red])
 * 종료 후 min(dp[N-1])이 최소 비용
 * 
 */
public class BJ_1149_RGB거리_신영은 {
	static int N;
	static int[][] dp;
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		
		dp = new int[N][3];
		for(int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			dp[i][0] = Integer.parseInt(st.nextToken());
			dp[i][1] = Integer.parseInt(st.nextToken());
			dp[i][2] = Integer.parseInt(st.nextToken());
		}
		
		// dp수행
		for(int i = 1; i < N; i++) {
			dp[i][0] += Math.min(dp[i-1][1], dp[i-1][2]);
			dp[i][1] += Math.min(dp[i-1][0], dp[i-1][2]);
			dp[i][2] += Math.min(dp[i-1][0], dp[i-1][1]);
		}
		Arrays.sort(dp[N-1]);
		System.out.println(dp[N-1][0]);
	}
}
