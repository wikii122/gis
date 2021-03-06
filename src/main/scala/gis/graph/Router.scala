package gis.graph

import gis.graph.routers.{BFS, PathFinder}

/**
 * Base class defining interface for pathfinding elements of the program
 */
abstract class Router[A] protected (protected val graph: Graph[A]) {

  /**
   * Method responsible for preparing data and executing algorithm.
   */
  def find(from: A, to: A, jumps: Int): Option[Route] = {
    val starting = graph.vertices collectFirst {
      case vertex: graph.Vertex if vertex.name == from => vertex
    }
    if (starting.isEmpty) return None
    val routes = findRoutes(List(starting.get), jumps, Map[A, Route](starting.get.name -> Start))

    if (routes.contains(to)) Some(routes(to))
    else None
  }

  /**
   * Container for route leading to destined vertex
   */
  class Route(val vertices: List[graph.Vertex], val distance: Int) {
    override def toString = s"Route($vertices, $distance)"
    def pretty = s"Distance: $distance\n" +
      vertices.reverse.foldLeft("") {(str, v) => str + s"${v.name}\n"}
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
