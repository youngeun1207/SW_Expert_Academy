import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import static java.lang.System.exit;

/**
 * 백준 17834 사자와 토끼
 * @author youngeun
 *
 * 165584 kb
 * 916 ms
 *
 * <이분 그래프>
 * 사자와 토끼가 그래프 상 엣지 %2 = 0 만큼 떨어져있으면 잡아먹힘 -> 홀수로 떨어지는 경우의 수 구하기
 * => 이분 그래프 알고리즘을 사용해서 정점을 2그룹으로 나누자!
 *     1. DFS로 색 번갈아가며 찍어주기
 *     2. 만약 방문했던 곳인데 색이 이전 노드와 같으면 이진그래프 만들 수 없는 경우임
 *         ex) 1  ㅡ  2  1R -> 2B -> 3R -> 1R(같은 색!!) 이진 그래프 실패 -> 모든 경우 잡아먹힘
 *              \  /
 *                3
 *     3. 만약 이진그래프 만들 수 있으면
 *         a. 서로 다른 색 고르는 경우의 수 * 2(사자, 토끼 위치 바뀌어도 동일함)
 *         b. nC2 = (n * n-1) / 2
 */
public class LionAndRabbit {
    static Node[] nodes;
    static int N, M;
    static boolean[] visited;
    static char[] colors = {'R', 'B'};
    static int[] colorCnt = {1, 0};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        // 그래프 만들기
        int v1, v2;
        Node node;
        nodes = new Node[N+1];
        for(int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            v1 = Integer.parseInt(st.nextToken());
            v2 = Integer.parseInt(st.nextToken());
            if(nodes[v1] == null) {
                node = new Node(v1);
                nodes[v1] = node;
            }
            if(nodes[v2] == null) {
                node = new Node(v2);
                nodes[v2] = node;
            }
            nodes[v1].adj.add(v2);
            nodes[v2].adj.add(v1);
        }
        // dfs
        visited = new boolean[N+1];
        visited[1] = true;
        nodes[1].color = 'R';
        DFS(0, nodes[1]);
        System.out.println(colorCnt[0] * colorCnt[1] * 2);
    }

    static void DFS(int prev, Node node) {
        int color;
//        System.out.println(node.n + "===========");
        for(int adj : node.adj) {
//            System.out.println(node.n + " " + adj + " " + node.color + " " + nodes[adj].color);
            if(!visited[adj]) {
                visited[adj] = true;
                color = (prev + 1)%2;
                nodes[adj].color = colors[color];
                colorCnt[color]++;
                DFS(color, nodes[adj]);

            }else if(nodes[adj].color == colors[prev]) { // 이분 그래프 아님!
                System.out.println("0");
                exit(0);
            }
        }
    }

    static class Node{
        int n;
        char color; // R or B
        List<Integer> adj;

        public Node(int n) {
            this.adj = new ArrayList<>();
            this.n = n;
        }

    }
}