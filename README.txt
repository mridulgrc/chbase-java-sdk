This project is derived from the HealthVault Java SDK (https://healthvaultjavalib.codeplex.com/)

This project comprises an SDK to access CHBase and some sample ui's
allowing a user to view and add weight measurements.  

Building:
    The build environment relies on Maven.  (http://maven.apache.org) 
    The UI and jaxb libraries both rely on the SDK.  

    Build from the top level pom.xml:
    > mvn install

    Javadoc:
    > mvn javadoc:javadoc

    Create Eclipse environment
    > mvn eclipse:eclipse

    Now import projects into Eclipse: File-->Import.
    In Project-->Properties-->Build Path, configure the M2_REPO variable to 
    point at your maven repository.  This is probably in 
    <user_home>/.m2/repository.  If you miss or skip this step, eclipse will 
    complain that it cannot find its dependencies.

Running the Sample:
    You can run the app directly from the command line with maven:
    > cd samle/jaxb-ui
    > mvn jetty:run
    Point your web browser at http://localhost:8080/jwildcat-ui

    You can run the web app directly from within Eclipse.  Select the UI 
    project, then Run As-->Java Application.  Otherwise, take the war produced 
    from your build above located at ui/target/jwildcat-ui.*.*-SNAPSHOT.war
    and install it in your servlet container. 

Private Key:
    CHBase uses public/private key infrastructure to verify the 
    application.  It does not validate the public key certificate chain so 
    there is no need to obtain keys from a trusted CA.  The CHBase java 
    sdk uses the JSSE keystore to obtain the application's private key.  The 
    keystore file is loaded from the classpath and is shipped with the name 
    "/keystore".  The following entries in hv-application configure its use:

    keystore.filename=/keystore
    keystore.keyname=java-wildcat
    keystore.password=password

    The jdk ships with a tool to create and manage keys within this store: keytool 
    (http://java.sun.com/javase/6/docs/technotes/tools/windows/keytool.html)

    The instructions for how to generate a public/private key pair are 
    described in the documentation for the 
    com.chbase.DefaultPrivateKeyStore class:

    keytool -genkeypair -keyalg RSA -keysize 1024 -keystore keystore -alias java-wildcat -validity 9999

    This creates a file named "keystore" if it doesn't already exist and the 
    generated keys are placed within.  The password for the keystore and the 
    key must be the same.  You may choose other values for the keystore name 
    and the key alias, but they must correspond to configuration values in 
    hv-application.properties.

    The public key certificate must then be exported from the key store and 
    sent to the partner team.  

    To export the key:

    keytool -export -alias java-wildcat -keystore keystore > my-pub.cer

Making SDK Requests:
    The application is responsible for marshaling and unmarshaling the <info> 
    section in each HV Request.  You can find detailed schemas for each 
    method here:  http://developer.chbase.com/method.
