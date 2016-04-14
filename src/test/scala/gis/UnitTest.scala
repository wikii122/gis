package gis

import org.scalatest.{Assertions, FlatSpec, Matchers}

/**
  * Default dest definition.
  */
trait UnitTest extends FlatSpec with Matchers {
  def assertNot(b: Boolean) = assert(!b)
}
