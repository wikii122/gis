package gis.graph


class PathFinder[A](g: Graph[A]) extends Router[A](g) {
  override protected[this] def findRoutes(vertex: graph.Vertex, jumps: Int, state: Map[A, Route]): Map[A, Route] = ???
}
