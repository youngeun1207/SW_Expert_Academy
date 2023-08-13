import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * SWEA 1949. [모의 SW 역량테스트] 등산로 조성
 * @author youngeun
 *
 * 20,552 kb
 * 145 ms
 *
 * DFS
 * (3 ≤ N ≤ 8), (1 ≤ K ≤ 5), 가장 높은 봉우리는 최대 5개 => 완탐으로 풀 수 있다.
 * 최고 봉우리 찾은 후 DFS 돌리기
 * 단, 1~K 만큼 1회 깎을 수 있으므로 지도 돌면서 한 칸씩 1~K만큼 깎기
 * 시간복잡도 O(N^2 * K * 5)
 */
public class HikingTrail {

    static int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; // 상 하 좌 우
    static int T, N, K, max, answer, startX, startY;
    static int[][] field;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        T = Integer.parseInt(br.readLine());

        for (int tc = 1; tc <= T; tc++) {
            st = new StringTokenizer(br.readLine());

            N = Integer.parseInt(st.nextToken());
            K = Integer.parseInt(st.nextToken());

            field = new int[N][N]; // 지도 정보 저장
            max = 0;
            answer = 0;

            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());

                for (int j = 0; j < N; j++) {
                    field[i][j] = Integer.parseInt(st.nextToken());
                    max = Math.max(max, field[i][j]); // 가장 높은 봉우리 저장
                }
            }

            for (int x = 0; x < N; x++) {
                for (int y = 0; y < N; y++) {
                    if (field[x][y] == max) { // 가장 높은 봉우리 발견!
                        // DFS 시작점 셋팅
                        startX = x;
                        startY = y;
                        construction(); // 공사 돌리기
                    }
                }
            }

            sb.append("#" + tc + " " + answer + "\n");
        }

        System.out.print(sb);
    }

    /**
     * DFS 돌리기 전, 지도 깎는 메소드
     * 지점 당 1~K만큼 깎아주고 난 후, 깎은것 원복해주기
     */
    static void construction() {
        for (int x = 0; x < N; x++) {
            for (int y = 0; y < N; y++) {
                for (int depth = 0; depth < K; depth++) { // 최대 K 깊이이므로 1부터 K 깊이까지 모두 확인하기
                    field[x][y]--; // 깎기
                    DFS(startX, startY, 1); // 시작점은 최고 봉우리
                }
                field[x][y] += K; // 지도 원복
            }
        }
    }

    /**
     * 현재 노드보다 더 낮은 길로 가는 DFS
     * @param x
     * @param y
     * @param dist : 이동한 깊이
     */
    static void DFS(int x, int y, int dist) {
        answer = Math.max(answer, dist); // 최대 깊이 갱신
        for (int i = 0; i < 4; i++) {
            int newX = x + dirs[i][0];
            int newY = y + dirs[i][1];
            
            if (isInRange(newX, newY) && field[x][y] > field[newX][newY]) { // 지도 범위 안 벗어남 + 더 낮은 길
                DFS(newX, newY, dist + 1); // 한칸 더 이동한 것이므로 dist+1
            }
        }
    }

    /**
     * 지도 범위 벗어났는지 확인하는 함수
     * @param x
     * @param y
     * @return
     */
    static boolean isInRange(int x, int y){
        if(x >= 0 && x < N && y >= 0 && y < N){
            return true;
        }
        return false;
    }
}
