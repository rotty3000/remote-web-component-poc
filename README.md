## Remote Web Components In Liferay (POC)

Adding remote web components in Liferay.

#### Building

To setup the Liferay DXP bundle do:

```bash
./gradlew initBundle
```

To build all the project modules do:

```
./gradlew deploy
```

To run Liferay do:

```
bundles/tomcat-9.0.33/bin/catalina.sh [jpda] run
```
*** add `jpda` if you want to enable java debugging on standard port (8000)
