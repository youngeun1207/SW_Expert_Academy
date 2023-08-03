import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * BJ 2961 도영이가 만든 맛있는 음식
 * @author youngeun
 *
 * 14180 KB
 * 124 ms
 *
 * 완전탐색으로 수행
 * N <= 10 이므로 가능한 부분집합은 2^10 -> 즉 1024개만 확인하면 됨
 * 부분집합 구하기 위해 비트연산 활용
 * N=4 면 0001 ~ 1111 까지 (10진수: 1 ~ 15)
 * 이후 1을 N번만큼 << 연산 해주기 (0001 -> 0010 -> 0100 -> 1000)
 * 위의 두 이진수 & 연산으로 둘 다 1인 경우 쓴맛, 신맛 연산 수행
 */
public class Doyoung {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        StringTokenizer st;

        int[][] ingredients = new int[N][2]; // 신맛 쓴맛 순
        for(int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            ingredients[i][0] = Integer.parseInt(st.nextToken());
            ingredients[i][1] = Integer.parseInt(st.nextToken());
        }
        int min_taste = Integer.MAX_VALUE; // 정답 저장할 변수 -> 최대값으로 초기화
        int taste, sour, bitter;
        for(int i = 1; i < (1<<N); i++) { // 1<<n: 부분집합의 개수(1 * 2^n)
            sour = 1; // 신 맛은 곱이라 1로 초기화
            bitter = 0; // 쓴 맛은 합이라 0으로 초기화
            for(int j = 0; j < N; j++) { // 원소의 개수만큼 비트 비교
                if((i & (1<<j)) == 0) continue;  // i의 j번째 비트가 0이면 넘어가기
                sour*=ingredients[j][0]; // 신맛은 곱
                bitter+=ingredients[j][1]; // 쓴맛은 합
            }
            taste = Math.abs(bitter-sour);
            if( taste < min_taste) { // 만약 현재 쓴-신 값이 더 작으면 교체
                min_taste = taste;
            }
        }
        System.out.println(min_taste);
    }

}

