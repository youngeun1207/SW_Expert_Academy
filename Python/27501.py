import sys
from collections import  defaultdict as dd
# 백준 27501 RGB트리
N = int(input())
node_dict = dd(list)

for _ in range(N-1):
    p, c = map(int, sys.stdin.readline().split())
    node_dict[p].append(c)
    node_dict[c].append(p)

r_dp = [0] * (N+1)
g_dp = [0] * (N+1)
b_dp = [0] * (N+1)

visited = [False] * (N+1)
route = [""] * (N+1)

for i in range(1, N+1):
    r_dp[i], g_dp[i], b_dp[i] = map(int, sys.stdin.readline().split())

def DFS(node): # 리프노드부터 올라오기
    visited[node] = True
    for c_node in node_dict[node]:
        if not visited[c_node]:
            DFS(c_node)
            # 빨간색 선택 시
            r_dp[node] += max(g_dp[c_node], b_dp[c_node])

            # 초록색 선택 시
            g_dp[node] += max(r_dp[c_node], b_dp[c_node])

            # 파랑색 선택 시
            b_dp[node] += max(r_dp[c_node], g_dp[c_node])
def getOrder(node, prev): # 루트 노드부터 내려가기
    visited[node] = True
    if prev == 'R':
        if g_dp[node] > b_dp[node]:
            route[node] = 'G'
        else:
            route[node] = 'B'
    elif prev == 'G':
        if r_dp[node] > b_dp[node]:
            route[node] = 'R'
        else:
            route[node] = 'B'
    elif prev == 'B':
        if r_dp[node] > g_dp[node]:
            route[node] = 'R'
        else:
            route[node] = 'G'

    for c_node in node_dict[node]:
        if not visited[c_node]:
            getOrder(c_node, route[node])

DFS(1)
max_beauty = max(r_dp[1], g_dp[1], b_dp[1])
print(max_beauty)

if max_beauty == r_dp[1]:
    route[1] = "R"
elif max_beauty == g_dp[1]:
    route[1] = "G"
if max_beauty == b_dp[1]:
    route[1] = "B"

visited = [False] * (N+1)
getOrder(1, "") # 루트 노드의 경우 직전 노드 없으므로 빈 칸 보내기
print("".join(route[1:]))