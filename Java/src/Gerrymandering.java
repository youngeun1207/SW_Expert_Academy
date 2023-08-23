import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.StringTokenizer;


/**
 * 백준 17471 게리맨더링
 * @author youngeun
 * 
 * 13400 kb
 * 100 ms
 * 
 * <부분집합 + BFS>
 * 2 ≤ N ≤ 10 -> 완전탐색
 * 처음에 입력 받을 때 인구 수 총합 저장해두기
 * 부분집합 만들기 -> 2^10: 1024개
 * BFS돌면서 서브트리인지 검증
 * 	- 연결될 수 없는 노드면 out 
 * 	- 연결 가능한 노드로만 이루어지면, 서브트리 합 vs 전체-서브트리 차이 최솟값 갱신 
 */
public class BJ_17471_게리맨더링_신영은 {
	static int N;
	static int answer = Integer.MAX_VALUE;
	static int selected = 1, tmp = 0, total = 0;
	static Map<Integer, ArrayList<Integer>> graph;
	static int[] cost;
	static boolean[] canVisit;
	static int a, b;
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		cost = new int[N];
		for(int n = 0; n < N; n++) {
			cost[n] = Integer.parseInt(st.nextToken());
			total+=cost[n];
		}
		graph = new HashMap<>(); // 해시맵으로 그래프 저장
		for(int n = 0; n < N; n++) {
			st = new StringTokenizer(br.readLine());
			int cnt = Integer.parseInt(st.nextToken());
			
			ArrayList<Integer> adjs = new ArrayList<>(); // 나의 인접 노드 목록
			
			for(int c = 0; c < cnt; c++) {
				adjs.add(Integer.parseInt(st.nextToken())-1);
			}
			
			graph.put(n, adjs);
		}
		
		for(selected = 1; selected < (1<<N)-1; selected++) { // 1<<n - 2: 부분집합의 개수(1 * 2^n), 공집합 제외
			tmp = 0;
			for(int city = 0; city < N; city++) { // 원소의 개수만큼 비트 비교
				// i의 j번째 비트가 0이면 선거구 A
				if((selected & (1<<city)) == 0) {
					tmp+=cost[city];
					a = city;
				}
				// i의 j번째 비트가 1이면 선거구 B
				else {
					b = city;
				}
			}
			// bfs로 두 경우 다 도달 가능한지 체크
			if(BFS(0) && BFS(1)) { // a선거구와 b선거구 모두 도달 가능한 노드로만 이루어짐
				answer = Math.min(answer, Math.abs(tmp-(total-tmp)));
			}
		}
		if(answer==Integer.MAX_VALUE) { // 반띵 불가
			System.out.println(-1);
		}else {
			System.out.println(answer);
		}
		
	}
	
	static boolean BFS(int flag) {
		Queue<Integer> queue = new ArrayDeque<Integer>();
		canVisit = new boolean[N]; // 방문 가능한 노드
		
		int start;
		if(flag == 0) {
			start = a;
		}
		else {
			start = b;
		}
		
		queue.add(start);
		canVisit[start] = true;
		
		while (!queue.isEmpty()) { // 방문 가능한 노드 목록 갱신
			
			int node = queue.poll();
			if(graph.containsKey(node)) {
				for(int adj : graph.get(node)) {
					if((selected & (1<<adj)) == flag<<adj && !canVisit[adj]) { // 나의 서브셋이면서, 방문 이력이 없음
						queue.add(adj); // 큐에 넣어!
						canVisit[adj] = true; // 해당 노드는 방문 가능하다
					}
				}
			}
		}
		for(int choice = 0; choice < N; choice++) {
			if((selected & (1<<choice)) == flag<<choice && canVisit[choice] == false) { // 나의 서브셋에 방문할 수 없음
				return false;
			}
		}
		return true;
	}
}
