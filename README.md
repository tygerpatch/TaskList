1. Run Gradle's eclipse task
2. Import project into Eclipse
3. Use Google App Engine
  * Right click on project
  * Select Properties > Google > App Engine
    * Put a check mark on "Use Google App Engine"
  * Select Google > Web Application
    * Put a check mark on "This project has a WAR directory"
    * Put a check mark on "Launch and deploy from this directory"
  * Click OK
4. Configure Datastore
  * Right click on project
  * Select Properties > Google > App Engine
    * Select "Enable local HRD support"
    * Select v2 for "Datanucleus JDO/JPA version"
  * Click OK
5. Configure Build Path
  * Right click on project
  * Select Build Path > Configure Build Path
  * Select the "Order and Export" tab
  * Put a check mark on "App Engine SDK [App Engine - 1.9.9]"
  * Move "App Engine SDK [App Engine - 1.9.9]" above "Web App Libraries"