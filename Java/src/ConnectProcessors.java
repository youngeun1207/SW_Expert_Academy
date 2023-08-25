import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * SWEA 1767. [SW Test 샘플문제] 프로세서 연결하기
 *
 * @author youngeun
 *
 * <백트래킹>
 * 2^core * 4^core * core * N(맵 길이) => core ≤ 12 이므로 백트래킹 쓰면 가능..
 * 코어 갯수 줄여가면서.. 부분집합 만들기
 * - 집합 내 코어 하나씩 선택하면서 상하좌우 연결한 후 또 DFS
 * 	- 해당 방향 갈 수 없으면 가지치기(백트래킹)
 * 	- 만약 부분집합에 속한 모든 코어가 연결이 되면 해당 개수가 최대값
 * 		- 나머지 같은 개수들 돌리면서.. 최소 길이 찾기
 */
public class ConnectProcessors {
    static int T, N, cnt, prev, answer;
    static int[][] map, dirs = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; // 상하좌우
    static PriorityQueue<ArrayList<Integer>> pq;
    static ArrayList<int[]> cores;
    static ArrayList<Integer> arr;

    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        T = Integer.parseInt(br.readLine());

        for(int tc = 1; tc <= T; tc++) {
            N = Integer.parseInt(br.readLine());
            answer = Integer.MAX_VALUE;
            // 맵에 넣으면서 core 개수 세기 + 위치 저장
            cores = new ArrayList<>();
            map = new int[N][N];
            for(int i = 0; i < N; i++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                for(int j = 0; j < N; j++) {
                    map[i][j] = Integer.parseInt(st.nextToken());
                    if(map[i][j] == 1 && i != 0 && i != N-1 && j != 0 && j != N-1) {
                        cores.add(new int[] {i, j});
                    }
                }
            }
            cnt = cores.size(); // (파워에 붙어있는 코어 제외)코어 갯수

            // 부분집합 만들기
            pq = new PriorityQueue<ArrayList<Integer>>((b, a) -> a.size() - b.size());

            // 바이너리 카운팅으로 만들거임
            for(int i = 1; i < (1<<cnt); i++) { // 공집합 제외
                arr = new ArrayList<>();
                for(int j = 0; j < cnt; j++) {
                    if((i & (1<<j)) == 0) continue;
                    // 부분집합 배열에 추가
                    arr.add(j);
                }
                pq.add(arr);
            }


            // 부분집합에 포함된 코어들은 모두 연결이 가능한 것이라 가정
            // 만약 속해있는 코어 중 하나라도 연결이 불가능하면 백트래킹으로 제외시킴
            prev = Integer.MIN_VALUE; // 성공한 경우의 코어 수
            while (!pq.isEmpty()) {
                arr = pq.poll();
                if(arr.size() < prev) {
                    break; // 코어 개수 더 줄이면 안됨!
                }
                DFS(arr.size(), 0, 0);
            }
            sb.append("#" + tc + " " + answer + "\n");
        }
        System.out.println(sb);
    }
    static void DFS(int cnt, int idx, int len) {
        if(idx == cnt) { // 해당 부분집합은 가능한 경우이다!
            // 성공하면 prev에 코어 개수 넣기 -> 코어 더 적은 경우 제외하기 위함
            prev = cnt;
            answer = Math.min(answer, len);
            return;
        }

        int[] coor = cores.get(arr.get(idx));

        for(int dir = 0; dir < 4; dir++) {
            int newR = coor[0];
            int newC = coor[1];
            boolean isPossible = false;
            int tmp = 0; // 전선 길이

            while(true) {
                newR+=dirs[dir][0];
                newC+=dirs[dir][1];

                if(newR < 0 || N == newR || newC < 0 || N == newC) { // 끝에 도착
                    isPossible = true;
                    break;
                }

                if(map[newR][newC] != 0) { // 다른 프로세서나 전선과 닿음
                    isPossible = false;
                    break;
                }

                tmp++; // 연결한 전선 길이 추가
                map[newR][newC] = 2; // 전선 부분
            }

            if(isPossible) DFS(cnt, idx+1, len+tmp); // DFS

            // 지도 원상복구
            while(true) {
                newR-=dirs[dir][0];
                newC-=dirs[dir][1];

                if(newR == coor[0] && newC == coor[1]) {
                    break;
                }
                map[newR][newC] = 0;
            }
        }
    }
}