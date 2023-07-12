# SWEA
# 창용 마을 무리의 개수
from collections import defaultdict as dd

T = int(input())
for tc in range(T):
    N, M = map(int, input().split())
    people = dd(list)
    for _ in range(M):
        p1, p2 = map(int, input().split())
        # 서로 아는 사이이므로 양방향 간선 그래프
        people[p1].append(p2)
        people[p2].append(p1)

    res = 0
    stack = []
    visited = [False] * (N+1)

    for i in range(1, N+1):
        # visited면 이미 그룹에 들어가있는 것
        if visited[i]:
            continue

        for p in people[i]:
            stack.append(p)
        # DFS    
        while stack:
            p = stack.pop()
            visited[p] = True
            for friend in people[p]:
                if not visited[friend]:
                    stack.append(friend)
        res+=1
    print('#{} {}'.format(tc+1, res))