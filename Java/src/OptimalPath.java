import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
/**
 * SWEA D5 최적 경로
 * @author youngeun
 *
 * 20,892 kb
 * 263 ms
 *
 * DFS + 백트래킹
 */
public class OptimalPath {
    static int[][] customers;
    static int[] input;
    static boolean[] visited;
    static int[] company, house;
    static int T, N, answer;
    static int start, end;

    public static void main(String[] args) throws NumberFormatException, IOException {
        // TODO Auto-generated method stub
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        T = Integer.parseInt(br.readLine());
        for(int tc = 1; tc <= T; tc++) {
            N = Integer.parseInt(br.readLine());
            st = new StringTokenizer(br.readLine());

            company = new int[] {Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())};
            house = new int[] {Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())};

            customers = new int[N][2]; // 고객 좌표 저장
            input = new int[N];

            for(int i = 0; i < N; i++) {
                input[i] = i;
                customers[i] = new int[] {Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())};
            }
            answer = Integer.MAX_VALUE;
            // 출발, 도착 위치 찾기
            int dist = 0;
            for(int i = 0; i < N-1; i++) {
                for(int j = i+1; j < N; j++) {
                    // i 시작 j 종료
                    visited = new boolean[N];
                    visited[i] = true;
                    end = j;
                    dist = getDistance(company[0], company[1], customers[i][0], customers[i][1]) + getDistance(house[0], house[1], customers[j][0], customers[j][1]);
                    DFS(customers[i][0], customers[i][1], dist, 1);

                    // j 시작 i 종료
                    visited = new boolean[N];
                    visited[j] = true;
                    end = i;
                    dist = getDistance(company[0], company[1], customers[j][0], customers[j][1]) + getDistance(house[0], house[1], customers[i][0], customers[i][1]);
                    DFS(customers[j][0], customers[j][1], dist, 1);
                }
            }

            System.out.printf("#%d %d\n", tc, answer);
        }



    }
    static void DFS(int prevX, int prevY, int dist, int cnt) {
        // 만약 끝점에 도달했으면 answer 변경
        if(cnt == N && prevX == customers[end][0] && prevY == customers[end][1]) {
            answer = Math.min(answer, dist);
            return;
        }
        // 끝까지 도달 안했는데 끝점 도착했으면 정답 반영 안하고 DFS 종료
        if(prevX == customers[end][0] && prevY == customers[end][1]) {
            return;
        }
        // 현재 정답보다 더 오래 걸리면 반영 안하고 DFS 종료
        if(dist > answer) {
            return;
        }
        // 다음 노드 선택
        for(int i = 0; i < N; i++) {
            if(!visited[i]) {
                visited[i] = true;
                DFS(customers[i][0], customers[i][1], dist + getDistance(prevX, prevY, customers[i][0], customers[i][1]), cnt+1);
                visited[i] = false;
            }
        }
    }

    static int getDistance(int x1, int y1, int x2, int y2) {
        return Math.abs(x1-x2) + Math.abs(y1-y2);
    }
}

