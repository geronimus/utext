package net.geronimus.text

import org.scalatest.funspec.AnyFunSpec

class ContainsUnprintableSpec extends AnyFunSpec:
  
  describe( ".containsUnprintable( text : String ) : Boolean" ) {

    it( "Returns `false` for the empty String." ) {

      assert( !containsUnprintable( "" ) )
    }

    it( "Returns false for text not containing any unprintable characters." ) {

      assert(
        !containsUnprintable(
          "There is a mole right at the top of British Intelligence."
        )
      )
    }

    it(
      "Returns true when the text does contain unprintable characters, at " +
        "any position."
    ) {
      assert(
        containsUnprintable(
          "\u0002There is a mole right at the top of British Intelligence."
        )
      )
      assert(
        containsUnprintable(
          "There is a mole\u001eright at the top of British Intelligence."
        )
      )
      assert(
        containsUnprintable(
          "There is a mole right at the top of British Intelligence.\u0007"
        )
      )
    }
  }

