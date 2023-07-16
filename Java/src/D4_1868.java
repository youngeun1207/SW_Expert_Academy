import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Scanner;

/**
 * SWEA D4 1868 파핑파핑 지뢰찾기
 * @author youngeun
 *
 * BFS
 * 주변에 지뢰가 하나도 없는 곳 먼저 찾아야 함
 * 먼저 주변 8곳이 다 지뢰가 아닌 부분 찾기
 * 그 다음 BFS로 확산(8곳 모두 지뢰가 아니면 queue에 넣기)
 *
 * 0개가 아닌 부분은 0개인 곳 찾은 반복문 돌려서 단순 갯수 세기
 */
public class D4_1868 {
    static int N;
    static char[][] mine;
    static boolean[][] visited;
    static int[] add_x = {-1, -1, -1, 0, 1, 1, 1, 0};
    static int[] add_y = {-1, 0, 1, 1, 1, 0, -1, -1};

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int T = scanner.nextInt();

        for (int test_case = 1; test_case <= T; test_case++) {
            N = scanner.nextInt();
            mine = new char[N][N];
            visited = new boolean[N][N];

            for (int i = 0; i < N; i++) {
                String row = scanner.next();
                for (int j = 0; j < N; j++) {
                    mine[i][j] = row.charAt(j);
                }
            }

            int cnt = 0;
            Queue<int[]> queue = new ArrayDeque<>();

            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    // 주변 모두 지뢰가 아닌 부분 찾으면 BFS로 확산해나가기
                    if (mine[i][j] == '.' && checkEightSides(i, j)) {
                        queue.offer(new int[]{i, j});
                        BFS(queue);
                        cnt++;
                    }
                }
            }

            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    // 주변 지뢰가 0개가 아닌 부분들
                    if (mine[i][j] == '.') {
                        cnt++;
                    }
                }
            }

            System.out.println("#" + test_case + " " + cnt);
        }
    }

    static boolean checkEightSides(int now_x, int now_y) {
        // 주변에 지뢰가 0개인 부분 찾기
        for (int i = 0; i < 8; i++) {
            int new_x = now_x + add_x[i];
            int new_y = now_y + add_y[i];
            if (new_x >= 0 && new_x < N && new_y >= 0 && new_y < N) {
                if (mine[new_x][new_y] == '*') {
                    return false;
                }
            }
        }
        return true;
    }

    static void BFS(Queue<int[]> queue) {
        while (!queue.isEmpty()) {
            int[] pos = queue.poll();
            int now_x = pos[0];
            int now_y = pos[1];
            mine[now_x][now_y] = 'o'; // visited 체크

            for (int i = 0; i < 8; i++) {
                int new_x = now_x + add_x[i];
                int new_y = now_y + add_y[i];
                if (new_x >= 0 && new_x < N && new_y >= 0 && new_y < N && mine[new_x][new_y] == '.') {
                    mine[new_x][new_y] = 'o'; // visited 체크
                    if (checkEightSides(new_x, new_y)) { // 주변 노드도 근처 8곳이 다 지뢰가 아니면 queue에 넣음
                        queue.offer(new int[]{new_x, new_y});
                    }
                }
            }
        }
    }
}
