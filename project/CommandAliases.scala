import sbt.addCommandAlias
import sbt.internal.DslEntry

object CommandAliases {

  def addCommandAliases(): DslEntry = {
    addCommandAlias("format", ";scalafmt;test:scalafmt")
  }

}