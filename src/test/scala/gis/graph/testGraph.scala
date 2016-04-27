package gis.graph

import gis.UnitTest

/**
  * Tests for Graph class
  */
class testGraph extends UnitTest{
  "Graph" must "have empty constructor" in {
    val graph = Graph[String]

    graph.vertices shouldBe empty
    graph.edges shouldBe empty
  }

  it should "allow to be constructed from given set of data" in {
    val graph = Graph(
      "1" :: "2" :: "3" :: Nil,
      ("1", "2", 1) ::
        ("3", "2", 2) ::
        ("2", "3", 4) ::
        ("1", "3", 5) :: Nil
    )

    graph.vertices.length shouldEqual 3
    graph.edges.length shouldEqual 4
  }

  it should "add new vertex to itself" in {
    val name = "test"
    val emptyGraph = Graph[String]
    val graph = emptyGraph + name

    graph.vertices.length shouldBe 1
    graph.vertices.head.name shouldBe name
    graph.edges shouldBe empty
  }

  it should "allow to add edge connecting two vertices" in {
    val name1 = "test1"
    val name2 = "test2"
    val cost = 2
    val initialGraph = Graph[String] + name1 + name2
    val graph = initialGraph + (name1, name2, cost)

    graph.vertices.length shouldEqual 2
    graph.edges.length shouldEqual 1
    graph.edges.head.cost shouldEqual cost

    name1::name2::Nil should contain (graph.edges.head.vertices._1.name)
    name1::name2::Nil should contain (graph.edges.head.vertices._2.name)
  }

  val name1 = "test1"
  val name2 = "test2"
  val cost = 2
  def minimalGraph = Graph[String] + name1 + name2 + (name1, name2, cost)

  "Vertex" must "have unique name" in {
    val graph = minimalGraph + name1

    graph.vertices.length shouldEqual 2
  }

  it should "list outgoing edges" in {
    val graph = minimalGraph

    graph.vertices.head.edges.length shouldEqual 1
  }

  "Edge" must "contain edges added to Graph" in {
    val graph = minimalGraph

    intercept[NoSuchElementException] {
      graph + (name1, "intruder", 2)
    }

    graph.edges.length shouldEqual 1
    graph.vertices.length shouldEqual 2
  }

  it must "correctly check if it contains an edge" in {
    val graph = minimalGraph

    assert(graph.edges.head contains name1)
    assert(graph.edges.head contains name2)
    assertNot(graph.edges.head contains "intruder")
  }
}
