package gis.graph

import gis.graph.routers.{BFS, PathFinder}

abstract class Router[A] protected (protected val graph: Graph[A]) {
  def find(from: A, to: A, jumps: Int): Option[Route] = {
    val starting = graph.vertices collectFirst {
      case vertex: graph.Vertex if vertex.name == from => vertex
    }
    val routes = findRoutes(List(starting.get), jumps, Map[A, Route](starting.get.name -> Start))

    if (routes.contains(to)) Some(routes(to))
    else None
  }

  class Route(val vertices: List[graph.Vertex], val distance: Int) {
    override def toString() = s"Route($vertices, $distance)"
  }
  object Start extends Route(Nil, 0)

  protected[this] def findRoutes(vertices: List[graph.Vertex],
                                 jumps: Int,
                                 state: Map[A, Route]): Map[A, Route]
}

object Router {
  def apply[A](graph: Graph[A]): Router[A] =
    if (graph.edges.forall(_.cost == graph.edges.head.cost)) new BFS[A](graph)
    else new PathFinder[A](graph)
}
