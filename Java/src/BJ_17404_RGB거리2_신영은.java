package algorithm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
/**
 * 백준 17404 RGB거리2
 * @author youngeun
 * 
 * 12196 KB
 * 88 ms
 * 
 * <DP>
 * 1. RGB거리1과 동일하게 진행하되, 맨 앞의 집 색을 미리 결정해두기
 * 	- 현재 선택한 색을 제외하고 DP의 0번 집을 input 최대값으로 저장해두면 다른 색을 선택할 일이 없어짐
 * 2. DP 순회 끝나고 마지막 N-1번째 저장된 값 중 맨 앞과 다른 경우만 대상으로 answer 갱신
 * 3. 이걸 시작점 R, G, B 3번 반복하면 된다.
 */
public class BJ_17404_RGB거리2_신영은 {
	static int N, answer = Integer.MAX_VALUE;
	static int[][] costs, dp;
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		
		costs = new int[N][3];
		for(int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			costs[i][0] = Integer.parseInt(st.nextToken());
			costs[i][1] = Integer.parseInt(st.nextToken());
			costs[i][2] = Integer.parseInt(st.nextToken());
		}
		
		// DP
		// 첫번째 집 색을 고정시키고 계산
		for(int first = 0; first < 3; first++) {
			dp = new int[N][3];
			// 첫번째 집은 first 빼고 Max값으로 초기화 -> 다른 색이 선택될 일이 없다
			for(int i = 0; i < 3; i++) {
				if(i != first) dp[0][i] = 1000;
				else dp[0][i] = costs[0][i];
			}
			
			// 2번째 집 부터 RGB거리와 동일하게 계산
			for(int i = 1; i < N; i++) {
				dp[i][0] = costs[i][0] + Math.min(dp[i-1][1], dp[i-1][2]);
				dp[i][1] = costs[i][1] + Math.min(dp[i-1][0], dp[i-1][2]);
				dp[i][2] = costs[i][2] + Math.min(dp[i-1][0], dp[i-1][1]);
			}
			
			// 마지막 집에서 first와 같은 행 제외하고 answer 갱신
			for(int i = 0; i < 3; i++) {
				if(i != first) answer = Math.min(answer, dp[N-1][i]);
			}
		}
		System.out.println(answer);

	}

}
