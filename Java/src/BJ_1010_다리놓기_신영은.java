package algorithm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 백준 1010 다리놓기
 * @author youngeun
 * 
 * 13868 KB	
 * 116 ms
 *
 * mCn을 DP로 구함..(파스칼의 삼각형)
 */
public class BJ_1010_다리놓기_신영은 {
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		for(int t = 0; t < T; t++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int N = Integer.parseInt(st.nextToken());
			int M = Integer.parseInt(st.nextToken());
			System.out.println(bino(M ,N));
		}
		
	}
	static long bino(int M, int N){
		long[][] B = new long[M+1][N+1];
		
		for(int i = 0; i <= M; i++) {
			for(int j = 0, end = Math.min(i, N); j <= end; j++) {
				if(j==0 || i==j) {
					B[i][j] = 1;
					continue;
				}
				B[i][j] = B[i-1][j-1] + B[i-1][j];
			}
		}
		return B[M][N];
	}
}
