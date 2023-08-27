import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 백준 1937 욕심쟁이 판다
 * @author youngeun
 *
 * 41836 KB
 * 432 ms
 *
 * <DFS + DP>
 *     DFS로 판다가 이동 가능할 때 까지 이동
 *     단, 한번 이동한 칸을 DP 메모이제이션으로 저장해서 시간초과 방지
 *     DP에 값 갱신된 칸으로 이동할 경우, 해당 칸에 저장된 이동 거리와 자기 자신(+1)을 더한 값이 이동 거리임
 */
public class GreedyPanda {
    static int N, answer = 0;
    static long[][] forest;
    static int[][] dp, dirs = new int[][] {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; // 상하좌우
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());

        // 지도 저장
        forest = new long[N][N];
        StringTokenizer st;
        for(int i = 0; i < N; i++){
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j < N; j++){
                forest[i][j] = Long.parseLong(st.nextToken());
            }
        }
        // DFS + DP
        dp = new int[N][N];
        for(int r = 0; r < N; r++){
            for(int c = 0; c < N; c++){
                if(dp[r][c] == 0) {
                    answer = Math.max(answer, DFS(r, c)); // 모든 칸 중 최대 이동 가능한 칸이 정답
                }
            }
        }
        System.out.println(answer);
    }

    /**
     * DFS 돌면서 각 칸마다 몇 번 움직일 수 있는지 확인
     * 이 때, dp 배열에 움직일 수 있는 칸 수 저장해두기(메모이제이션)
     * @param r
     * @param c
     * @return
     */
    static int DFS(int r, int c){
        if(dp[r][c] != 0){ // 해당 칸은 이동 거리 계산이 되어있음 -> 다시 DFS로 계산할 필요 없이 해당 칸 이동 횟수 바로 return
            return dp[r][c];
        }

        dp[r][c] = 1; // 확인하지 않은 칸은 1로 초기화

        int newR, newC;
        for(int[] dir : dirs){ // 상하좌우 4방향 탐색
            newR = r + dir[0];
            newC = c + dir[1];
            if(isInRange(newR, newC, forest[r][c])){
                // 상하좌우 중 선택했을 때 최대 값을 저장해야 함. -> Math.max로 4번 돌면서 그 중 최대값 선택
                dp[r][c] = Math.max(dp[r][c], DFS(newR, newC) + 1); // 재귀 + dp 최대값 갱신
            }
        }
        return dp[r][c];
    }

    /**
     * 다음 칸으로 이동할 수 있는지 검증
     * @param r
     * @param c
     * @param prev 직전 대나무 개수
     * @return
     */
    static boolean isInRange(int r, int c, long prev){
        // 칸을 넘어가지 않으면서, 이전 칸보다 현재 칸의 대나무 수가 더 많을 때 이동 가능
        return 0 <= r && r < N && 0 <= c && c < N && forest[r][c] > prev;
    }
}
