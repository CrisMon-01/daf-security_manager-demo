## Installation

### Prerequisites

Superset and Ckan must be properly installed and configured before proceed with secutity manager installation

### Procedure

The installation depends on the ***REMOVED***ironment where is is run.
For this reason, when executing the following steps, replace \<***REMOVED***ironment\> with `test` or `prod` accordingly.

1. git clone ***REMOVED***://github.com/***REMOVED***/***REMOVED***
2. cd `security-manager`
3. `sbt` 
4. `eval System.setProperty("STAGING", "true")` If you want to deploy on test ***REMOVED***ironment
5. `docker:publish` to compile and push the docker image on Nexus
6. If not already present, create kerberos config map (refer to ***REMOVED***-operational repo: ***REMOVED***://github.com/***REMOVED***/***REMOVED***-operational.git)
7. cd `kubernetes` 
8. `./config-map-<***REMOVED***ironment>.sh` to create config map
9. `./kubectl create -f ***REMOVED***_security_manager_<***REMOVED***ironment>.yaml` to deploy the containers in kubernetes
10. Setup user and groups and test installation accordingly to this guide: ***REMOVED***://github.com/italia/***REMOVED***/blob/master/infrastructure/pages/security.md
