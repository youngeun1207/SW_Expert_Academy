import java.util.*;

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
class ChangeValue {
    public int solution(String begin, String target, String[] words) {
        if (!Arrays.asList(words).contains(target)) {
            return 0;
        }

        Queue<Pair<String, Integer>> queue = new LinkedList<>();
        queue.offer(new Pair<>(begin, 0));
        int n = words.length;

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
                for (int j = 0; j < newWord.length(); j++) {
                    if (word.charAt(j) == newWord.charAt(j)) {
                        cnt++;
                    }
                }
                if (cnt == target.length() - 1) {
                    queue.offer(new Pair<>(words[i], idx + 1));
                    words[i] = "0";
                }
            }
        }

        return 0;
    }
}
