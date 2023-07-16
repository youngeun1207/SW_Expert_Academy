import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

/**
 * SWEA D4 7465
 * 창용 마을 무리의 개수
 * @author youngeun
 *
 * DFS로 풀었음
 * 양방향 그래프로 저장 후 DFS 돌려서 같은 무리인지 확인함.
 */
class D4_7465
{
    static int N, M;
    static List<List<Integer>> people;
    static boolean[] visited;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int T = scanner.nextInt();

        for (int tc = 1; tc <= T; tc++) {
            N = scanner.nextInt();
            M = scanner.nextInt();

            people = new ArrayList<>();
            for (int i = 0; i <= N; i++) {
                people.add(new ArrayList<>());
            }

            for (int i = 0; i < M; i++) {
                // 서로 아는 사이이므로 양방향 간선 그래프
                int p1 = scanner.nextInt();
                int p2 = scanner.nextInt();
                people.get(p1).add(p2);
                people.get(p2).add(p1);
            }

            int res = 0;
            visited = new boolean[N + 1];

            for (int i = 1; i <= N; i++) {
                if (visited[i]) { // visited면 이미 그룹에 들어가있는 것
                    continue;
                }
                dfs(i);
                res++;
            }

            System.out.println("#" + tc + " " + res);
        }
    }

    static void dfs(int node) {
        visited[node] = true;

        for (int friend : people.get(node)) {
            if (!visited[friend]) {
                dfs(friend);
            }
        }
    }
}