## InterSystems IRIS Interoperability (Cloud Storage)
This is an example of interoperability that performs file transfers to a cloud storage service.

## What this example does

This application receive a http multipart request with a file and saves to Azure Blob 

## Prerequisites
1. Make sure you have [Docker desktop](https://www.docker.com/products/docker-desktop) installed.

2. It will also be necessary to have a cloud storage account compatible with IRIS Interoperability, such as Amazon Web Services (AWS), Azure Blob Storage (Azure), or Google Cloud Platform (GCP).
<img src="./docs/assets/azure_blob_conf.png" alt="azure">

## Installation: Docker

1. First, modify the file [cloudstoragecredential](./cloudstoragecredential) with the credentials of the cloud storage service, for example using Azure Blob:
```
DefaultEndpointsProtocol=https
AccountName=YOUR_ACCOUNT_NAME
AccountKey=YOUR_ACCOUNT_KEY
EndpointSuffix=core.windows.net
```

2. Open the terminal in this directory and run:

```
$ docker-compose build
```

3. Run the IRIS container with your project:

```
$ docker-compose up -d
```

## How to Run the Sample

1. Open the [production](http://localhost:52773/csp/irisapp/EnsPortal.ProductionConfig.zen?PRODUCTION=dc.upload.UploadProduction) in the IRIS Administration Portal (login: SuperUser and password: SYS).
<img src="./docs/assets/login_iris.png" alt="login_iris">
 
2. Start the production.
<img src="./docs/assets/production_start.png" alt="production_start">
<img src="./docs/assets/Interoperability.png" alt="Interoperability">

3. Check the cloud storage configuration in the business operation and ensure that the credentials file is properly selected.
<img src="./docs/assets/cloud_storage_conf.png" alt="cloud storage conf">


4. Now Open Postman and create a multipart request into a form-data pointing to localhost:9980/ using verb POST. See sample:
<img src="./docs/assets/postman_request.png" alt="postman">

5. After executing the request, verify if the file has been correctly sent to the previously registered Azure Blob.
<img src="./docs/assets/azure_blob.png" alt="azure">