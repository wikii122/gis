package gis

import better.files.File
import gis.graph.{Graph, Router}
/**
  * Program launcher.
  *
  * TODO: usage information
  * TODO: help message
  */
object App {
  def main(args: Array[String]) = {
    val conf = parse(args.toList)
    if (conf.isDefined) {
      val graph = File(conf.get.file).lines.map(_.split(" ")).foldLeft(Graph[String]) {
        (g, s) => if (s.length == 1) g + s(0)
          else if (s.length == 3) g + (s(0), s(1), s(2).toInt)
          else g
      }

      val router = Router(graph)
      println(router.find(conf.get.from, conf.get.to, conf.get.jumps))
    }
    else ???
  }

  case class Conf(file: String, from: String, to: String, jumps: Int)
  def parse(args: List[String]) = args match {
    case file::from::to::jumps::Nil if jumps forall Character.isDigit => Some(Conf(file, from, to, jumps.toInt))
    case _ => None
  }
}
