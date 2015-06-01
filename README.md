# RabbitMQ Java 

This is a simple Servlet-based Java webapp that uses RabbitMQ. 

New users should check out the resources available at [HP Helion Docs](http://docs.hpcloud.com/helion/devplatform/workbook/messaging/java/). 
The site includes more detail and has instructions on how to create an HP
Helion Development Platform Application Lifecycle Services Cluster.

## Prerequisites
- If you do not have an HP Helion Development Platform Application Lifecycle 
  Services Cluster available, please create one before continuing. You will also
  need to install the Helion CLI, which can be installed from the cluster's
  Management Console. Please refer to [HP Helion Docs](http://docs.hpcloud.com/helion/devplatform/workbook/messaging/java/)
  for further details.  
- Make sure that the RabbitMQ service is enabled. It is not enabled by default. 
  You can take the following steps to enable it:
    - Go to the Management Console (e.g. https://api.example.com)
    - Admin --> Cluster --> Settings (gear icon on right corner) --> Check off 
      Rabbit3 --> Save

## Building the Application

The specific commands listed below are intended for Unix-like machines.

Build the application with Maven. Maven requires you to have the Java Development
Kit (JDK) installed. The JDK can be installed with the following command:

    sudo apt-get install default-jdk

### Maven

Make sure you have [Maven](http://maven.apache.org/ "Maven") installed.
The simplest way to install Maven is:

    sudo apt-get install maven

Then, *cd* into this app's root directory (directory with manifest.yml) and execute:

    mvn clean package

This will create the .war file within the 'target' directory. The pom.xml file 
in the root directory is used by Maven to build the application.

## Debugging the Application Locally

Like all tomcat servlets, this application can be debugged in Eclipse. 
Requirements: 
1. a valid RabbitMQ instance, accessible via amqp://{user}:{password}@{host}:{port}/%2f URI
2. a Tomcat server configured in Eclipse: see https://www.mulesoft.com/tcat/tomcat-wtp

You can 'Run As' or 'Debug As' a tomcat server from the 'Run' menu option, assuming you have eclipse WTP installed.
When you run or debug from Tomcat, be sure to set the RABBIT_MQ environment variable to point at the instance of RabbitMQ you are using. 
   
## Deploy the Application to Helion Development Platform

Execute the following commands:

- Open the terminal
- If you are not already there, *cd* to the root directory of the sample. The 
  root directory contains the manifest.yml file which helps automate deployment. 
- If you have not logged in to your target environment yet, execute the following:

    `helion target https://api.example.com`
    
    `helion login`
    
    Enter your Management Console credentials
    
    `helion push`

    Hit enter to accept any default values that you may be prompted for. 
    Note: By default, ALS clusters are configured with two domains (private and
    public). In some situations, the Helion CLI may prompt you to select a target
    domain. If prompted, select the public domain from the given list (e.g. https://api.example.com)

## View and run the app
- Go to the management console (e.g. https://api.example.com)
- Check the applications link to see a list of your apps.
- Click on the name of the app you just deployed. The app name is specified in 
  manifest.yml.
- Click "View App" to see your app in action.

The result when visiting the application page and clicking 'View App' should be
a page that will accept a user's message.
