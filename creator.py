import random
import string
#initial values
number=3
no_egde = 4

def vgen():
    for x in string.ascii_lowercase:
        yield x

    gen = vgen()
    for x in string.ascii_lowercase:
        for y in gen:
            yield x+y


with open("test","w+") as f:
    f.truncate()

    name = vgen()

    ver_list = [next(name) for i in range(number)]

    print(ver_list)

    vertices = [ver_list[0]]
    egdes = []
    for x in ver_list[1:]:
        v = random.choice(vertices)
        vertices.append(x)
        egdes.append((v, x, random.randrange(100)))

    for x in range(no_egde - len(egdes)):
        vs = random.sample(vertices, 2)
        vs = sorted(vs)
        egdes.append((vs[0], vs[1], random.randrange(100)))

    egdes = sorted(egdes)
    prev = None
    all_edges = []
    for edge in egdes:
        *name, cost = edge
        if name != prev:
            all_edges.append(edge)
            prev = name


    f.write(str(number)+" "+str(len(all_edges))+"\n")

    for ver in vertices:
        f.write(str(ver)+"\n")

    for edge in all_edges:
        f.write(' '.join([edge[0], edge[1], str(edge[2])+"\n"]))
