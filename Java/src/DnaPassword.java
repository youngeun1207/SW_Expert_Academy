import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
/**
 * 백준 12891 DNA 비밀번호
 * 누적합 문제
 * @author youngeun
 *
 * 35480 KB
 * 328 ms
 *
 * 핵산 별 0 ~ index 까지 등장 횟수 저장(dp_핵산 배열)
 * P 길이로 슬라이딩 윈도우 -> dp[end] - dp[start] 값이 조건 만족하면 answer++
 */
public class DnaPassword {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());

        int S = Integer.parseInt(st.nextToken());
        int P = Integer.parseInt(st.nextToken());

        int[] minNucleicAcid = new int[4]; // A C G T 최소값 저장
        int[] dp_A = new int[S+1];
        int[] dp_C = new int[S+1];
        int[] dp_G = new int[S+1];
        int[] dp_T = new int[S+1];

        String dna = br.readLine();
        for(int i = 1; i <= S; i++) {
            int a = 0, c = 0, g = 0, t = 0;
            switch (dna.charAt(i-1)) {
                case 'A':
                    a = 1;
                    break;
                case 'C':
                    c = 1;
                    break;
                case 'G':
                    g = 1;
                    break;
                case 'T':
                    t = 1;
                    break;
            }
            // 각 핵산 별 등장 횟수 누적합 배열
            dp_A[i] = dp_A[i-1] + a;
            dp_C[i] = dp_C[i-1] + c;
            dp_G[i] = dp_G[i-1] + g;
            dp_T[i] = dp_T[i-1] + t;
        }

        st = new StringTokenizer(br.readLine());
        for(int i = 0; i < 4; i++) {
            minNucleicAcid[i] = Integer.parseInt(st.nextToken());
        }

        int addA, addC, addG, addT, end;
        int answer = 0;
        for(int start = 0; start < S - P + 1; start++) {
            end = start + P;
            // 해당 구간의 핵산 갯수 합 구하기 -> 구간합 알고리즘
            addA = dp_A[end] - dp_A[start];
            addC = dp_C[end] - dp_C[start];
            addG = dp_G[end] - dp_G[start];
            addT = dp_T[end] - dp_T[start];

            // 등장 횟수 조건 만족하면 answer++
            if(addA >= minNucleicAcid[0] && addC >= minNucleicAcid[1] && addG >= minNucleicAcid[2] && addT >= minNucleicAcid[3]) {
                answer++;
            }
        }
        System.out.println(answer);
    }
}

