# Pygmy Marmoset: an assignment submission webapp for CS courses

![Pygmy marmoset photo](PygmyMarmosetWebapp/war/img/pygmyMarmoset-crop.jpg)

Pygmy Marmoset is a very simple assignment submission web application for CS courses.

It is designed as a very feature-reduced version of [Marmoset](http://marmoset.cs.umd.edu/index.shtml).  Pygmy Marmoset has no provision for automated testing or code reviews, so if you need those, use Marmoset!

I had several motivations for creating Pygmy Marmoset:

* *Smaller and more extensible code base*.  The Marmoset code base has a lot of the usual problems associated with being a research prototype.
* *Ease of setup and deployment*.  Marmoset requires an application server to run.  Pygmy Marmoset is deployable as a single jar file, much the same as [CloudCoder](https://cloudcoder.org).
* *Improved UI*.  The feature reduction comes will a significant simplification of the user interface.  Some aspects of the UI are improved: for example, [syntaxhighlighter.js](https://github.com/syntaxhighlighter/syntaxhighlighter) is used for highlighting submitted source code.
* *Bug fixes*.  In particular, Marmoset has issues allowing students to check their submissions: sometimes source files don't appear in the file listing for the submission.

## Current status

Pygmy Marmoset is ready for production!  We are using it for computer science courses at [York College](https://www.ycp.edu) effective Fall 2017.

## Trying it out

### Development

You will need Eclipse and JDK 1.8 (or higher).

You can use the following steps to try out Pygmy Marmoset in a development configuration:

1. Run the `fetchdeps.pl` script to fetch dependencies.
2. Import all of the projects in the repository into Eclipse.
3. Create a MariaDB database called `pygmymarmoset` using the MariaDB `root` account.
4. Make sure it is possible to log into the MariaDB `root` account using the password `root`.
5. Run the `CreateDatabase` program in the `PygmyMarmosetPersistence` project.  This will create the database tables and create an initial user account with superuser privileges.
6. Run the `Main` program in the `PygmyMarmosetLauncher` project.
7. Connect to <code>http://localhost:8080/marmoset</code> using a web browser.
8. Log in.

### Production

You will need JDK 1.8.

You can use the following steps to set up a production instance of Pygmy Marmoset:

**1**. Create a MariaDB database, and configure a user account that has `ALL` privileges on the database.  Make sure this user can authenticate via password.

**2**. In the top-level `PygmyMarmoset` directory (after cloning the repo), create a file called `pygmymarmoset.properties` with the following properties:

* `pm.db.host`: the hostname of the database server
* `pm.db.user`: the MariaDB username that the webapp will use to connect to the database
* `pm.db.passwd`: the password associated with the MariaDB username
* `pm.db.name`: the name of the MariaDB database

Here is an example configuration:

```
pm.db.host=localhost
pm.db.user=ingo
pm.db.passwd=nicekitty123
pm.db.name=pygmymarmosetdb
```

This example configuration assumes that the MariaDB server is on the same server as the Pygmy Marmoset webapp (hence `localhost` as the value of `pm.db.host`).

**3**. From the top-level PygmyMarmoset directory, run the command

```
./fetchdeps.pl
```

This will fetch all of the libraries needed by Pygmy Marmoset.

**4**. From the top-level PygmyMarmoset directory, run the command

```bash
gradle uberJar
```

This assumes that you have [Gradle](https://gradle.org/) installed.  After a successful build, the file `build/pygmyMarmosetApp.jar` is the executable webapp jar file.

**5**. Deploy the executable jar file by copying it to your server, then log into the server.

**6**. On the server, run the command

```bash
java -jar pygmyMarmosetApp.jar createdb
```

to create the database tables and initial user account.

**7**. Start the webapp by running the command

```bash
java -jar pygmyMarmosetApp.jar start
```

to start the webapp.  The webapp will listen for connections on `localhost:8080`.  You will almost certainly want to set up Apache 2 or nginx to act as a reverse proxy (e.g., to allow for connections via https).  For example, I use the following in my Apache 2 configuration:

```
# Transparently proxy requests for /marmoset to Pygmy Marmoset
ProxyPass /marmoset http://localhost:8080/marmoset
ProxyPassReverse /marmoset http://localhost:8080/marmoset
<Proxy http://localhost:8080/marmoset>
    Order Allow,Deny
    Allow from all
</Proxy>
```

You can shut down the webapp with the command

```bash
java -jar pygmyMarmosetApp.jar shutdown
```

## License

The code is distributed under the terms of the [AGPL v3](https://www.gnu.org/licenses/agpl-3.0.en.html).  See [LICENSE.txt](LICENSE.txt).

## Contact

Email <david.hovemeyer@gmail.com> with feedback.
