import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
/**
 * 백준 2023 신기한 소수
 * 18328 KB
 * 224 ms
 * @author youngeun
 *
 * 1~N까지 길이의 문자열 BFS로 형성 -> 소수인 경우에만 큐에 넣기
 * 뒤에 숫자 1개씩(짝수 제외) 붙인 후 계속해서 소수 판별
 */
public class PrimeNumbers {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StringBuilder sb = new StringBuilder();
        int N = sc.nextInt();

        if(N == 1) { // 1이면 큐 돌릴 필요 없어~!
            System.out.printf("2\n3\n5\n7");
            System.exit(0);
        }

        // 1 ~ N 자리의 수 까지 트리 완성해가기
        Queue<Integer> queue = new LinkedList<>();
        // 1자리 수 소수 미리 집어넣기
        queue.add(2);
        queue.add(3);
        queue.add(5);
        queue.add(7);
        int num;
        int out = (int) (Math.pow(10, N-1));
        int[] odds = {1, 3, 7, 9};
        while(queue.size() > 0) {
            int prev = queue.poll();
            for(int j = 0; j < 4; j+=1) {
                num = prev * 10 + odds[j]; // 숫자 한 자리 이어붙이기
                if(isPrimeNumber(num)) { // 소수 맞아!
                    if(num < out) { // 아직 자리수 모자라니까 뒤에
                        queue.add(num);
                    }else { // 자리수 다 채웠음! 출력ㄱ
                        sb.append(num + "\n");
                    }
                }
            }
        }
        System.out.println(sb);
    }
    public static boolean isPrimeNumber(int x) {
        // 2부터 x의 제곱근까지의 모든 수를 확인
        for (int i = 2; i <= Math.sqrt(x); i++) {
            // x가 해당 수로 나누어떨어진다면
            if (x % i == 0) {
                return false; // 소수가 아님
            }
        }
        return true; // 안나눠떨어지면 소수
    }
}
