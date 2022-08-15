package net.geronimus.text

import org.scalatest.funspec.AnyFunSpec

class UniCharSeqSpec extends AnyFunSpec:

  describe( ".apply( position : Int ) : String" ) {

    it(
      "Throws an IndexOutOfBoundsException when the position is out of range."
    ) {
      assertThrows[ IndexOutOfBoundsException ]( UniCharSeq.empty( 1 ) )
      assertThrows[ IndexOutOfBoundsException ]( UniCharSeq( "hello" )( 5 ) )
      assertThrows[ IndexOutOfBoundsException ]( UniCharSeq( "hello" )( -1 ) )
      assertThrows[ IndexOutOfBoundsException ]( UniCharSeq( "🥸" )( 1 ) )
      assertThrows[ IndexOutOfBoundsException ]( UniCharSeq( "🥸" )( -1 ) )
    }

    it( "Returns the logical character at the position requested." ) {
    
      assert( UniCharSeq( "Émile" )( 2 ) == "i" )
      assert( UniCharSeq( "😎🥸🥳" )( 1 ) == "🥸" )
      assert( UniCharSeq( "😇\u202fÉmilie" )( 2 ) == "É" )
    }
  }

  describe( ".equals( that : Any ) : Boolean" ) {

    it(
      "UniCharSeq values containing the same (normalized) text compare as " +
        "equal."
    ) {
      assert( UniCharSeq( "foo" ) != UniCharSeq( "bar" ) )
      assert( UniCharSeq( "\u0045\u0301" ) != UniCharSeq( "\u0045\u0302" ) )
      assert( UniCharSeq( "😎" ) != UniCharSeq( "🥸" ) )

      assert( UniCharSeq( "Hello, Dolly!" ) == UniCharSeq( "Hello, Dolly!" ) )
      assert( UniCharSeq( "\u0045\u0301mile" ) == UniCharSeq( "\u00c9mile" ) )
      assert( UniCharSeq( "🙈🙉🙊" ) == UniCharSeq( "🙈🙉🙊" ) )
    }
  }

  describe( ".hashCode : Int" ) {

    it(
      "UniCharSeq values containing the same (normalized) text have the same " +
        "hash code."
    ) {
      assert( UniCharSeq( "foo" ).hashCode != UniCharSeq( "bar" ).hashCode )
      assert(
        UniCharSeq( "\u0045\u0301" ).hashCode !=
          UniCharSeq( "\u0045\u0302" ).hashCode
      )
      assert( UniCharSeq( "😎" ).hashCode != UniCharSeq( "🥸" ).hashCode )

      assert(
        UniCharSeq( "Hello, Dolly!" ).hashCode ==
          UniCharSeq( "Hello, Dolly!" ).hashCode
      )
      assert(
        UniCharSeq( "\u0045\u0301mile" ).hashCode ==
          UniCharSeq( "\u00c9mile" ).hashCode
      )
      assert(
        UniCharSeq( "🙈🙉🙊" ).hashCode ==
          UniCharSeq( "🙈🙉🙊" ).hashCode
      )
    }
  }

  describe( ".head : String" ) {

    it( "If the sequence is empty, throws a NoSuchElementException." ) {
      
      assertThrows[ NoSuchElementException ]( UniCharSeq.empty.head )
    }

    it(
      "Returns the head element, whether a single char or a surrogate pair."
    ) {
      assert( UniCharSeq( "Émile" ).head == "É" )
      assert( UniCharSeq( "🫥 Choose……" ).head == "🫥" )
    }

    it(
      "Returns the Normalization Form C version of the first character, if " +
        "there are multiple possible representations."
    ) {
      assert( UniCharSeq( "\u0045\u0301mile" ).head == "\u00c9" )
    }
  }

  describe( ".length : Int" ) {

    it( "The length of an empty UniCharSeq is zero." ) {

      assert( UniCharSeq.empty.length == 0 )
    }

    it(
      "A UniCharSeq of a String of low surrogate pairs is the same length " +
        "as the String."
    ) {
      val test = "Hello, World!"
      assert( UniCharSeq( test ).length == test.length )
    }

    it(
      "A UniCharSeq of a String of 3 two-byte UTF-16 characters has a " +
        "length of 3."
    ) {
      assert( UniCharSeq( "😎🥸🥳" ).length == 3 )
    }
  }

  describe( ".slice( from : Int, until : Int ) : String" ){
    
    it(
      "When arguments are out of bounds, we coerce them to the min and " +
        "max values respectively."
    ) {
      val test = "🥸\u202fGroucho 😝\u202fChico 😋\u202fHarpo"

      assert( UniCharSeq( test ).slice( -5, 9 ) == "🥸\u202fGroucho" )
      assert( UniCharSeq( test ).slice( 18, 255 ) == "😋\u202fHarpo" )
      assert(
        UniCharSeq( test ).slice( -255, 255 ) ==
          "🥸\u202fGroucho 😝\u202fChico 😋\u202fHarpo"
      )
    }

    it( "Operates on logical characters rather than Char values." ) {
      assert(
        UniCharSeq( "🪤🐁 🐈  🐕   🏃    🔥" ).slice( 3, 11 ) ==
          "🐈  🐕   🏃"
      )
    }
  }
  
  describe( ".tail : String" ){

    it(
      "Throws an UnsupportedOperationException when you call it on an empty " +
        "sequence."
    ) {
      assertThrows[ UnsupportedOperationException ]( UniCharSeq.empty.tail )
    }

    it(
      "When you call it on a one-character sequence, it returns the empty " +
        "String."
    ) {
      assert( UniCharSeq( "x" ).tail == "" )
      assert( UniCharSeq( "\u0045\u0302" ).tail == "" )
      assert( UniCharSeq( "🤔" ).tail == "" )
    }

    it(
      "Returns all logical characters minus the first, regardless of whether " +
        "it is physically a single-Char or a surrogate pair."
    ) {
      assert( UniCharSeq( "Parse" ).tail == "arse" )
      assert( UniCharSeq( "\u0045\u0301mile" ).tail == "mile" )
      assert( UniCharSeq( "🙈🙉🙊" ).tail == "🙉🙊" )
    }
  }

  describe( ".toSeq : Seq[ String ]" ) {

    it( "An empty UniCharSeq gives an empty Seq." ) {
  
      assert( UniCharSeq.empty.toSeq == Seq.empty )
    }

    it(
      "The Seq returned contains the normalized logical characters, in order."
    ) {
      assert(
        UniCharSeq( "Hello, Dolly!" ).toSeq ==
          Seq( "H", "e", "l", "l", "o", ",", " ", "D", "o", "l", "l", "y", "!" )
      )
      assert(
        UniCharSeq( "\u0045\u0301mile" ).toSeq ==
          Seq( "\u00c9", "m", "i", "l", "e" )
      )
      assert(
        UniCharSeq( "🙈🙉🙊" ).toSeq ==
          Seq( "🙈", "🙉", "🙊" )
      )
    }
  }

  describe( ".toString : String" ) {

    it( "Returns the normalized version of the input text." ) {

      assert( UniCharSeq( "Hello, Dolly!" ).toString == "Hello, Dolly!" )
      assert( UniCharSeq( "\u0045\u0301mile" ).toString == "\u00c9mile" )
      assert( UniCharSeq( "🙈🙉🙊" ).toString == "🙈🙉🙊" )
    }
  }

