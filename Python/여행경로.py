# 프로그래머스 Lv.3 여행경로
# 재귀 DFS + 백트래킹

import copy
from collections import defaultdict as dd

answer = []

def DFS(graph, node, order, n):
    global answer
    if len(order) == n + 1:
        answer = order
        return
    for i in range(len(graph[node])):
        city = graph[node][i]

        order_copy = copy.deepcopy(order)
        order_copy.append(city)

        graph_copy = copy.deepcopy(graph)
        graph_copy[node].pop(i)

        DFS(graph_copy, city, order_copy, n)


def solution(tickets):
    global answer
    graph = dd(list)
    for ticket in tickets:
        graph[ticket[0]].append(ticket[1])
    for edges in graph.values():
        edges.sort(reverse=True)

    # DFS
    DFS(copy.deepcopy(graph), "ICN", copy.deepcopy(["ICN"]), len(tickets))

    return answer