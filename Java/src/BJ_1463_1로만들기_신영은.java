package algorithm;

import java.util.Scanner;
/**
 * 백준 1463 1로 만들기
 * @author youngeun
 * 
 * 16832 KB
 * 128 ms
 * 
 * Bottom-Up으로 DP
 */
public class BJ_1463_1로만들기_신영은 {
	static int N;
	static int[] dp;
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		dp = new int[N+1];
		
		for(int i = 2; i <= N; i++) {
			dp[i] = dp[i-1]+1; // +1은 기본으로 하는 연산
			if(i%2 == 0) // 2로 나누어지면
				dp[i] = Math.min(dp[i/2]+1, dp[i]);
			if(i%3 == 0) // 3으로 나누어지면
				dp[i] = Math.min(dp[i], dp[i/3]+1);
		}
		System.out.println(dp[N]);
	}
}
