# 프로그래머스 Lv.3 단어 변환
# BFS 활용
# 단어 목록에서 현재 단어와 알파벳 하나만 차이나는 단어는 queue에 넣기
# 중복해서 들어가지 않도록 주의!
from collections import deque
def solution(begin, target, words):
    if target not in words:  # 타겟 단어가 없으면 그냥 종료
        return 0

    queue = deque()
    queue.append((begin, 0))  # 현재 단어와, 몇 번 연산한 결과인지 저장
    n = len(words)
    while queue:  # BFS
        word, idx = queue.popleft()

        if word == target:
            return idx

        for i in range(n):
            cnt = 0
            new_word = words[i]
            for j in range(len(new_word)):
                if word[j] == new_word[j]:  # 한 글자씩 비교하기
                    cnt += 1
            if cnt == len(target) - 1:  # 1글자 빼고 다 같은 단어는 queue에 삽입
                queue.append((words[i], idx + 1))
                words[i] = '0'  # 중복해서 들어가지 않도록 단어 바꾸기 (visited 역할)

