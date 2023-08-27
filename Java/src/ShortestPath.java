import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * 백준 1753 최단경로
 * @author youngeun
 *
 * 118036 KB
 * 660 ms
 *
 * 우선순위 큐를 사용한 다익스트라
 */
public class ShortestPath {
    static int V, E, S;
    static int[] costs;
    static ArrayList<Node>[] graph;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();
        V = Integer.parseInt(st.nextToken());
        E = Integer.parseInt(st.nextToken());
        S = Integer.parseInt(br.readLine());

        graph = new ArrayList[V+1];

        for (int i = 1; i <= V; i++) {
            graph[i] = new ArrayList<>();
        }

        for(int e = 0; e < E; e++) {
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            int weight = Integer.parseInt(st.nextToken());

            graph[from].add(new Node(to, weight));
        }

        costs = new int[V+1];
        Arrays.fill(costs, Integer.MAX_VALUE);

        PriorityQueue<Node> queue = new PriorityQueue<>();
        queue.add(new Node(S, 0));
        costs[S] = 0;
        boolean[] visited = new boolean[V + 1];

        while (!queue.isEmpty()) {
            Node node = queue.poll();
            if(visited[node.to]) continue;
            visited[node.to] = true;

            for(Node next : graph[node.to]) {
                if(costs[next.to] > costs[node.to] + next.weight) {
                    costs[next.to] = costs[node.to] + next.weight;
                    queue.add(new Node(next.to, costs[next.to]));
                }
            }
        }

        for(int i = 1; i <= V; i++) {
            if(costs[i] == Integer.MAX_VALUE) {
                sb.append("INF\n");
                continue;
            }
            sb.append(costs[i] + "\n");
        }
        System.out.println(sb);
    }

    static class Node implements Comparable<Node>{
        int to;
        int weight;

        public Node(int to, int weight) {
            this.to = to;
            this.weight = weight;
        }

        @Override
        public int compareTo(Node o) {
            return this.weight - o.weight;
        }
    }
}

