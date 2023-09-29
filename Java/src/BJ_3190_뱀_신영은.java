import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 백준 3190 뱀
 * @author youngeun
 *
 * 12028 KB
 * 88 ms
 *
 * <deque를 이용한 단순구현>
 * 유의할 점: L번의 방향전환이 끝나도 뱀이 살이있을 수 있음!!
 * 1. 뱀의 머리와 꼬리가 계속해서 늘어나고, 줄어들기에 양 방향에 모두 접근 가능해야함
 * 2. 또한, 뱀의 몸통을 순서대로 기억해야함
 * 3. 고로 양 방향 접근이 가능하면서, 순서가 있는 자료구조인 deque 사용.
 */
public class BJ_3190_뱀_신영은 {
    static int N, K, L, X;
    static char C;
    static boolean[][] appleMap, snakeMap;
    static ArrayDeque<int[]> snake;
    static Queue<Integer> moveDirs;
    static int[][] dirs = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}}; // 오른, 아래, 왼, 위

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        appleMap = new boolean[N][N]; // 사과 위치 저장
        for (int k = 0; k < K; k++){
            st = new StringTokenizer(br.readLine());
            appleMap[Integer.parseInt(st.nextToken())-1][Integer.parseInt(st.nextToken())-1] = true;
        }

        L = Integer.parseInt(br.readLine());

        snakeMap = new boolean[N][N]; // 자기 자신과 부딪혔는지 확인하기 위함
        snake = new ArrayDeque<>(); // 뱀의 위치 좌표 순서대로 저장하기 위함
        // 뱀의 초기 위치
        snake.add(new int[]{0, 0});
        snakeMap[0][0] = true;

        // 뱀의 진행 방향 저장
        moveDirs = new ArrayDeque<>();
        int time = 1;
        int dir = 0; // 초기값 오른쪽

        for (int l = 0; l < L; l++){
            st = new StringTokenizer(br.readLine());
            X = Integer.parseInt(st.nextToken());
            C = st.nextToken().charAt(0);

            while (time < X){ // X초 뒤에 방향전환이므로, 전환되기 전까지는 이전 값 복사
                moveDirs.offer(dir);
                time++;
            }

            // 방향 전환
            switch (C){
                case 'L': // 좌회전
                    dir = dir>0 ? dir-1:3;
                    break;
                case 'D': // 우회전
                    dir = (dir+1)%4;
                    break;
            }
        }
        moveDirs.offer(dir);

        time = 1;
        dir = 0;
        while (moveSnake(dir)){
            if(!moveDirs.isEmpty()){
                dir = moveDirs.poll();
            }
            time++;
        }
        System.out.println(time);
    }

    /**
     * 뱀 움직이는 메소드
     * @param dir : 뱀의 이동 방향
     * @return : 게임 끝났는지 여부
     */
    static boolean moveSnake(int dir){
        int[] head = snake.peekFirst();
        int newR = head[0] + dirs[dir][0];
        int newC = head[1] + dirs[dir][1];

        if (isInRange(newR, newC)){
            // 머리 늘리기
            snakeMap[newR][newC] = true;
            snake.offerFirst(new int[] {newR, newC});

            if(appleMap[newR][newC]){ // 사과 있음 -> 사과 먹기
                appleMap[newR][newC] = false;
            }else{ // 사과 없음 -> 꼬리 줄이기
                int[] tail = snake.pollLast();
                snakeMap[tail[0]][tail[1]] = false;
            }
            return true;
        }
        return false;
    }

    /**
     * 뱀이 벽 or 자기 자신과 부딪혔는지 여부
     * @param r
     * @param c
     * @return 안부딪혔을 때 true
     */
    static boolean isInRange(int r, int c){
        return 0 <= r && r < N && 0 <= c && c < N && !snakeMap[r][c];
    }
}
