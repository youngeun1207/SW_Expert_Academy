# 프로그래머스 Lv.2 오픈채팅방
# 2019 KAKAO BLIND RECRUITMENT
# 단순구현
def solution(records):
    answer = []
    commands = []
    users = []
    nicknames = {}
    for record in records:
        cmd = list(record.split())
        if cmd[0] == "Enter":
            commands.append("님이 들어왔습니다.")
            users.append(cmd[1])
            nicknames[cmd[1]] = cmd[2]
        elif cmd[0] == "Leave":
            commands.append("님이 나갔습니다.")
            users.append(cmd[1])
        elif cmd[0] == "Change":
            nicknames[cmd[1]] = cmd[2]
    for i in range(len(commands)):
        answer.append("{}{}".format(nicknames[users[i]], commands[i]))

    return answer