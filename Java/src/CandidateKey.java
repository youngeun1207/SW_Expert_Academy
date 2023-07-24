import java.util.*;

/**
 * 프로그래머스 Lv.2 후보키
 * 2019 KAKAO BLIND RECRUITMENT
 * 19.61ms, 76.9MB
 *
 * 단순구현
 * 조합 활용한 문제
 * 후보키 조건인 최소성을 충족하기 위해 검증된 CK와 현재 후보 key 중복 여부 확인함
 */
class CandidateKey {
    public int solution(String[][] relation) {
        int answer = 0;
        int n = relation[0].length;
        List<Integer> l = new ArrayList<>(); // 조합 생성을 위한 index 모음
        for (int i = 0; i < n; i++) {
            l.add(i);
        }
        List<List<Integer>> ckList = new ArrayList<>(); // 검증된 ck 저장해두는 ArrayList -> 최소성 만족 여부 확인용

        for (int i = 0; i < n; i++) {
            for (List<Integer> ckIdx : combinations(l, i + 1)) { // 생성된 조합이 최소성을 만족하는지 확인
                boolean isDuplicate = false;
                for (List<Integer> ck : ckList) {
                    HashSet<Integer> hs = new HashSet<>(ckIdx); // 기존 ck와 새로운 조합 합친 후 set으로 만들기
                    hs.addAll(ck);

                    if (hs.size() == i + 1) { // 만약 합쳤는데 새로운 조합에 기존 ck가 100% 포함된다면 set의 길이는 조합 길이와 같을 것이다
                        isDuplicate = true;
                        break;
                    }
                }
                if (isDuplicate) { // 최소성 만족하지 못하였으므로 다음 조합 ㄱㄱ
                    continue;
                }

                List<String> ckCol = new ArrayList<>(); // 해당 ck가 유일성을 만족하는지 확인
                for (String[] row : relation) {
                    StringBuilder ckStr = new StringBuilder();
                    for (int idx : ckIdx) { // String으로 만들어서 검증할것임
                        ckStr.append(" ").append(row[idx]);
                    }
                    ckCol.add(ckStr.toString());
                }
                if (relation.length == new HashSet<>(ckCol).size()) { // String들이 다 다르면 유일성 보장된것임!
                    ckList.add(new ArrayList<>(ckIdx)); // 해당 index 조합 검증된 ck에 추가
                }
            }
        }
        return ckList.size(); // 검증된 ck 개수
    }

    /**
     * 파이썬의 itertools.combinations 재귀함수로 구현함
     * @param list : 조합에 사용 될 숫자 리스트
     * @param n : 조합 내 숫자 개수
     * @return : 숫자 n개짜리 가능한 조합 전체 반환
     */
    private List<List<Integer>> combinations(List<Integer> list, int n) {
        List<List<Integer>> result = new ArrayList<>();
        if (n == 1) { // 1개면 덧붙일 필요 없이 item 1개로 이루어진 ArrayList 반환함
            for (Integer item : list) {
                List<Integer> comb = new ArrayList<>();
                comb.add(item);
                result.add(comb);
            }
        } else if (n == list.size()) { // 숫자 n개로 구성된 조합 완성
            result.add(new ArrayList<>(list));
        } else { // n개 열심히 채우기
            for (int i = 0; i < list.size() - n + 1; i++) {
                Integer item = list.get(i);
                List<Integer> rest = new ArrayList<>(list.subList(i + 1, list.size()));
                for (List<Integer> comb : combinations(rest, n - 1)) { // 재귀로 구현함 -> n개 ~ 1개까지 쭉 재귀 타고 내려가기
                    comb.add(item); // 뒤에 item 하나씩 덧붙이기
                    result.add(comb);
                }
            }
        }
        return result;
    }
}
