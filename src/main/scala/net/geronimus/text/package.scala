package net.geronimus

/** Contains utilities to help you operate on text values. */
package object text:

  /** This function works like Java's `String.trim()`, except that it's much
    * more precise. It returns the `String` you pass in, minus any leading or
    * trailing spacing characters.
    *
    * Except that `String.trim()` defines spacing characters as any `char` whose
    * numeric value is 32 (0x20) or less, which includes a number of control
    * characters that are not technically spaces.
    *
    * By contrast, `closeShave` considers spacing characters as the set of
    * character values that the object [[net.geronimus.text.Characters]] defines
    * in `spacingSet`, which means all of the values defined in
    * [[net.geronimus.text.Characters.LineEndings]], plus all of the values
    * defined in [[net.geronimus.text.Characters.Spaces]].
    *
    * Basically this includes all horizontal and vertical spacing modifiers in
    * the Unicode basic multilingual plane, but nothing more.
    *
    * `closeShave` stops trimming at the first non-spacing character that it
    * finds on each side. It will not remove any of these characters from the
    * middle of a value.
    *
    * @param text The text value from which you would like all leading and
    * trailing spacing characters removed.
    */
  def closeShave( text : String ) : String = {
    
    enum SearchDirection:
      case Leading, Trailing

    def trimSide(
      text : String,
      trimChars : Set[ Char ],
      search : SearchDirection
    ) : String =
      if text.isEmpty then text
      else {
        val ( checkValue, rest ) = search match
          case SearchDirection.Leading => ( text.head, text.tail )
          case SearchDirection.Trailing => ( text.last, text.init )

        if !trimChars.contains( checkValue ) then text
        else trimSide( rest, trimChars, search )
      }

    trimSide(
      trimSide( text, Characters.spacingSet, SearchDirection.Leading ),
      Characters.spacingSet,
      SearchDirection.Trailing
    )
  }

  /** Returns `true` if the text contains any characters classified as
    * `Unprintable` in [[net.geronimus.text.Characters.Unprintable]].
    * Otherwise `false`.
    */
  def containsUnprintable( text : String ) : Boolean =
    if text.isEmpty then false
    else if Characters.unprintableSet.contains( text.head ) then true
    else containsUnprintable( text.tail )

  def filterUnprintable( text : String ) : String =
    text.filter( !Characters.unprintableSet.contains( _ ) )

