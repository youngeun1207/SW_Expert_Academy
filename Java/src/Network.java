import java.util.LinkedList;
import java.util.Queue;

/**
 * 프로그래머스 Lv.3 네트워크
 * BFS로 그래프 노드 순회하면서 연결된 노드인지 확인함
 */
class Network {
    public int solution(int n, int[][] computers) {
        int answer = 0;
        Queue<Integer> queue = new LinkedList<>(); // BFS를 위한 queue
        boolean[] visited = new boolean[n]; // 방문 여부 체크

        for (int a = 0; a < n; a++) {
            if (!visited[a]) {
                queue.add(a);
                answer++; // 새로운 그룹 시작

                while (!queue.isEmpty()) { // 이어져있는 노드가 없을 때 까지 반복
                    int node = queue.poll();
                    for (int i = 0; i < n; i++) {
                        if (computers[node][i] == 1 && !visited[i]) { // 현재 노드와 이어져있고, 방문하지 않은 노드일 때
                            visited[i] = true;
                            queue.add(i); // queue에 넣어서 BFS 수행
                        }
                    }
                }
            }
        }
        return answer;
    }
}
