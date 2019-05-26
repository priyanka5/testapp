Application uses Spring Boot RESTful web application, to show image at endpoint /homersimpson and current time in Covilha at endpoint /covilha
Application also has inbuilt prometheus exporter to show the number of count for request made on both endpoints.
# Application endpoint and inbuilt prometheus are accessible on endpoints after depolying on kubernetes GCP Cluster

http://35.232.118.126/covilha
http://35.232.118.126/homersimpson
http://35.232.118.126/prometheus


Below are the steps followed - 

Its an maven app, to build the jar run "mvn clean install"

App is deployed on Kubernetes cluster running on Google Cloud Platform. Below are the steps followed one jar file is generated.

Create a Docker Image using the attached Dockerfile by following commands in directory where Dockerfile is located - 

# build docker image
docker build -y testappimage .


# Below are required only if docker image has to be pushed on registry ,otherwise local docker image can also be used for deploying application
# tag docker image
docker tag testappimage priyanka/testappimage

# login to docker hub 
docker login 

# Push image to dockerhub
docker push priyanka/testappimage


# Once dockerimage is pushed , Connect to kubernetes cluster through CLI and run command - 

kubectl run testapp --image=priyanka/testappimage

kubectl expose deployment testapp --port=80 --target-port=8080 --type=LoadBalancer

# Get the app IP 
kubectl get svc  
#below is the output from above command

yashugupta29@cloudshell:~ (supple-walker-241714)$ kubectl get svc
NAME         TYPE           CLUSTER-IP      EXTERNAL-IP      PORT(S)        AGE
kubernetes   ClusterIP      10.11.240.1     <none>           443/TCP        13h
testapp      LoadBalancer   10.11.250.213   35.232.118.126   80:31975/TCP   13h

# Application endpoint and inbuilt prometheus is now Accessible on 

http://35.232.118.126/covilha
http://35.232.118.126/homersimpson
http://35.232.118.126/prometheus




