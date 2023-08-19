import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
/**
 * 백준 1074 Z
 * @author youngeun
 *
 * 13000 KB
 * 88 ms
 *
 * z 순으로 재귀 호출
 * 범위에 속하지 않으면 재귀 호출하는 대신 해당 구역 안에 있는 숫자 갯수만큼 cnt 증가
 * 별도의 배열로 저장헀다가 메모리초과가 일어났다. 생각해보니 필요가 없는 배열이었다.
 * 즉시 해당 구역값과 동일해지면 출력하도록 함
 */
public class Z {
    static int N, r, c, divR, divC, cnt = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        r = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());
        divR = r/2;
        divC = c/2;

        recur(0, 0, 1<<(N-1)); // 4칸은 계산하기 쉬우니까.. 그냥 2^(N-1) 만큼만 돌고 4칸 달성 시 계산함
    }

    static void recur(int sr, int sc, int length) {
        if(length == 1) {
            if(sr <= divR && divR < sr+length && sc <= divC && divC < sc+length) {
                int modR = r%2;
                int modC = c%2;
                System.out.println(cnt*4 + modR*2 + modC); // 4칸이면 나머지 연산으로 바로 구할 수 있다.
                System.exit(0);
            }
            cnt++;
            return;
        }
        // z 순으로 재귀 호출
        // 단, 범위에 속하지 않으면 그냥 넘어가고 해당 구역 안에 있는 숫자 갯수만큼 cnt에 추가했다.
        int half = length/2;
        // 1사분면
        if(sr <= divR && divR < sr+half && sc <= divC && divC < sc+half) {
            recur(sr, sc, half);
        }else {
            cnt+=(half*half);
        }
        // 2사분면
        if(sr <= divR && divR < sr+half && sc+half <= divC && divC < sc+length) {
            recur(sr, sc+half, half);
        }else {
            cnt+=(half*half);
        }
        // 3사분면
        if(sr+half <= divR && divR < sr+length && sc <= divC && divC < sc+half) {
            recur(sr+half, sc, half);
        }else {
            cnt+=(half*half);
        }
        // 4사분면
        if(sr+half <= divR && divR < sr+length && sc+half <= divC && divC < sc+length) {
            recur(sr+half, sc+half, half);
        }else {
            cnt+=(half*half);
        }
    }
}
