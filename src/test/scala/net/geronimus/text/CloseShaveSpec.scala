package net.geronimus.text

import org.scalatest.funspec.AnyFunSpec

import scala.util.Random

class CloseShaveSpec extends AnyFunSpec {

  describe( "net.geronimus.text.closeShave( text : String ) : String" ) {

    it( "Given an empty String, returns an empty String." ) {

      val empty = ""
      assert( closeShave( empty ) == empty )
    }

    it(
      "Given a String with no leading or trailing space characters, it " +
        "returns the same value."
    ) {

      val alreadyTrim = "Answer: Ginger Rogers'\n  ...and Fred Astaire's"
      assert( closeShave( alreadyTrim ) == alreadyTrim )
    }

    it(
      "Given text surrounded by leading and trailing ordinary spaces and " +
        "linebreaks, it returns only the text in the middle."
    ) {
      val middle = "with utext"
      val paddingChars = Vector( " ", "\n", "\r\n" )
      val padding = randomPadding( paddingChars, 9 )
      val padded = padding + middle + padding

      assert( closeShave( padded ) == middle )
    }

    it(
      "Given text surrounded by leading and trailing unusual spaces and " +
        "linebreaks, it returns only the text in the middle."
    ) {
      val middle = "with\u3000\u2028\u000c\u202futext"
      val paddingChars = Characters.spacingSet
        .map( _.toString )
        .toSet
        .incl( "\r\n" ).toVector
      val padding = randomPadding( paddingChars, 9 )
      val padded = padding + middle + padding

      assert( closeShave( padded ) == middle )
    }
  }

  def randomPadding(
    paddingChars : IndexedSeq[ String ],
    maxLength : Int,
    result : String = ""
  ) : String =
    if result.length >= maxLength then result
    else randomPadding(
      paddingChars,
      maxLength,
      result ++ paddingChars( Random.between( 0, paddingChars.length ) )
    )
}

