Scott Logic Hackathon 2015
==========================

To get started with the Scott Logic Hackathon:

* Download source as a zip and extract.
* Open [Eclipse](https://www.eclipse.org/downloads/) and right-click the Package Explorer
* Import -> General -> Existing Projects into Workspace
* Browse to unzipped project, select both "Hackathon" and "hackathon-webapp".
* Make sure you are not copying project to workspace, and click Finish.
* Right click Hackathon project -> Run As -> Java Application.
* Fill in the Hackathon/src/trading/TradingStrategy to do trading

The "hackathon-webapp" project is an optional extra which visualises the profits through the year. It uses the same TradingStrategy as in the command line version.
To run, you will need a local [Tomcat Webserver](http://tomcat.apache.org/index.html).

* Right click "hackathon-webapp" project -> Run as -> Run on Server
* If not already configured, add the Tomcat server as a resource
* Open up http://localhost:8080/hackathon.
* If the TradingStrategy is changed, the hackathon-webapp will need to be cleaned and redeployed to see new results.
