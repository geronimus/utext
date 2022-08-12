package net.geronimus.text

import java.text.Normalizer

object UniCharSeq:

  val empty : UniCharSeq = new UniCharSeq( "" )

  def apply( ofString : String ) =
    if ofString == "" then
      empty
    else
      new UniCharSeq( ofString )

  def normalize( text : String ) : String =
    Normalizer.normalize( text, Normalizer.Form.NFC ).nn

class UniCharSeq private( ofString : String ):

  private val text = UniCharSeq.normalize( ofString )
  private val charIndex = makeIndex( text )

  val isEmpty = text == ""

  val length = charIndex.length

  def apply( position : Int ) : String =
    if isEmpty then
      throw new IndexOutOfBoundsException(
        "You cannot index into an empty sequence."
      )
    else if position < 0 || position >= length then
      throw new IndexOutOfBoundsException(
        s"Expected: An Int value between 0 and ${ length }.\n" +
        s"Found: ${ position }"
      )
    else
      text.slice(
        charIndex( position ).begin,
        charIndex( position ).end
      )

  def head : String =
    if isEmpty then
      throw new NoSuchElementException(
        "You cannot get the head element of an empty sequence."
      )
    else
      text.slice(
        charIndex( 0 ).begin,
        charIndex( 0 ).end
      )

  def slice( from : Int, until : Int ) : String = {

    val start = from.max( 0 )
    val end = until.min( charIndex.length ) - 1

    if start >= end then ""
    else text.slice(
      charIndex( start ).begin,
      charIndex( end ).end
    )
  }

  def tail : String =
    if isEmpty then
      throw new UnsupportedOperationException(
        "You cannot get the tail element of an empty sequence."
      )
    else if charIndex.length == 1 then ""
    else text.substring( charIndex( 1 ).begin ).nn

  private def makeIndex(
    text : String,
    result : Seq[ IndexEntry ] = Vector.empty,
    currentPos : Int = 0
  ) : Seq[ IndexEntry ] =
  if text.length == 0 || currentPos > text.length - 1 then
    result
  else if text( currentPos ).isHighSurrogate then
    makeIndex(
      text,
      result :+ IndexEntry( currentPos, currentPos + 2 ),
      currentPos + 2
    )
  else
    makeIndex(
      text,
      result :+ IndexEntry( currentPos, currentPos + 1 ),
      currentPos + 1
    )

  private case class IndexEntry( begin : Int, end : Int )

