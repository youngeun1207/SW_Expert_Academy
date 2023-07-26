import java.util.*;

/**
 * 프로그래머스 Lv.3 여행경로
 * @author youngeun
 * 144.90ms, 161MB
 *
 * DFS + 백트래킹 문제
 * 해당 경로로 모든 티켓을 소진할 수 없는 경우 graph 상태를 원상복구 해서 백트래킹으로 다시 DFS 돌려야 함
 * 원상복구를 위해 graph와 order를 copy해서 넘김
 */
class TravelRoute {
    static List<String> answer; // DFS 재귀형태라 한번에 결과값 내보내기 위해 answer는 static으로 선언함

    /**
     * 재귀로 작동하는 DFS
     * @param graph : 노드와 연결된 간선 정보 Map으로 저장
     * @param node : 현재 여행 중인 국가
     * @param order : 누적된 여행 경로
     * @param n : 여행해야 하는 나라 총 갯수
     */
    private void DFS(Map<String, List<String>> graph, String node, List<String> order, int n) {
        if (order.size() == n + 1) { // 모든 도시 다 돌았어!
            if(answer == null){ // 사전순으로 우선순위 있기 때문에 먼저 완성된 루트가 정답!
                answer = new ArrayList<>(order);
                return;
            }
            return;
        }
        List<String> edges = graph.get(node);
        if(edges != null){
            for (int i = 0; i < edges.size(); i++) {
                String city = edges.get(i);

                // 경로도 복사해서 넘겨야 함!
                List<String> orderCopy = new ArrayList<>(order);
                orderCopy.add(city);

                // 그래프 복사한 후, 지금 선택한 엣지 지워서 넘기기
                // 복사한 후 지워야 다음 sibling 순회에 영향 안미침
                Map<String, List<String>> graphCopy = new HashMap<>(graph);
                List<String> edgesCopy = new ArrayList<>(edges);
                edgesCopy.remove(i);
                graphCopy.put(node, edgesCopy);

                // 재귀 -> 꼭 복사본으로 넘겨줘
                DFS(graphCopy, city, orderCopy, n);
            }
        }
    }

    public String[] solution(String[][] tickets) {
        Map<String, List<String>> graph = new HashMap<>();
        for (String[] ticket : tickets) {
            // 단방향 간선 그래프 만들기 -> 티켓 리스트 매번 순회 안하고도 연결된 노드 찾을 수 있어!
            graph.putIfAbsent(ticket[0], new ArrayList<>());
            graph.get(ticket[0]).add(ticket[1]);
        }
        for (List<String> edges : graph.values()) {
            // 이름 사전순으로 우선순위 있으므로
            // 나중에 검증 안해도 젤 먼저 만들어진 루트가 사전순 우선순위 높게 만들기 위해 미리 sort
            Collections.sort(edges);
        }

        // 인천부터 출발
        List<String> order = new ArrayList<>();
        order.add("ICN");

        // DFS
        DFS(new HashMap<>(graph), "ICN", order, tickets.length);

        return answer.toArray(new String[0]); // 출력 포맷 일반 array라 변환해줌
    }
}
