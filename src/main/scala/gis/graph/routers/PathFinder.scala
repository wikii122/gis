package gis.graph
package routers

import scala.annotation.tailrec

class PathFinder[A](g: Graph[A]) extends Router[A](g) {
  @tailrec
  final override protected[this] def findRoutes(vertices: List[graph.Vertex], jumps: Int,
                                          routes: Map[A, Route]): Map[A, Route] = vertices match {
    case Nil => routes
    case vertex :: vertexList => if (routes(vertex.name).vertices.length < jumps) {
        val analyzedEdges = vertex.edges filterNot (edge => {
          val otherVertex = edge opposite vertex.name
          (routes contains otherVertex.name) &&
            (routes(vertex.name).distance + edge.cost >= routes(otherVertex.name).distance)
        })
        val addedVertices = analyzedEdges.foldLeft(vertexList) { (vs: List[graph.Vertex], edge) =>
          val otherVertex = edge opposite vertex.name
          if (vs contains otherVertex) vertices
          else otherVertex.asInstanceOf[graph.Vertex] :: vertices
        }
        val updatedRoutes = analyzedEdges.foldLeft(routes) { (rs: Map[A, Route], edge) =>
          val otherVertex = edge opposite vertex.name
          if (!rs.contains(otherVertex.name) || rs(vertex.name).distance + edge.cost < rs(otherVertex.name).distance) {
            val route = rs(vertex.name)
            routes + (otherVertex.name -> new Route(vertex :: route.vertices, route.distance + edge.cost))
          } else {
            routes
          }
        }
        findRoutes(addedVertices, jumps, updatedRoutes)
      } else findRoutes(vertexList, jumps, routes)
  }
}
