from itertools import combinations
# 프로그래머스 Lv.2 후보키
# 2019 KAKAO BLIND RECRUITMENT
# 4.00ms, 10.2MB
# 단순 구현 문제
def solution(relation):
    answer = 0
    l = list(range(len(relation[0])))
    ck_list = []
    for i in range(len(relation[0])):
        for ck_idx in combinations(l, i+1):
            isDuplicate = False
            for ck in ck_list:
                if len(set(ck + list(ck_idx))) == i+1:
                    isDuplicate = True
                if isDuplicate:
                    break
            if isDuplicate:
                continue
            ck_col = []
            for j in range(len(relation)):
                ck_str = ""
                for k in range(i+1):
                    ck_str += ' '
                    ck_str += relation[j][ck_idx[k]]
                ck_col.append(ck_str)
            if len(relation) == len(set(ck_col)):
                ck_list.append(list(ck_idx))
    return len(ck_list)