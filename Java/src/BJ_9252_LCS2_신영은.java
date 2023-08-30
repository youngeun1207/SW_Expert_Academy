package algorithm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

/**
 * 백준 9252 LCS2
 * @author youngeun
 * 
 * 16708 KB
 * 152 ms
 * 
 * DP를 이용한 LCS
 */
public class BJ_9252_LCS2_신영은 {
	static String s1, s2;
	static int[][] dp;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		s1 = br.readLine();
		s2 = br.readLine();
		
		dp = new int[s1.length()+1][s2.length()+1];
		
		for(int i = 1; i <= s1.length(); i++) {
			for(int j = 1; j <= s2.length(); j++) {
				if(s1.charAt(i-1) == s2.charAt(j-1)) { // 같은 글자면
					dp[i][j] = dp[i-1][j-1]+1; // 대각선 좌측 위에 저장된 값 + 1
				}
				else { // 다른 글자면
					dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]); // 왼쪽과 위 중 더 큰 쪽 가져오기
				}
			}
		}
		int answer = dp[s1.length()][s2.length()];
		System.out.println(answer);
		if(answer == 0) { // LCS length == 0이면 넘어감
			System.exit(0);
		}
		
		int sr = s1.length();
		int sc = s2.length();
		
		Stack<Character> stack = new Stack<>(); // 역순으로 찾기 때문에 stack 씀
		while(dp[sr][sc] > 0) { // 0이면 더이상 겹치는 문자 없다는 것!
			if(dp[sr][sc] != dp[sr-1][sc] && dp[sr][sc] != dp[sr][sc-1]) { // 왼쪽, 위쪽 모두 다름 -> 대각선 위에서 가져왔다는 뜻!
				stack.add(s1.charAt(sr-1)); // 해당 문자는 겹치는 문자이므로 출력
				sr--; // 대각선 왼쪽 위로 이동
				sc--;
				continue;
			}
			if(dp[sr][sc] == dp[sr-1][sc]) { // 위랑 같음 -> 위에서 가져옴
				sr--; // 위로 이동
			}else if(dp[sr][sc] == dp[sr][sc-1]) { // 왼쪽랑 같음 -> 왼쪽에서 가져옴
				sc--; // 왼쪽으로 이동
			}
		}
		
		while(!stack.isEmpty()) { // 역순으로 출력
			System.out.print(stack.pop());
		}
	}
}
