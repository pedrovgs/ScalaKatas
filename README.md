# <img alt="Scala" src="https://www.scala-lang.org/resources/img/frontpage/scala-spiral.png" height="60"/> ScalaKatas [![Build Status](https://travis-ci.com/pedrovgs/ScalaKatas.svg?branch=master)](https://travis-ci.com/pedrovgs/ScalaKatas)

Scala training repository used to learn Scala and Functional Programming by solving some common katas using just purely functional programming with [Cats](https://github.com/typelevel/cats).

### List of katas:

### Executing tests:

This project contains some tests written using [ScalaTest](http://www.scalatest.org/) and [ScalaCheck](https://www.scalacheck.org/). You can easily run the tests by executing any of the following commands:

```
sbt test // Runs every test in your project
sbt test-only *AnySpec // Runs specs matching with the filter passed as param.
```

### Checkstyle:

For the project checkstyle we are using [ScalaFMT](https://scalameta.org/scalafmt/). The code format will be evaluated after accepting any contribution to this repository using this tool. You can easily format your code changes automatically by executing ``sbt format``.

Developed By
------------

* Pedro Vicente Gómez Sánchez - <pedrovicente.gomez@gmail.com>

<a href="https://twitter.com/pedro_g_s">
  <img alt="Follow me on Twitter" src="https://image.freepik.com/iconos-gratis/twitter-logo_318-40209.jpg" height="60" width="60"/>
</a>
<a href="https://es.linkedin.com/in/pedrovgs">
  <img alt="Add me to Linkedin" src="https://image.freepik.com/iconos-gratis/boton-del-logotipo-linkedin_318-84979.png" height="60" width="60"/>
</a>

License
-------

    Copyright 2019 Pedro Vicente Gómez Sánchez

    Licensed under the GNU General Public License, Version 3 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.gnu.org/licenses/gpl-3.0.en.html

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
