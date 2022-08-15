package net.geronimus.text

import org.scalatest.funspec.AnyFunSpec

class FilterUnprintableSpec extends AnyFunSpec:

  val filtered = "There is a mole right at the top of British Intelligence."

  describe( ".filterUnprintable( text : String ) : String" ) {

    it( "Given the empty String, returns the empty String." ) {
    
      assert( filterUnprintable( "" ) == "" )
    }

    it(
      "Given a value with no unprintable characters, it returns the same value."
    ) {
      assert( filterUnprintable( filtered ) == filtered )
    }

    it(
      "Given a value containing unprintable characters, it returns the " +
        "value with all of the unprintable characters removed."
    ) {
      assert(
        filterUnprintable(
          "\u0002There is a mole\u001e right at the top of British " +
            "\u0095Intelligence.\u0007"
        ) == filtered
      )
    }
  }

