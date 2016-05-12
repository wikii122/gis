import random
import string
#initial values
number=3
no_egde = 7

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

    vertices = [ver_list[0]]
    egdes = []
    for x in ver_list[1:]:
        v = random.choice(vertices)
        vertices.append(x)
        egdes.append((v, x, random.randrange(100)))

    for x in range(no_egde - len(egdes)):
        vs = random.sample(vertices, 2)
        egdes.append((vs[0], vs[1], random.randrange(100)))

    f.write(str(number)+" "+str(len(egdes))+"\n")
    for ver in vertices:
        f.write(str(ver)+"\n")
    for edge in egdes:
        f.write(' '.join([edge[0], edge[1], str(edge[2])+"\n"]))
