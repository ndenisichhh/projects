from random import randint as rd
import time
import os

points = 0
b = []
def jng(bet):
    field = [[str(rd(1, 6)) for _ in range(9)] for _ in range(9)]
    b.append(field.copy())

    def checkTop(i, j):
        global counter
        field[i][j] += 'A'
        while i != 0 and field[i][j][0] == field[i-1][j] and len(field[i-1][j]) == 1 and field[i-1][j][0]!='0':
            field[i-1][j] += 'A'
            i-=1
            counter += 1
            checkRight(i, j)
            checkLeft(i, j)
        
    def checkBottom(i, j):
        global counter
        while i != 8 and field[i][j][0] == field[i+1][j] and len(field[i+1][j]) == 1 and field[i+1][j][0]!='0':
            field[i+1][j] += 'A'
            i+=1
            counter += 1
            checkRight(i, j)
            checkLeft(i, j)

    def checkRight(i, j):
        global counter
        while j != 8 and field[i][j][0] == field[i][j+1] and len(field[i][j+1]) == 1 and field[i][j+1][0]!='0':
            field[i][j+1] += 'A'
            j+=1
            counter += 1
            checkTop(i, j)
            checkBottom(i, j)
            

    def checkLeft(i, j):
        global counter
        while j != 0 and field[i][j][0] == field[i][j-1] and len(field[i][j-1]) == 1 and field[i][j-1][0]!='0':
            field[i][j-1] += 'A'
            j-=1
            counter += 1
            checkTop(i, j)
            checkBottom(i, j)

    def deleteZero():
        for i in range(9):
            a = []
            for j in range(8, -1, -1):
                a.append(field[j][i])
            b = 0
            while a[-(a.count('0')):].count('0') != a.count('0'):
                if a[b] == '0':
                    a.append(a.pop(b))
                else: b+=1
            for j in range(9):
                if a[j] == '0':
                    a[j] = str(rd(1, 6))
            for j in range(9):
                field[j][i] = a[8-j]

    def motion():
        global counter, points
        nextMotion = False
        # i - строка, j - столбец
        for i in range(9):
            for j in range(9):
                counter = 1
                checkTop(i, j)
                checkBottom(i, j)
                checkLeft(i, j)
                checkRight(i, j)
                for a in field:
                    for b in a:
                        if 'A' in b:
                            if counter >= 5:
                                field[field.index(a)][a.index(b)] = '0'
                                nextMotion = True
                                points += bet/3
                            else:
                                field[field.index(a)][a.index(b)] = field[field.index(a)][a.index(b)][0]
        return nextMotion

    while motion():
        b.append(field.copy())
        deleteZero()
        b.append(field.copy())
    return b, round(points)

# time 2
# print(jng(100))