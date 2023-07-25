# 백준 21608 상어초등학교
# 삼성전자 2021년도 상반기 기출
# 단순구현 + 가중치
class Shark:
    x = 1
    y = 1
    friends = []
    def __init__(self, x, y, friends):
        self.x = x
        self.y = y
        self.friends = friends

N = int(input())
sharks = {}
favors = {}
fixed_classroom = [[False]*N for _ in range(N)]
dirs = [(-1, 0), (0, -1), (0, 1), (1, 0)] # 우선순위에 맞게 상, 좌, 우, 하

def getScore(shark):
    score = -1
    x = shark.x
    y = shark.y
    for dir_x, dir_y in dirs:
        new_x, new_y = x + dir_x, y + dir_y
        if 0 <= new_x < N and 0 <= new_y < N and fixed_classroom[new_x][new_y] in shark.friends:
            score+=1
    return int(10 ** score)

for x in range(N**2):
    classroom = [[0] * N for _ in range(N)]

    inputs = list(map(int, input().split()))
    shark = inputs[0]
    friends = inputs[1:]

    if x == 0:
        fixed_classroom[1][1] = shark
        sharks[shark] = Shark(1, 1, friends)
        continue

    score = 0
    # 인접한 친구 점수 -> 친구 당 1점
    for friend in friends:
        if sharks.get(friend) == None:
            continue
        x, y = sharks.get(friend).x, sharks.get(friend).y
        for dir_x, dir_y in dirs:
            new_x, new_y = x + dir_x, y + dir_y
            if 0 <= new_x < N and 0 <= new_y < N and not fixed_classroom[new_x][new_y]:
                classroom[new_x][new_y] += 1
                score = max(score, classroom[new_x][new_y])

    # 인접한 빈 자리 점수 -> 빈 자리 당 0.2점 (4면이 다 빈 자리라도, 친구 1명 더 있는게 높은 점수!)
    for x in range(N):
        for y in range(N):
            if fixed_classroom[x][y]:
                continue
            for dir_x, dir_y in dirs:
                new_x, new_y = x + dir_x, y + dir_y
                if 0 <= new_x < N and 0 <= new_y < N and not fixed_classroom[new_x][new_y]:
                    classroom[x][y] += 0.2
                    score = max(score, classroom[x][y])

    # 점수 같으면 왼쪽 위에서부터 착석
    flag = False
    for x in range(N):
        for y in range(N):
            if fixed_classroom[x][y]:
                continue
            if classroom[x][y] == score:
                fixed_classroom[x][y] = shark
                sharks[shark] = Shark(x, y, friends)
                flag = True
                break
        if flag:
            break
answer = 0
for shark in sharks.values():
    answer += getScore(shark)
print(answer)
