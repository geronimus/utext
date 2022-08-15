package net.geronimus.text

/** An object containing utility constants and sets to help you work with
  * character data. 
  */
object Characters {

  /** The set of all line ending characters. */
  val lineEndingsSet : Set[ Char ] = Set(
    LineEndings.LineFeed,
    LineEndings.VerticalTab,
    LineEndings.FormFeed,
    LineEndings.CarriageReturn,
    LineEndings.NextLine,
    LineEndings.LineSeparator,
    LineEndings.ParagraphSeparator
  )

  /** The set of all word boundary or horizontal spacing modifier characters. */
  val spacesSet : Set[ Char ] = Set(
    Spaces.HorizontalTab,
    Spaces.Space,
    Spaces.NoBreakSpace,
    Spaces.OghamSpaceMark,
    Spaces.EnQuad,
    Spaces.EmQuad,
    Spaces.EnSpace,
    Spaces.EmSpace,
    Spaces.ThickSpace,
    Spaces.MidSpace,
    Spaces.SixPerEmSpace,
    Spaces.FigureSpace,
    Spaces.PunctuationSpace,
    Spaces.ThinSpace,
    Spaces.HairSpace,
    Spaces.NarrowNoBreakSpace,
    Spaces.MediumMathematicalSpace,
    Spaces.IdeographicSpace,
    Spaces.MongolianVowelSeparator,
    Spaces.ZeroWidthSpace,
    Spaces.ZeroWidthNonJoiner,
    Spaces.ZeroWidthJoiner,
    Spaces.WordJoiner,
    Spaces.ZeroWidthNoBreakSpace
  )

  /** The set of all horizontal and vertical spacing modifier characters,
    * meaning the union of the `lineEndingsSet` and the `spacesSet`.
    */
  val spacingSet = lineEndingsSet ++ spacesSet

  /** The set of all unprintable characters. */
  val unprintableSet : Set[ Char ] = Set(
    Unprintable.NullCharacter,
    Unprintable.StartOfHeading,
    Unprintable.StartOfText,
    Unprintable.EndOfText,
    Unprintable.EndOfTransmission,
    Unprintable.Enquiry,
    Unprintable.Acknowledge,
    Unprintable.Bell,
    Unprintable.Backspace,
    Unprintable.ShiftIn,
    Unprintable.ShiftOut,
    Unprintable.DataLinkEscape,
    Unprintable.DeviceControl1,
    Unprintable.DeviceControl2,
    Unprintable.DeviceControl3,
    Unprintable.DeviceControl4,
    Unprintable.NegativeAcknowledge,
    Unprintable.SynchronousIdle,
    Unprintable.EndOfTransmissionBlock,
    Unprintable.Cancel,
    Unprintable.EndOfMedium,
    Unprintable.SubstituteCharacter,
    Unprintable.Escape,
    Unprintable.FileSeparator,
    Unprintable.GroupSeparator,
    Unprintable.RecordSeparator,
    Unprintable.UnitSeparator,
    Unprintable.Delete,
    Unprintable.PaddingCharacter,
    Unprintable.HighOctetPreset,
    Unprintable.BreakPermittedHere,
    Unprintable.NoBreakHere,
    Unprintable.Index,
    Unprintable.StartOfSelectedArea,
    Unprintable.EndOfSelectedArea,
    Unprintable.CharacterTabulationSet,
    Unprintable.CharacterTabulationWithJustification,
    Unprintable.LineTabulationSet,
    Unprintable.PartialLineForward,
    Unprintable.PartialLineBackward,
    Unprintable.ReverseLineFeed,
    Unprintable.SingleShiftTwo,
    Unprintable.SingleShiftThree,
    Unprintable.DeviceControlString,
    Unprintable.PrivateUse1,
    Unprintable.PrivateUse2,
    Unprintable.SetTransmitState,
    Unprintable.CancelCharacter,
    Unprintable.MessageWaiting,
    Unprintable.StartOfProtectedArea,
    Unprintable.EndOfProtectedArea,
    Unprintable.StartOfString,
    Unprintable.SingleGraphicCharacterIntroducer,
    Unprintable.SingleCharacterIntroIntroducer,
    Unprintable.ControlSequenceIntroducer,
    Unprintable.StringTerminator,
    Unprintable.OperatingSystemCommand,
    Unprintable.PrivateMessage,
    Unprintable.ApplicationProgramCommand
  )

