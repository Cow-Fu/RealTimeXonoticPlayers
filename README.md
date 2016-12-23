# Overview
This is a simple command line application used to fetch real time information about activities on public Xonotic servers.
## Build
After cloning the project, simply run `gradle jar`
## Run
To run, simply run with `java -jar build/libs/XonServers-0.1.jar`, and replacing the path with the desired location. Without arguments, it will automatically list all currently active Xonotic servers. If an IP address for a server is given, then various information regarding the matches information will be displayed.

For ease of use, adding a simple bash script with the following should do the trick: `java -jar /path/to/jar "$@"`
