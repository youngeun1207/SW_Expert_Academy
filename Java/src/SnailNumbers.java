import java.util.Scanner;

/**
 * SWEA D2 1954 달팽이 숫자
 * @author youngeun
 *
 * 단순구현 문제
 * 패턴 분석 -> 같은 길이가 2번씩 회전하면서 반복
 * 단, 처음 3번(맨 바깥 라인)만 3번 반복
 * 예) 4*4: 1,2,3(3칸) [회전] 4,5,6(3칸) [회전] 7,8,9(3칸) [회전] 10,11(2칸) [회전] 12,13(2칸) [회전] 14(1칸) [회전] 15(1칸)
 * 종료 후 마지막 번호 넣음 끝
 */
public class SnailNumbers {
    static int TestCase;
    static int N;
    static int[][] dirs = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}}; // 오, 아래, 왼, 위
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        TestCase = sc.nextInt();
        for(int tc = 1; tc <= TestCase; tc++){
            N = sc.nextInt();
            int repeat = 3; // 가장 밖 라인은 3번
            int dir = 0; // 오른쪽부터 시작

            int length = N-1;
            int row = 0;
            int col = 0;

            int[][] snail = new int[N][N];
            int cnt = 1;
            while (cnt < N*N){
                for(int l = 0; l < length; l++){
                    System.out.println(cnt + " " + length);
                    snail[row][col] = cnt;
                    row += dirs[dir][0];
                    col += dirs[dir][1];
                    cnt++;
                }
                repeat--;
                if(repeat == 0){
                    length--;
                    repeat = 2;
                }
                dir = (dir+1)%4;

            }

            snail[row][col] = N*N;
            System.out.printf("#%d\n", tc);
            for(int i = 0; i < N; i++){
                for(int j = 0; j < N; j++){
                    System.out.print(snail[i][j] + " ");
                }
                System.out.println();
            }
        }

    }
}
