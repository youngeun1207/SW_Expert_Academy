import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * SWEA D4 1238. [S/W 문제해결 기본] 10일차 - Contact
 * @author youngeun
 *
 * <BFS>
 * 전형적인 BFS
 */
public class Contact {
    static int T, N, S, to, from, answer;
    static Map<Integer, ArrayList<Integer>> graph;
    static boolean[] visited;
    static Queue<int[]> queue;
    public static void main(String[] args) throws IOException {
        T = 10;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        for(int tc = 1; tc <= 10; tc++){
            StringTokenizer st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            S = Integer.parseInt(st.nextToken());
            graph = new HashMap<>();
            st = new StringTokenizer(br.readLine());
            // 그래프 해시맵으로 저장
            for(int i = 0; i < N/2; i++){
                from = Integer.parseInt(st.nextToken());
                to = Integer.parseInt(st.nextToken());
                graph.putIfAbsent(from, new ArrayList<>());
                graph.get(from).add(to);
            }
            // 방문 배열
            visited = new boolean[100+1];

            // BFS
            queue = new ArrayDeque<>();
            queue.offer(new int[] {S, 1}); // 시작 노드부터 시작
            visited[S] = true;
            int prev = 0;
            while (!queue.isEmpty()){
                int[] node = queue.poll();
                if(prev != node[1]){ // 새로운 너비 시작
                    answer = 0; // 정답 초기화
                    prev = node[1]; // 새로운 너비 셋팅
                }
                answer = Math.max(answer, node[0]); // 가장 숫자가 큰 노드
                if(graph.containsKey(node[0])){
                    for(int n : graph.get(node[0])){
                        if(!visited[n]){ // 방문하지 않은 노드만 큐에 넣기
                            queue.offer(new int[] {n, node[1]+1}); // 너비 +1
                            visited[n] = true;
                        }
                    }
                }
            }
            sb.append("#" + tc + " " + answer + "\n");
        }
        System.out.println(sb);
    }
}
