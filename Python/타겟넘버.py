# 프로그래머스 Lv.2 타겟 넘버
# BFS로 확산해가며 해결
# 숫자 배열 앞에서부터 +, - 일 경우 계산하여 배열에 저장
# parent 노드에 다음 숫자 +, - 일 경우 2가지로 계산하여 leaf 배열에 저장
def solution(numbers, target):
    answer = 0
    tree = [0]
    for i in numbers:
        leaves = []
        for parent in tree: # 이전 계산 결과 저장한 parent 노드
            leaves.append(parent+i) # 더한 경우
            leaves.append(parent-i) # 뺀 경우
        tree = leaves # 자식 노드를 부모 노드로
    for i in tree:
        if i == target:
            answer+=1
    return answer