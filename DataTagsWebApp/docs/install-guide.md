# Install Guide
DataTags project has two main components:
    * *DataTagsLib* - Language kit, complete with parsers, compilers, and a runtime engine. This is a java project, created in NetBeans.
    * *DataTagsWebApp* - a Play Framework web application, that uses the DataTagsLib, among other things.
To work on DataTagsLib, all you need to do is clone the repo and open it with NetBeans.

To work on the web application, you'll need:

* typesafe activator
* scala
* sbt

The best way to get all of them, is to install them via a package manager. On a Mac, that boils down to going to [homebrew](http://brew.sh), installing it, and, opening a terminal and typing:
    
    $ brew install scala
    $ brew install typesafe-activator

Play, and this DataTagsWebApp, also use [Less](http://lesscss.org), to make the creation of css less painful. While Play has a built-in support for this, you install a version that would allow you to experiment with it outside of Play. To do that, install *npm* (the node.js package management system)

    $ brew install npm

And then install Less

    $ npm install -g less

To test your installation, open a terminal application. Navigate to the `datatags-app` folder, and type

    $ activator start

After a while (might be a long while, depends on how many items sbt has to download from the internet), you should be able to view your local version at http://localhost:9000

## Troubleshooting
* In case of a currpt installation, e.g. when any of the scala commands fail over a missing dependency (such as `scala.tools.nsc.Global`) try deleting the caches of maven, ivy and sbt:
    - `~/.m2`
    - `~/.ivy2`
    - `~/.sbt`

On the terminal, this would mean:

    rm -rf ~/.m2
    rm -rf ~/.ivy2
    rm -rf ~/.sbt


## Deploy to test server

1. Prepare you local copy. At the `datatags-app` folder, type `activator clean stage`.
1. Ssh into `dvnweb-vm1.hmdc.harvard.edu`. Cd into `datatags-apps`
2. Make a new directory (preferably named after the date)
3. scp `target` and `public` folders from the local `datatag-app` to the new folder created on the server
4. On the server, type `./stop-current.sh`
5. relink current to the new application folder (`ln -s [new folder name goes here] current`)
6. start the new application by typing `./start-current.sh`
7. Test you changes at http://www.datatags.org
8. logout using `ctrl-D`
9. Test you changes at http://www.datatags.org again, just to make sure the logout did not close the server process.
