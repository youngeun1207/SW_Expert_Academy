import java.util.*;

/**
 * 프로그래머스 Lv.3 양과 늑대
 * 2022 KAKAO BLIND RECRUITMENT 기출
 * 15.06ms, 84.9MB
 *
 * DFS
 * 양: 0 늑대: 1
 * 양이 늑대보다 많아야 자식 노드로 이동
 * 분기 포인트로 지정한 부모 노드 - visited = True
 * 분기 포인트에서 계속 밑으로 서브트리 순회하기
 */
class SheepAndWolf {
    static Map<Integer, List<Integer>> graph;
    static boolean[] visited;
    static List<Integer> answer;
    static int[] nodeInfo;

    public int solution(int[] info, int[][] edges) {
        graph = new HashMap<>();
        visited = new boolean[info.length];
        nodeInfo = info;
        answer = new ArrayList<>();

        for (int[] edge : edges) { // 부모 노드 먼저 검증해서 자식 노드 검증 시간 줄이는 용으로 HashMap에 그래프 옮겨담기
            graph.putIfAbsent(edge[0], new ArrayList<>()); // 파이썬 defaultdict와 동일한 역할
            graph.putIfAbsent(edge[1], new ArrayList<>());
            graph.get(edge[0]).add(edge[1]);
            graph.get(edge[1]).add(edge[0]);
        }

        visited[0] = true; // root node: 0번 노드
        DFS(1, 0);

        int maxSheep = 0;

        for (int numSheep : answer) {
            maxSheep = Math.max(maxSheep, numSheep);
        }

        return maxSheep;
    }

    private void DFS(int numSheep, int numWolf) {
        if (numSheep > numWolf) { // 갈 수 있어
            answer.add(numSheep);
        } else { // 잡아먹힌다!
            return;
        }

        for (Map.Entry<Integer, List<Integer>> entry : graph.entrySet()) { // 부모, 자식 노드 둘 다 검증해야하므로 entrySet으로 받음
            // key: 부모 노드, value: 자식 노드 리스트
            if(visited[entry.getKey()]){ // 서브트리 순회 할 분기 포인트인지 확인
                for(int childNode : entry.getValue()){
                    if (!visited[childNode]) {
                        visited[childNode] = true; // 아래로 뻗어나갈 분기 포인트 체크
                        if (nodeInfo[childNode] == 0) {
                            DFS(numSheep + 1, numWolf);
                        } else {
                            DFS(numSheep, numWolf + 1);
                        }
                        visited[childNode] = false; // 서브트리 다 순회했음
                    }
                }
            }
        }
    }
}
