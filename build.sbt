val scala3Version = "3.3.0"

val DoobieVersion = "1.0.0-RC2"
val Http4sVersion = "0.23.19"
val CirceVersion = "0.14.5"

lazy val root = project
  .in(file("."))
  .settings(
    name := "scala3",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := scala3Version,

    libraryDependencies += "org.scalameta" %% "munit" % "0.7.29" % Test,
    libraryDependencies += "org.typelevel" %% "cats-core" % "2.9.0",
    libraryDependencies += "org.typelevel" %% "cats-effect" % "3.5.0",

    libraryDependencies ++= Seq(
      "org.tpolecat" %% "doobie-core"     % DoobieVersion,
      "org.tpolecat" %% "doobie-postgres" % DoobieVersion,
      "org.tpolecat" %% "doobie-hikari"   % DoobieVersion,
      "org.http4s"   %% "http4s-ember-server" % Http4sVersion,
      "org.http4s"   %% "http4s-ember-client" % Http4sVersion,
      "org.http4s"   %% "http4s-circe"    % Http4sVersion,
      "org.http4s"   %% "http4s-dsl"      % Http4sVersion,
      "io.circe"   %% "circe-generic" % CirceVersion,
    )
  )
