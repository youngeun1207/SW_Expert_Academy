import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;

/**
 * 백준 3025 돌 던지기
 * @author youngeun
 *
 * 80604 KB
 * 664 ms
 *
 * <DP + skack>
 * 각 열마다 돌을 던졌을 때 가능한 위치 스택에 저장함
 * 1. 만약 스택에 경로 없으면 -> 맨 윗 줄에서 바로 move()
 * 2. 스택에 경로 있으면 -> pop 하면서 해당 지점이 돌(O)이면 그 경로 꽉 찬 것이므로 다음 경로 찾기
 * 3. move()
 *  아래 로직 수행하기 전에 현재 도달한 지점 경로에 push
 *  a. 빈 칸이면(.) 바로 한 줄 밑으로
 *  b. 맨 마지막 줄이거나, 벽(X)을 만나면 탐색 종료
 *  c. 만약 돌(O) 만나면
 *      i. 왼쪽 검증해보고 가능하면 열 한칸 왼쪽으로
 *      ii. 오른쪽 검증해보고 가능하면 열 한칸 오른쪽으로
 *      -> 이후 한 줄씩 아래로 내려가면서 검증하기
 * 4. move() 종료 후 마지막으로 push된 지점이 돌 멈추는 자리
 */
public class ThrowStone {
    static int R, C, N, col;
    static char[][] map;
    static Stack<int[]>[] dp;
    static int[] coor;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());

        map = new char[R][C]; // 지도
        dp = new Stack[C]; // 굴러가기 포인트

        for(int r = 0; r < R; r++){
            String s = br.readLine();
            for(int c = 0; c < C; c++){
                map[r][c] = s.charAt(c);
            }
        }
        for (int c = 0; c < C; c++){
            dp[c] = new Stack<>();
        }

        N = Integer.parseInt(br.readLine());

        for(int n = 0; n < N; n++) {
            col = Integer.parseInt(br.readLine()) - 1;
            // dp에 저장된 경로 뒤에서부터 검증
            // 이동 가능한 지점 나올 때 까지 pop
            while (!dp[col].isEmpty()) {
                coor = dp[col].peek();
                // 해당 지점에 돌이 이미 있으면 pop
                if (map[coor[0]][coor[1]] == 'O') {
                    dp[col].pop();
                } // 구르기 지점 도착
                else if (map[coor[0]][coor[1]] == '.') {
                    break;
                }
            }
            // 돌 양 옆으로 구르기
            if (!dp[col].isEmpty()) {
                coor = dp[col].pop();
            }
            // 맨 윗 줄부터 떨어지기
            else {
                coor = new int[]{0, col};
            }
            move();

            // 돌 위치 확정
            coor = dp[col].pop();
            map[coor[0]][coor[1]] = 'O';
        }

        StringBuilder sb = new StringBuilder();
        for(int r = 0; r < R; r++){
            for(int c = 0; c < C; c++){
                sb.append(map[r][c]);
            }
            sb.append("\n");
        }
        System.out.println(sb);
    }

    /**
     * 돌 이동하기
     */
    static void move(){
        int r = coor[0];
        int c = coor[1];

        while (true){
            dp[col].push(new int[] {r, c});
            // 맨 마지막 줄 or 벽 도달
            if(r+1 == R || map[r+1][c] == 'X'){
                return;
            }

            // 돌 만남
            if(map[r+1][c] == 'O'){
                // 왼쪽
                if(isInRange(r, c-1) && isInRange(r+1, c-1)){
                    c--;
                }
                // 오른쪽
                else if (isInRange(r, c+1) && isInRange(r+1,c+1)) {
                    c++;
                }
                // 둘 다 못가
                else{
                    map[r][c] = 'O';
                    return;
                }
            }
            r++;
        }
    }

    /**
     * 주어진 칸 넘어가는지 확인
     * @param r
     * @param c
     * @return
     */
    static boolean isInRange(int r, int c){
        return 0 <= r && r < R && 0 <= c && c < C && map[r][c] == '.';
    }
}
