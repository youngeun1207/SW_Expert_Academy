import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 백준 15961 회전초밥
 * @author youngeun
 *
 * 180336 KB
 * 548 ms
 *
 * <투포인터>
 * 2 ≤ N ≤ 3,000,000
 * 2 ≤ d ≤ 3,000
 * 슬라이딩 윈도우 완탐으로 최악의 경우 3,000,000 * 3,000 -> 시간초과 발생
 * 투포인터로 O(N)만에 물어야 하는 문제
 * 초밥의 종류 d를 300으로 한정하였기 때문에 3000짜리 정수 배열 생성 후 포인터 이동에 따라 값 갱신하도록 하면 보다 빠르게 풀 수 있다.
 * 회전 빠르게 하기 위해 N*2 동일한 배열로 2개 이어붙인 형태로 저장
 */
public class ConveyorBeltSushi {
    static int N, D, K, C, answer = 0;
    static int[] sushies, status;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        D = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());

        sushies = new int[N*2];
        for(int i = 0; i < N; i++){ // 배열 2개 이어붙인 형태로 저장
            sushies[i] = Integer.parseInt(br.readLine());
            sushies[N+i] = sushies[i];
        }
        status = new int[3001]; // 초밥 종류 3000개로 제한되어있음

        // 투포인터
        int point1 = 0;
        int point2 = K-1;

        // 초기값 저장
        for(int i = 0; i < K; i++){
            if(status[sushies[i]] == 0){
                answer++;
            }
            status[sushies[i]]++;
        }
        int prev = answer; // 쿠폰 적용 전의 값 저장해야함!!!
        if(status[C] == 0){ // 쿠폰 적용
            answer++;
        }
        while (point1 < N) {
            int tmp = prev;
            point1++; // 포인터 이동
            point2++;

            status[sushies[point1-1]]--; // 한칸 앞으로 갔으므로 그 이전 값 하나 빼기
            if(status[sushies[point1-1]] == 0){ // 해당 종류의 초밥 더이상 없어
               tmp--;
            }
            status[sushies[point2]]++; // 한칸 앞으로 갔으므로 새로 들어온 값 추가
            if(status[sushies[point2]] == 1){ // 해당 종류의 초밥은 원래 없던 초밥
               tmp++;
            }
            prev = tmp; // 반드시 쿠폰 적용 전에 저장

            if(status[C] == 0){ // 쿠폰 적용
               tmp++;
            }
            answer = Math.max(answer, tmp); // 최댓값 반영
        }
        System.out.println(answer);
    }
}
