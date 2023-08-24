import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * 백준 18234 당근 훔쳐먹기
 * @author youngeun
 *
 * 65560 KB
 * 524 ms
 *
 * <그리디>
 * 1. w ≤ p -> 먹지 말고 계속 아껴뒀다 마지막에 먹는게 이득!
 *    현재 먹는 것 보다 1번 기다렸다 먹으면 2배 이상의 이득 -> T-N까지 계속 키우기
 * 2. p가 높을수록 아껴뒀다 나중에 먹는 것이 이득임(하루라도 더 길러야함)
 *    T-N부터 T까지 p 낮은 순으로 하나씩 뽑아먹기
 * 3. 우선순위 큐로 p 낮은 당근부터 뽑아 먹도록 함
 */
public class StealingCarrot {
    static int N, T;
    static PriorityQueue<Carrot> carrots;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        T = Integer.parseInt(st.nextToken());

        carrots = new PriorityQueue<>();
        for(int i = 0; i < N; i++){
            st = new StringTokenizer(br.readLine());
            carrots.add(new Carrot(Long.parseLong(st.nextToken()), Long.parseLong(st.nextToken())));
        }
        long answer = 0;
        Carrot c;
        for(int day = T-N; day < T; day++){
            c = carrots.poll();
            answer += c.w + (c.p*day);
        }
        System.out.println(answer);
    }
    static class Carrot implements Comparable<Carrot>{
        long w;
        long p;

        public Carrot(long w, long p){
            this.w = w;
            this.p = p;
        }

        @Override
        public int compareTo(Carrot o) {
            return (int)(this.p - o.p);
        }
    }
}
