import random
import string
#initial values
number=28

def vgen():
    for x in string.ascii_lowercase:
        yield x

    gen = vgen()
    for x in string.ascii_lowercase:
        for y in gen:
            yield x+y



with open("test","w+") as f:
    f.truncate()
    ver = 0
    con = 0
    vlist = []
    name = vgen()
    ver_list = [next(name) for i in range(number)]
    #for i in range(1,number+1):
    #   nam=""
    #   for k in int(i/26 - i%1):
    #       nam.append(chr(96+i))
    print(ver_list)
    how_many=random.random()*number
    how_many=int(how_many-how_many%1+number)
    for i in range(1,how_many+1):
        chosen1=random.random()*len(ver_list)
        chosen1=int(chosen1-chosen1%1)
        chosen2=random.random()*(len(ver_list)-1)
        chosen2=int(chosen2-chosen2%1)
        if chosen2>=chosen1:
            chosen2=chosen2+1
        vlist.append([ver_list[chosen1],ver_list[chosen2],int(1+random.random()*20)])
    f.write(str(number)+" "+str(len(vlist))+"\n")
    for ver in ver_list:
        f.write(str(ver)+"\n")
    for edge in vlist:
        f.write(' '.join([edge[0], edge[1], str(edge[2])+"\n"]))
