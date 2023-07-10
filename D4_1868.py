# 파핑파핑 지뢰찾기

from collections import deque

T = int(input())
for test_case in range(1, T + 1):
    N = int(input())
    mine = []
    for _ in range(N):
        mine.append(list(input()))

    ans = 0
    visited = [[False]*N for _ in range(N)]

    queue = deque()

    add_x, add_y = [-1, -1, -1, 0, 1, 1, 1, 0], [-1, 0, 1, 1, 1, 0, -1, -1]

    def check_8sides(now_x, now_y):
        for i in range(8):
            new_x, new_y = now_x+add_x[i], now_y+add_y[i]
            if 0 <= new_x < N and 0 <= new_y < N:
                if mine[new_x][new_y]=='*':
                    return False
        return True
        
    def BFS():
        while queue:
            now_x, now_y = queue.popleft()
            mine[now_x][now_y] = 'o'
            for i in range(8):
                new_x, new_y = now_x+add_x[i], now_y+add_y[i]
                if 0 <= new_x < N and 0 <= new_y < N and mine[new_x][new_y] == '.':
                    mine[new_x][new_y] = 'o'
                    if check_8sides(new_x, new_y):
                        queue.append((new_x, new_y))
    
    cnt = 0
    for i in range(N):
        for j in range(N):
            if mine[i][j] == '.' and check_8sides(i, j):
                queue.append((i, j))
                BFS()
                cnt+=1

    for i in range(N):
        for j in range(N):
            if mine[i][j] == '.':
                cnt+=1
    
    print('#{} {}'.format(test_case, cnt))