  /** Characters that - either on their own, or in combination - signal the end
    * of a line of text.
    */
  object LineEndings {

    val LineFeed = '\n'
    val VerticalTab = '\u000b'
    val FormFeed = '\u000c'
    val CarriageReturn = '\r'
    val NextLine = '\u0085'
    val LineSeparator = '\u2028'
    val ParagraphSeparator = '\u2029'
  }

  /** Word boundaries and horizontal spacing modifiers. */
  object Spaces {
    val HorizontalTab = '\t'
    val Space = ' '
    val NoBreakSpace = '\u00a0'
    val OghamSpaceMark = '\u1680'
    val EnQuad = '\u2000'
    val EmQuad = '\u2001'
    val EnSpace = '\u2002'
    val EmSpace = '\u2003'
    val ThickSpace = '\u2004'
    val MidSpace = '\u2005'
    val SixPerEmSpace = '\u2006'
    val FigureSpace = '\u2007'
    val PunctuationSpace = '\u2008'
    val ThinSpace = '\u2009'
    val HairSpace = '\u200a'
    val NarrowNoBreakSpace = '\u202f'
    val MediumMathematicalSpace = '\u205f'
    val IdeographicSpace = '\u3000'
    val MongolianVowelSeparator = '\u180e'
    val ZeroWidthSpace = '\u200b'
    val ZeroWidthNonJoiner = '\u200c'
    val ZeroWidthJoiner = '\u200d'
    val WordJoiner = '\u2060'
    val ZeroWidthNoBreakSpace = '\ufeff'
  }

  /** Control characters that do not have an generally accepted graphical
    * representation for printing on-screen.
    */
  object Unprintable {
    
    val NullCharacter = '\u0000'
    val StartOfHeading = '\u0001'
    val StartOfText = '\u0002'
    val EndOfText = '\u0003'
    val EndOfTransmission = '\u0004'
    val Enquiry = '\u0005'
    val Acknowledge = '\u0006'
    val Bell = '\u0007'
    val Backspace = '\u0008'
    val ShiftIn = '\u000e'
    val ShiftOut = '\u000f'
    val DataLinkEscape = '\u0010'
    val DeviceControl1 = '\u0011'
    val DeviceControl2 = '\u0012'
    val DeviceControl3 = '\u0013'
    val DeviceControl4 = '\u0014'
    val NegativeAcknowledge = '\u0015'
    val SynchronousIdle = '\u0016'
    val EndOfTransmissionBlock = '\u0017'
    val Cancel = '\u0018'
    val EndOfMedium = '\u0019'
    val SubstituteCharacter = '\u001a'
    val Escape = '\u001b'
    val FileSeparator = '\u001c'
    val GroupSeparator = '\u001d'
    val RecordSeparator = '\u001e'
    val UnitSeparator = '\u001f'
    val Delete = '\u007f'
    val PaddingCharacter = '\u0080' // Originally intended to 'pad' unneeded
                                    // bytes in multi-byte sets.
    val HighOctetPreset = '\u0081'
    val BreakPermittedHere = '\u0082'
    val NoBreakHere = '\u0083'
    val Index = '\u0084'
    val StartOfSelectedArea = '\u0086'
    val EndOfSelectedArea = '\u0087'
    val CharacterTabulationSet = '\u0088'
    val CharacterTabulationWithJustification = '\u0089'
    val LineTabulationSet = '\u008a'
    val PartialLineForward = '\u008b'
    val PartialLineBackward = '\u008c'
    val ReverseLineFeed = '\u008d'
    val SingleShiftTwo = '\u008e'
    val SingleShiftThree = '\u008f'
    val DeviceControlString = '\u0090'
    val PrivateUse1 = '\u0091'
    val PrivateUse2 = '\u0092'
    val SetTransmitState = '\u0093'
    val CancelCharacter = '\u0094'
    val MessageWaiting = '\u0095'
    val StartOfProtectedArea = '\u0096'
    val EndOfProtectedArea = '\u0097'
    val StartOfString = '\u0098'
    val SingleGraphicCharacterIntroducer = '\u0099'
    val SingleCharacterIntroIntroducer = '\u009a'
    val ControlSequenceIntroducer = '\u009b'
    val StringTerminator = '\u009c'
    val OperatingSystemCommand = '\u009d'
    val PrivateMessage = '\u009e'
    val ApplicationProgramCommand = '\u009f'
  }
}

