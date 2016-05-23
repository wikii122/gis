package gis.graph
package routers

import scala.annotation.tailrec

class BFS[A](g: Graph[A]) extends Router[A](g) {
  @tailrec
  final override protected[this] def findRoutes(vertices: List[graph.Vertex], jumps: Int,
                                          routes: Map[A, Route]): Map[A, Route] = vertices match {
    case Nil => routes
    case vertex :: vertexList =>
      if (routes(vertex.name).vertices.length < jumps) {
        val analyzed = vertex.edges.filterNot(r => routes contains r.opposite(vertex.name).name)
        val addedVertices = analyzed map (_ opposite vertex.name)
        val updatedRoutes = analyzed.foldLeft(routes){(nameAll, edge) =>
          val route = nameAll(vertex.name)
          nameAll + (edge.opposite(vertex.name).name -> new Route(vertex :: route.vertices, route.distance + edge.cost))}

        findRoutes(addedVertices.asInstanceOf[List[graph.Vertex]], jumps, updatedRoutes)
      } else findRoutes(vertexList, jumps, routes)
  }
}
