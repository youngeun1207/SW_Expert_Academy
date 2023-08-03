import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * SWEA D2 2001 파리 퇴치
 * 완전탐색
 * @author youngeun
 *
 * 5 <= N <= 15, 2<=M<=N
 * 단순 완전탐색(4중 for문) 시 WorstCase -> O(15^4) = 50,625
 * => 완탐으로 접근 가능한 문제
 */
public class KillFly {
    static int T;
    static int N, M;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        T = Integer.parseInt(br.readLine());
        for(int tc = 1; tc <= T; tc++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken()); // 격자
            M = Integer.parseInt(st.nextToken()); // 파리채

            int[][] flies = new int[N][N];

            for (int i = 0; i < N; i++){
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < N; j++){
                    flies[i][j] = Integer.parseInt(st.nextToken());
                }
            }
            int max = 0;
            for(int i = 0; i <= N-M; i++){
                for(int j = 0; j <= N-M; j++){
                    int add = 0;
                    for(int k = 0; k < M; k++){
                        for(int l = 0; l < M; l++){
                            add += flies[i+k][j+l];
                        }
                    }
                    max = Math.max(add, max);
                }
            }
            System.out.printf("#%d %d\n", tc, max);
        }
    }
}
