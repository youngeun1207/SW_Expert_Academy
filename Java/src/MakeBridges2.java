import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 백준 17472 다리 만들기 2
 * @author youngeun
 *
 * 13152 KB
 * 92 ms
 *
 * 1. 섬 집합 만들기
 *  - 1인 칸부터 BFS로 육지로 이어진 영역 찾기
 *  - 해당 영역들 좌표 배열 형태로 islands 배열에 넣기 -> ArrayList<ArrayList<int[]>>
 *
 * 2. 섬과 섬 연결하기
 *  - n(최대 10개의 섬) 중 2개 선택 -> nC2
 *  - 각 섬의 좌표 중 섬과 섬 간에
 *      - col은 같고, row만 다름 => 세로 연결
 *      - row는 같고, col만 다름 => 가로 연결
 *      - 둘 다 겹치지 않음 => 연결 불가
 *      - 맵의 크기 최대 100*100 -> 섬 크기는 100*100보다 작음(점 100^2개 이하) => O(100^4)
 *      - 가장 짧은 길이 고르기
 *      - 다른 섬 지나지 않도록 도달한 섬과 목표 섬의 고유 번호가 동일해야함
 *
 * 3. 섬과 섬을 잇는 다리 중 최소값 고르기
 *  - MST -> Prim's Algo
 *  - O(ElogE)
 *  - 만약 mst 만들 수 없으면 -1
 *
 */
public class MakeBridges2 {
    static int N, M, cnt, id = 1;
    static int[][] map, dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    static boolean[][] visited;
    static ArrayList<ArrayList<int[]>> islands = new ArrayList<>();
    static Map<Integer, ArrayList<Node>> graph;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        map = new int[N][M];
        for(int n = 0; n < N; n++){
            st = new StringTokenizer(br.readLine());
            for(int m = 0; m < M; m++){
                map[n][m] = Integer.parseInt(st.nextToken());
            }
        }
        // 1. 섬 집합 만들기
        visited = new boolean[N][M];
        for(int n = 0; n < N; n++){
            for(int m = 0; m < M; m++){
                if(map[n][m] == 1 && !visited[n][m]){
                    // BFS
                    BFS(new int[] {n, m});
                    id++;
                }
            }
        }

        // 2. 섬과 섬 연결하기
        // 2개 고르는 조합이라 그냥 반복문 쓰장
        cnt = islands.size();
        graph = new HashMap<>();
        for(int island1 = 0; island1 < cnt-1; island1++){
            for(int island2 = island1+1; island2 < cnt; island2++) {
                int cost = connectIsland(island1, island2);
                if(cost < Integer.MAX_VALUE){
                    graph.putIfAbsent(island1, new ArrayList<>());
                    graph.putIfAbsent(island2, new ArrayList<>());
                    graph.get(island1).add(new Node(island2, cost));
                    graph.get(island2).add(new Node(island1, cost));
                }
            }
        }

