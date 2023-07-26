import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 프로그래머스 Lv.2 오픈채팅방
 * 2019 KAKAO BLIND RECRUITMENT
 * @author youngeun
 *
 * 단순구현 문제
 * Map으로 유저 아이디 - 닉네임 해싱
 * 닉네임 변경 시 출력 메시지 로직은 순회하거나 수정할 필요 없이 결과 출력시에만 반영
 */
class OpenChats {
    public String[] solution(String[] records) {
        // commands와 users는 1:1 매핑됨
        List<String> commands = new ArrayList<>(); // 유저 출입 메시지 저장
        List<String> users = new ArrayList<>(); // 출입한 유저 아이디 저장
        Map<String, String> nicknames = new HashMap<>(); // 아이디 : 닉네임

        for (String record : records) {
            String[] parsedCmd = record.split(" ");
            switch (parsedCmd[0]) {
                case "Enter":
                    commands.add("님이 들어왔습니다.");
                    users.add(parsedCmd[1]);
                    nicknames.put(parsedCmd[1], parsedCmd[2]); // 닉네임 HashMap에 저장
                    break;
                case "Leave":
                    commands.add("님이 나갔습니다.");
                    users.add(parsedCmd[1]);
                    break;
                case "Change":
                    nicknames.replace(parsedCmd[1], parsedCmd[2]); // 닉네임 HashMap 업데이트
                    break;
            }
        }
        String[] answer = new String[commands.size()];
        for(int i = 0; i < commands.size(); i++){
            answer[i] = nicknames.get(users.get(i)) + commands.get(i); // 닉네임과 입출력 안내문 합치기
        }
        return answer;
    }
}