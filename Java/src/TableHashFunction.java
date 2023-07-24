import java.util.*;

/**
 * 프로그래머스 Lv.2 테이블 해시 함수
 * 16.88ms, 142MB
 *
 * 해싱 구현하는 문제
 */
class TableHashFunction {
    public int solution(int[][] data, int col, int row_begin, int row_end) {
        int answer = 0;

        Arrays.sort(data, (a, b) -> { // 파이썬 data.sort(lambda x:x[col-1])과 동일한 역할 수행
            int cmp = Integer.compare(a[col - 1], b[col - 1]);
            return cmp != 0 ? cmp : Integer.compare(b[0], a[0]);
        });

        int xor = 0; // 시작 행 ~ 끝 행 mod 연산 값 xor 하기
        for (int i = row_begin - 1; i < row_end; i++) {
            int add = 0;
            for (int j = 0; j < data[i].length; j++) {
                add += data[i][j] % (i + 1); // row[j] % row_index
            }
            xor ^= add;
        }
        return xor;
    }
}
