import java.util.Scanner;

/**
 * 백준 11729 하노이 탑 이동 순서
 * @author youngeun
 * 50180 KB
 * 620 ms
 *
 * 층수를 줄여가면서 재귀 호출로 해결하는 문제
 * IO overhead 줄이기 위해 StringBuilder에 출력할 문자열들 저장한 후 일괄 출력함
 */
public class TowerOfHanoi {
    private static final StringBuilder output = new StringBuilder();
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        output.append((int) (Math.pow(2, n) - 1)).append("\n"); // 이동횟수 = 2^n -1
        hanoi(n, 1, 2, 3);

        System.out.println(output);
    }
    static void hanoi(int n, int start, int mid, int end){ // 출발점, 보조기둥, 목적지
        if(n == 1){ // 원반이 1개면 목적지까지 옮기면 끝
            output.append(start).append(" ").append(end).append("\n");
        }
        else {
            hanoi(n-1, start, end, mid); // n-1개를 보조기둥으로 옮기기
            output.append(start).append(" ").append(end).append("\n"); // 가장 큰 원반을 목적지로 옮기기
            hanoi(n-1, mid, start, end); // 다시 보조기둥에서 목적지로 옮기기
        }
    }
}
