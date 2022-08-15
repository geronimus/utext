package net.geronimus.text

import java.text.Normalizer

import scala.util.hashing.MurmurHash3

/** Creates instances of a `UniCharSet` and contains its static values and
  * methods.
  */
object UniCharSeq:

  /** The empty `UniCharSeq`, containing no characters. */
  val empty : UniCharSeq = new UniCharSeq( "" )

  /** Creates a `UniCharSeq` from a `String` value. */
  def apply( ofString : String ) =
    if ofString == "" then
      empty
    else
      new UniCharSeq( ofString )

  /** Given a `String` value, this function returns a version of the same
    * `String` with Unicode normalization form C (NFC) applied. (Canonical
    * decomposition, followed by canonical composition.)
    *
    * Effectively, this means that glyphs made by composing characters with
    * combining characters will be substituted with a single character. For
    * example, given the value _à_, composed from the Unicode characters
    * `U+0061` and `U+0300`, `normalize` will return the identical glyph _à_,
    * but represented using the single Unicode character `U+00e0`.
    *
    * @param text The text value to be normalized using Unicode normalization
    * form C (NFC).
    */
  def normalize( text : String ) : String =
    Normalizer.normalize( text, Normalizer.Form.NFC ).nn

/** Represents a `String` as a sequence of logical Unicode characters, rather
  * than `Char` values. In the UTF-16 representation used by the Java Virtual
  * Machine (and the Scala runtime), a single `Char` value may only represent
  * half of what human users would recognize as a character. (See
  * [Character][1].)
  *
  * UniCharSeq makes it easier, for example, to determine the real `length` of a
  * `String` containing multi-`Char` Unicode characters, such as Emoji and
  * glyphs from historical languages. All characters are treated equally,
  * regardless of their underlying number of `Char` values.
  *
  * `UniCharSeq` normalizes its text value according to Unicode normalization
  * form C. This means that it converts characters created using combining
  * diacritic characters to a standard, single-character representation of the
  * same glyph. (See `object UniCharSeq.normalize`.)
  *
  * `UniCharSeq` provides an `apply` method to allow you to access individual
  * characters by index value, and `head` and `tail` methods to help with
  * iteration. For more advanced constructs, such as `filter` and `map`, it
  * provides a `toSeq` method to convert the value to a sequence of `String`
  * values, representing the individual logical characters in the `String`.
  *
  * [1]: [[https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Character.html]]
  */
class UniCharSeq private( ofString : String ) derives CanEqual:

  /** The normalized text value contained in this `UniCharSeq`. */
  val text = UniCharSeq.normalize( ofString )
  private val charIndex = makeIndex( text )

  /** Indicates whether or not this `UniCharSeq` contains the empty `String`. */
  val isEmpty = text == ""

  /** The number of logical characters (not `Char`s) in this this `UniCharSeq`.
    */
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

  override def equals( other : Any ) : Boolean = other match
    case that : UniCharSeq => this.text == that.text
    case _ => false

  override def hashCode : Int = MurmurHash3.finalizeHash(
    MurmurHash3.mixLast( 31, text.hashCode ),
    1
  )

  /** The first logical character (not `Char` value) in this `UniCharSeq`.
    * For example, if the underlying text starts with an emoji character, you
    * get the whole emoji as a `String`, not one half of a surrogate pair as a
    * `Char`.
    */
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

  /** The number of logical characters (not `Char`s) in this this `UniCharSeq`.
    */
  def size : Int = length

  /** Returns a portion of the `UniCharSeq`'s underlying text value, according
    * to the arguments you provide.
    *
    * @param from The (offset) index at which you want the portion of the text
    * to begin. For example, if you wanted to include the first character in
    * in the text value, you would use `0`. If you wanted to start at the
    * second character, you would use `1`.
    *
    * If you provide a value less than `0`, then `UniCharSeq` will substitute
    * `0`.
    *
    * Remember that the index values that you're operating on represent logical
    * characters in the `UniCharSeq`, and not `Char` values in a `String`.
    *
    * @param until The (offset) index after which you would like the portion of
    * the text to end. (This index is excluded from the returned `String`.) For
    * example, to take only the first character, use `.slice( 0, 1 )`. To take
    * the second, third, and fourth characters, use `.slice( 1, 4 )`.
    *
    * If you provide a value greater than the final index in the text,
    * `UniCharSeq` will substitute the its length, meaning the final index plus
    * `1`.
    *
    * If you provide a value smaller than or equal to the `from` argument,
    * `UniCharSeq` will return the empty `String`.
    *
    * Remember that the index values that you're operating on represent logical
    * characters in the `UniCharSeq`, and not `Char` values in a `String`.
    */
  def slice( from : Int, until : Int ) : String = {

    val start = from.max( 0 )
    val end = until.min( charIndex.length ) - 1

    if start >= end then ""
    else text.slice(
      charIndex( start ).begin,
      charIndex( end ).end
    )
  }

  /** Returns all the `String` of all logical characters in the text value,
    * minus the first.
    */
  def tail : String =
    if isEmpty then
      throw new UnsupportedOperationException(
        "You cannot get the tail element of an empty sequence."
      )
    else if charIndex.length == 1 then ""
    else text.substring( charIndex( 1 ).begin ).nn

  /** Returns an immutable sequence where each element is one logical character
    * in the `UniCharSeq`'s underlying text value.
    */
  def toSeq : Seq[ String ] =
    if isEmpty then Vector.empty[ String ]
    else vectorize( text, charIndex )

  /** Returns the underlying, normalized `text` value of this `UniCharSeq`. */
  override def toString : String = text

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

  private def vectorize(
    text : String,
    index : Seq[ IndexEntry ],
    result : Vector[ String ] = Vector.empty,
    currentPos : Int = 0
  ) : Vector[ String ] =
    if currentPos >= index.length then result
    else vectorize(
      text,
      index,
      result :+
        text.slice( index( currentPos ).begin, index( currentPos ).end ),
      currentPos + 1
    )

  private case class IndexEntry( begin : Int, end : Int )

