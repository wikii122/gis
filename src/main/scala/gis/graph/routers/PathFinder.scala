package gis.graph
package routers

import scala.annotation.tailrec

/**
 * Class responsible for pathfinding in graph using modified Dijkstra algorithm 
 * Provided graph should respect following rules:
 * -All edges must connect different vertices 
 */
class PathFinder[A](g: Graph[A]) extends Router[A](g) {
  final private[this] def dfs(vertex: graph.Vertex, jumps: Int, routes: Map[A, Route]): Map[A, Route] = {
    if (jumps == 0)
      return routes
    val edges = vertex.edges.filterNot(e => routes.contains(e.opposite(vertex.name).name))
    edges.foldLeft(routes) {(rs, e) =>
      val other = e.opposite(vertex.name).asInstanceOf[graph.Vertex]
      val route = routes(vertex.name)
      val result = dfs(e.opposite(vertex.name).asInstanceOf[graph.Vertex], jumps-1, routes + (other.name ->
        new Route(other :: route.vertices, route.distance + e.cost)))
      result.foldLeft (rs) {
        (rs, route) =>
          val (key, value) = route
          if (!routes.contains(key) || value.distance < routes(key).distance) rs.updated(key, value)
          else rs
      }
    }
  }

  final override protected[this] def findRoutes(vertices: List[graph.Vertex], jumps: Int,
                                          routes: Map[A, Route]): Map[A, Route] = dfs(vertices.head, jumps, routes)
}
