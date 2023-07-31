import java.util.Scanner;

/**
 * 백준 11729 하노이 탑 이동 순서
 * @author youngeun
 *
 * 층수를 줄여가면서 재귀 호출로 해결하는 문제
 */
public class TowerOfHanoi {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        hanoi(n, 1, 2, 3);
    }
    static void hanoi(int n, int start, int mid, int end){ // 출발점, 보조기둥, 목적지
        if(n == 1){ // 원반이 1개면 목적지까지 옮기면 끝
            System.out.printf("%d %d\n", start, end);
        }
        else {
            hanoi(n-1, start, end, mid); // n-1개를 보조기둥으로 옮기기
            System.out.printf("%d %d\n", start, end); // 젤 큰 원반을 목적지로 옮기기
            hanoi(n-1, mid, end, start); // 다시 보조기둥에서 목적지로 옮기기
        }
    }
}
