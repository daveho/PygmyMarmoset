# Pygmy Marmoset: an assignment submission webapp for CS courses

![Pygmy marmoset photo](PygmyMarmosetWebapp/war/img/pygmyMarmoset-crop.jpg)

Pygmy Marmoset is a very simple assignment submission web application for CS courses.

It is designed as a very feature-reduced version of [Marmoset](http://marmoset.cs.umd.edu/index.shtml).  Pygmy Marmoset has no provision for automated testing or code reviews, so if you need those, use Marmoset!

I had several motivations for creating Pygmy Marmoset:

* *Smaller and more extensible code base*.  The Marmoset code base has a lot of the usual problems associated with being a research prototype.
* *Ease of setup and deployment*.  Marmoset requires an application server to run.  Eventually Pygmy Marmoset will be deployable as a single jar file, much the same as [CloudCoder](https://cloudcoder.org).
* *Improved UI*.  The feature reduction comes will a significant simplification of the user interface.  Some aspects of the UI are improved: for example, [syntaxhighlighter.js](https://github.com/syntaxhighlighter/syntaxhighlighter) is used for highlighting submitted source code.
* *Bug fixes*.  In particular, Marmoset has issues allowing students to check their submissions: sometimes source files don't appear in the file listing for the submission.

## Current status

Pygmy Marmoset is fairly close to being useable.  It will be piloted in two courses at [York College](https://www.ycp.edu) in Summer 2017.

You probably don't want to try using it in production at this point.  My plan is to have it ready for production deployment by August, 2017.

## Trying it out

You will need Eclipse and JDK 1.8 (or higher).

Steps:

1. Run the `fetchdeps.pl` script to fetch dependencies.
2. Import all of the projects in the repository into Eclipse.
3. Create a MariaDB database called `pygmymarmoset` using the MariaDB `root` account.
4. Make sure it is possible to log into the MariaDB `root` account using the password `root`.
5. Run the `CreateDatabase` program in the `PygmyMarmosetPersistence` project.  This will create the database tables and create an initial user account with superuser privileges.
6. Run the `Main` program in the `PygmyMarmosetLauncher` project.
7. Connect to <code>http://localhost:8080/marmoset</code> using a web browser.
8. Log in.

## License

The code is distributed under the terms of the [AGPL v3](https://www.gnu.org/licenses/agpl-3.0.en.html).  See [LICENSE.txt](LICENSE.txt).

## Contact

Email <david.hovemeyer@gmail.com> with feedback.
