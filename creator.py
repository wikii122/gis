import random
import string
from graphviz import Digraph
#initial values
# Liczba wierzcholkow
number=12000#random.randrange()
# Liczba krawedzi
no_egde = 50000

def vgen():
    for x in string.ascii_lowercase:
        yield x

    gen = vgen()
    for x in gen:
        for y in string.ascii_lowercase:
            yield x+y


with open("test","w+") as f:
    f.truncate()
    dot = Digraph(comment='Graf stworzony na potrzeby GIS')

    name = vgen()

    ver_list = [next(name) for i in range(number)]

    print(ver_list)

    vertices = [ver_list[0]]
    egdes = []
    for x in ver_list[1:]:
        dot.node(x)
        v = random.choice(vertices)
        vertices.append(x)
        egdes.append((v, x, 1))#random.randrange(100)))

    for x in range(no_egde - len(egdes)):
        vs = random.sample(vertices, 2)
        vs = sorted(vs)
        egdes.append((vs[0], vs[1], 1))#random.randrange(100)))

    egdes = sorted(egdes)
    prev = None
    all_edges = []
    for edge in egdes:
        name1, name2, cost = edge
        name = [name1, name2]
        if name != prev:
            all_edges.append(edge)
            prev = name


    f.write(str(number)+" "+str(len(all_edges))+"\n")

    for ver in vertices:
        f.write(str(ver)+"\n")

    for edge in all_edges:
        f.write(' '.join([edge[0], edge[1], str(edge[2])+"\n"]))
        dot.edge(edge[0], edge[1], label = str(edge[2]), arrowhead = "none")
    f.close()
    print("DONE!")
    dot.render('test.gv', view=True)
