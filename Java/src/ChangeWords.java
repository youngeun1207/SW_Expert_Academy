import java.util.*;

/**
 * 프로그래머스 Lv.3 단어 변환
 * BFS
 * @author youngeun
 *
 *
 */
class ChangeValue {
    public int solution(String begin, String target, String[] words) {
        if (!Arrays.asList(words).contains(target)) {
            return 0;
        }

        Queue<Pair<String, Integer>> queue = new LinkedList<>();
        queue.offer(new Pair<>(begin, 0)); // 현재 단어와, 몇 번 연산한 결과인지 저장
        int n = words.length;
        // BFS
        while (!queue.isEmpty()) {
            Pair<String, Integer> pair = queue.poll();
            String word = pair.getKey();
            int idx = pair.getValue();

            if (word.equals(target)) {
                return idx;
            }

            for (int i = 0; i < n; i++) {
                int cnt = 0;
                String newWord = words[i];
                for (int j = 0; j < newWord.length(); j++) { // 한 글자씩 비교하기
                    if (word.charAt(j) == newWord.charAt(j)) {
                        cnt++;
                    }
                }
                if (cnt == target.length() - 1) { // 1글자 빼고 다 같은 단어는 queue에 삽입
                    queue.offer(new Pair<>(words[i], idx + 1));
                    words[i] = "0"; // 중복해서 들어가지 않도록 단어 바꾸기 (visited 역할)
                }
            }
        }
        return 0;
    }
}

/**
 * 단어-연산횟수 key-value로 저장하기 위한 자료구조
 * @param <A>
 * @param <B>
 */
class Pair<A, B> {
    private final A key;
    private final B value;

    public Pair(A key, B value) {
        this.key = key;
        this.value = value;
    }

    public A getKey() {
        return key;
    }

    public B getValue() {
        return value;
    }
}