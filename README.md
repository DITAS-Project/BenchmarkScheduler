# DITAS - Benchmarkscheduler 
The Benchmarkscheduler is a service of the DITAS Framework to create and deploy benchmarks on the DITAS environment.
## Installing
There are three ways to install the Benchmarkscheduler
### docker-compose
For the docker-compose approach, you can use the provided docker-composefile to run the project.

start the project:
```
docker-compose up
```
this command starts the benchmarkscheduler and a mongo db container to handle the data.

### kubernetes deployment
The provided kubernetes deployment file starts the whole [tub-dummy-example](https://github.com/DITAS-Project/tub-dummy-example) plus the benchmarkscheduler. 
First you need to configure the delpoyment file to fit your specific cluster environment.

After your deployment file fits your cluster you should apply the config using:
```
kubectl apply -f kubernetes_deployment.yaml
```
If  not you first should setup your cluster and configure the deloyment file to fit your specific cluster.

### local using mvn
To build the project run:
```
mvn clean package -DskipTests
```
You need a mongo instance running at localhost:27017 for the project to run successfully or if you want to 
 use a mongo instance on another address you need to change the application-local.properties file in the ressource folder.


To run the project:
```
java -jar target/benchmarkscheduler-0.0.1-SNAPSHOT.jar -Dspring.profiles.active=local
```

## Usage

### API
#### benchmark-controller
`GET` `/benchmark/results?upper=DD-MM-YYYY&lower=DD-MM-YYYY`

Returns all benchmark for a given timespan, both upper and lower bound are required.

`GET` `/benchmark/results/csv?upper=DD-MM-YYYY&lower=DD-MM-YYYY`

Returns all benchmark for a given timespan in csv format, both upper and lower bound are required.

#### command-controller
`POST` `/command/create?bleprint_id=some_id`

Required Body
```
none
```
Returns a workload_id in the header which can be used to start a benchmark. 'blueprint_id' is a required field.

`POST` `/command/start`

Required Body

```
{
  "token": "optional",
  "vdc_id": "vdc0",
  "workload_id": "5cfa626a37df1044d2c0064f"
}
```
Starts a benchmark based on the given workload_id and the vdc_id. The token is optional but necessary is Access Control is used.

Returns an URL to the results in the header.

#### sampledata-controller
`GET` `/sd/all`

Returns all saved Sampledata.

#### workload-controller
`DELETE` `/wl`

Deletes all saved workloads.

`GET` `/wl/{workload_id}`

Returns the workload to the given id if present.

`GET` `/wl/all`

Returns all available workloads.

## License

This project is licensed under the Apache 2.0 - see the [LICENSE](LICENSE) file for details.

## Acknowledgments

This is being developed for the [DITAS Project](https://www.ditas-project.eu/)
