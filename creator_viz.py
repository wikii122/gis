import random
import string
from graphviz import Digraph
#initial values
number=5
no_egde = 10

def vgen():
    for x in string.ascii_lowercase:
        yield x

    gen = vgen()
    for x in string.ascii_lowercase:
        for y in gen:
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
        egdes.append((v, x, random.randrange(100)))

    for x in range(no_egde - len(egdes)):
        vs = random.sample(vertices, 2)
        egdes.append((vs[0], vs[1], random.randrange(1,100)))

    f.write(str(number)+" "+str(len(egdes))+"\n")

    for ver in vertices:
        f.write(str(ver)+"\n")

    for edge in egdes:
        f.write(' '.join([edge[0], edge[1], str(edge[2])+"\n"]))
        dot.edge(edge[0], edge[1], label = str(edge[2]), arrowhead = "none")
    dot.render('test-output/round-table.gv', view=True)
