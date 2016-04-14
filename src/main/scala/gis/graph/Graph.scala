package gis.graph

/**
  * Graph is an immutable data container.
  *
  * Actually, Graph is just a convenient wrapper for a vertices and edges
  * being the elements of it. Those structures are represented by inner Vertex
  * and Edge classes, which are constructed on demand.
  */
class Graph[A] private (vertexList: Set[A], edgeList: Set[(A, A, Int)]) {
  protected[this] val graph: Graph[A] = this

  case class Vertex(name: A) {
    lazy val edges = graph.edges filter {_ contains name}
  }

  case class Edge(vertices: (Vertex, Vertex), cost: Int) {
    def contains(v: Vertex): Boolean = contains(v.name)
    def contains(n: A): Boolean = n == vertices._1.name || n == vertices._2.name
  }

  /** List of vertices.*/
  lazy val vertices: List[Vertex] = vertexList map {Vertex(_)} toList
  /** List of edges */
  lazy val edges: List[Edge] = {
    val vertexMap = vertices map {vertex => (vertex.name, vertex)} toMap

    edgeList map {(edge) => val (v1, v2, cost) = edge
      Edge((vertexMap(v1), vertexMap(v2)), cost)
    } toList
  }

  /** Create new graph with additional vertex. */
  def +(node: A): Graph[A] = new Graph(vertexList+node, edgeList)

  /** Create new graph with additional edge */
  def +(node: (A, A, Int)): Graph[A] =
    if ((vertexList contains node._1) &&
      (vertexList contains node._2))
      new Graph(vertexList, edgeList+node)
    else
      throw new NoSuchElementException(s"Vertex ${if (vertexList.contains(node._1)) node._2 else node._1}" +
        s"was not found in the graph")
}

object Graph {
  def apply[A] = new Graph[A](Set.empty, Set.empty)
}
