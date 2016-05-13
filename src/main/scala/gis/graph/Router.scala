package gis.graph

abstract class Router[A] protected (protected val graph: Graph[A]) {
  def find(from: A, to: A, jumps: Int): Option[Route] = {
    val starting = graph.vertices collectFirst {
      case vertex: graph.Vertex if vertex.name == from => vertex
    }
    val routes = findRoutes(starting.get, jumps, Map[A, Route]())

    if (routes.contains(to)) Some(routes(to))
    else None
  }

  class Route()

  protected[this] def findRoutes(vertex: graph.Vertex, jumps: Int, state: Map[A, Route]): Map[A, Route]
}

object Router {
  def apply[A](graph: Graph[A]): Router[A] = ???
}
