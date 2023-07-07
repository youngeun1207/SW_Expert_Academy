# [S/W 문제해결 응용] 4일차 - 보급로

from collections import deque

C = int(input())
for test_case in range(1, 1+C):
    N = int(input())

    maps = []
    for _ in range(N):
        maps.append(list(map(int, list(input()))))

    ans = 0
    dp = [[float('inf')] * N for _ in range(N)]
    dp[0][0] = 0

    q = deque()
    q.append((0, 0))

    dirs = [(-1, 0), (1, 0), (0, -1), (0, 1)] # 상하좌우

    while q:
        now_y, now_x = q.popleft()
        for add_y, add_x in dirs:
            y, x = now_y + add_y, now_x + add_x
            if (0 <= y < N) and (0 <= x < N):
                # 해당 노드까지 가는 새로운 경로 cost가 기존 cost보다 작으면 업데이트
                cost = dp[now_y][now_x] + maps[y][x]
                if dp[y][x] > cost:
                    dp[y][x] = cost
                    q.append((y, x))

    ans = dp[-1][-1]
    print("#{} {}".format(test_case, ans))