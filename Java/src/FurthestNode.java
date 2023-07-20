import java.util.*;

/**
 * 프로그래머스 Lv.3 가장 먼 노드
 *
 * 시간 67.55ms
 * 메모리 116MB
 *
 * BFS와 다익스트라 알고리즘으로 1번 노드로부터 각 노드 간의 최단거리를 구함
 * 노드까지 도달하는 데 이동한 거리를 visited에 저장한 후, 가장 큰 값의 갯수를 셈
 *
 * BFS를 위해 Queue 라이브러리 사용
 * 입력으로 주어진 edge 배열을 양방향 간선그래프로 변환하기 위해 HashMap 사용
 */
class FurthestNode {
    static final int INF = Integer.MAX_VALUE;

    public int solution(int n, int[][] edge) {
        int[] visited = new int[n + 1]; // 노드 별 최단 거리 저장
        Arrays.fill(visited, INF); // MAX_INT로 초기화

        Map<Integer, List<Integer>> graph = new HashMap<>();
        for (int[] e : edge) {
            // putIfAbsent -> python의 defaultdict(list)와 동일하게 Map의 Value를 빈 Array로 초기화
            graph.putIfAbsent(e[0], new ArrayList<>());
            graph.putIfAbsent(e[1], new ArrayList<>());
            // 양방향 그래프로 저장
            graph.get(e[0]).add(e[1]);
            graph.get(e[1]).add(e[0]);
        }

        visited[1] = 0; // 1번 노드부터 시작

        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{0, 1}); // (distance, node)
        int maxDist = 0; // 가장 먼 노드까지 도달하는데 이동한 거리 저장

        // BFS + 다익스트라
        while (!queue.isEmpty()) {
            int[] pair = queue.poll();
            int dist = pair[0];
            int node = pair[1];
            maxDist = Math.max(maxDist, dist); // 기존 maxDist보다 현재 노드가 더 멀면 업데이트

            // 다익스트라 -> 현재 노드에 저장된 거리가 새로운 경로 이동거리보다 작으면 더 볼 필요 없이 최단거리 탈락
            if (visited[node] < dist) {
                continue;
            }

            for (int sibling : graph.get(node)) {
                if (dist + 1 < visited[sibling]) { // 시간 효율을 위해 queue에 넣을 때 부터 최단거리 후보가 아니면 제외시킴
                    queue.offer(new int[]{dist + 1, sibling}); // dist: 직전 노드 + 1칸 더 이동함
                    visited[sibling] = dist + 1;
                }
            }
        }

        // 가장 먼 노드 갯수 세기
        int answer = 0;
        for (int i = 1; i <= n; i++) {
            if (visited[i] == maxDist) {
                answer++;
            }
        }

        return answer;
    }
}
