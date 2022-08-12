ThisBuild / organization := "net.geronimus"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := "3.1.3"

lazy val utext = ( project in file( "." ) )
  .settings(
    name := "utext",
    description :=
      "Provides utility functions for working with text values (ie, " +
        "Strings). For these functions, the abstraction of working with " +
        "\"text\" should not break down when faced with Unicode surrogate " +
        "pairs.",
    libraryDependencies ++= Seq(
      scalatest
    ),
    scalacOptions ++= Seq(
      "-deprecation",     // Display detailed info when a feature is deprecated.
      "-explain",         // Explain errors in detail.
      "-explain-types",   // Explain type errors in detail.
      "-feature",         // Warn me when I should explicitly import a feature.
      "-indent",          // Allow Scala 3 significant indentation.
      "-new-syntax",      // Use then and do in control structures, not parens.
      "-print-lines",     // Show source code line numbers.
      "-Xmigration",      // Warn me when the new version changes things.
      "-Yexplicit-nulls"  // Ref types are not nullable.
    )
  )

// External libraries:
lazy val scalatest = "org.scalatest" %% "scalatest" % "3.2.13" % Test

