In the IRIS administration portal, import the production file [Export-concurso_ReleaseManagerProduction-20231129102647.xml](./src/Export-concurso_ReleaseManagerProduction-20231129102647.xml)

Edit the [provider credentials file](./conf/azureblob) then move to the IRIS docker container

```shell
docker cp azureblob iris:/usr/irissys/mgr
```