        // 3. MST 구하기
        // 모든 노드가 연결되어있지 않으면 탈락..
        if(graph.size() != islands.size()){
            System.out.println(-1);
            System.exit(0);
        }
        // Prim's algo
        boolean[] visitedNode = new boolean[10];
        int answer = 0;
        PriorityQueue<Node> queue = new PriorityQueue<>(); // 최소 cost 간선 고르기 위해 내부적으로 minHeap 구현되어있는 priorityQueue 사용
        queue.add(new Node(0, 0));
        Node node;
        int cost, to;
        while (!queue.isEmpty()){
            node = queue.poll(); // 우선순위 큐라서 가장 cost 작은 엣지 나온다.
            to = node.to;
            cost = node.cost;

            if(visitedNode[to]){
                continue;
            }
            visitedNode[to] = true; // 방문처리

            answer+=cost; // 엣지 추가됨
            if(graph.containsKey(to)){ // NPE 방지
                for(Node n : graph.get(to)) {
                    if (!visitedNode[n.to]) { // 방문하지 않은 노드로 가는 엣지면 큐에 추가
                        queue.add(n);
                    }
                }
            }
        }
        // 모든 노드가 포함된 스패닝트리 생성 되었는지 확인
        for (int i = 0; i < graph.size(); i++){
            if(!visitedNode[i]){
                System.out.println(-1);
                System.exit(0);
            }
        }
        System.out.println(answer);
    }

    /**
     * 우선순위 큐 정렬 기준을 위해 Comparable 구현
     */
    static class Node implements Comparable<Node>{
        int to;
        int cost;

        public Node(int to, int cost) {
            this.to = to;
            this.cost = cost;
        }

        @Override
        public int compareTo(Node o) {
            return this.cost > o.cost ? 1 : -1;
        }
    }

    /**
     * 같은 섬 끼리 좌표 저장하는 BFS
     * @param coor 시작 좌표
     */
    static void BFS(int[] coor){
        Queue<int[]> queue = new ArrayDeque<>();
        queue.add(coor);
        visited[coor[0]][coor[1]] = true;

        ArrayList<int[]> adj = new ArrayList<>();

        while (!queue.isEmpty()){
            int[] node = queue.poll();
            adj.add(node); // 섬을 이루고 있는 좌표 저장
            map[node[0]][node[1]] = id; // 지도에 섬 고유번호 저장해두기 -> 나중에 다리 연결 시 사용
            for(int[] dir : dirs){ // 상하좌우 이동
                int newRow = node[0]+dir[0];
                int newCol = node[1]+dir[1];

                if(canMove(newRow, newCol)){
                    queue.add(new int[] {newRow, newCol});
                    visited[newRow][newCol] = true;
                }
            }
        }
        islands.add(adj);
    }

    /**
     * 다음 칸 이동 가능한지
     * @param row
     * @param col
     * @return
     */
    static boolean canMove(int row, int col) {
        // 칸 넘어가지 않으면서, 1(육지)면서, 방문하지 않은 지점
        if(0<=row && row<N && 0<=col && col<M && !visited[row][col] && map[row][col]==1) return true;
        return false;
    }

    /**
     * 조합으로 구한 두 섬 연결하는 최소 cost의 다리 구하기
     * @param island1
     * @param island2
     * @return
     */
    static int connectIsland(int island1, int island2){
        int answer = Integer.MAX_VALUE;
        // 섬을 이루는 좌표 중 1개씩 골라서 둘이 이어지나 체크
        for(int[] coor1 : islands.get(island1)){
            for(int[] coor2 : islands.get(island2)){
                int dist = 1;
                int point1 = map[coor1[0]][coor1[1]], point2 = map[coor2[0]][coor2[1]]; // 도달한 육지의 섬 고유번호 저장
                // 같은 열
                if(coor1[0] == coor2[0]) {
                    if(coor1[1] > coor2[1]){ // 2번이 오른쪽으로
                        while (map[coor2[0]][coor2[1]+dist] == 0){
                            dist++;
                        }
                        point2 = map[coor2[0]][coor2[1]+dist];
                    }else { // 1번이 오른쪽으로
                        while (map[coor1[0]][coor1[1]+dist] == 0){
                            dist++;
                        }
                        point1 = map[coor1[0]][coor1[1]+dist];
                    }
                }
                // 같은 행
                else if (coor1[1] == coor2[1]) {
                    if(coor1[0] > coor2[0]){ // 2번이 아래로
                        while (map[coor2[0]+dist][coor2[1]] == 0){
                            dist++;
                        }
                        point2 = map[coor2[0]+dist][coor2[1]];
                    }else { // 1번이 아래로
                        while (map[coor1[0]+dist][coor1[1]] == 0){
                            dist++;
                        }
                        point1 = map[coor1[0]+dist][coor1[1]];
                    }
                }
                // 도달한 섬이 같은 섬이여야함 (다른 섬을 지나면 안됨..)
                if (point1 == point2 && dist-1 > 1) {
                    answer = Math.min(dist-1, answer);
                }
            }
        }
        return answer;
    }
}
