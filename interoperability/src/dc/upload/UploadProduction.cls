Class dc.upload.UploadProduction Extends Ens.Production
{

XData ProductionDefinition
{
<Production Name="dc.upload.UploadProduction" LogGeneralTraceEvents="false">
  <Description></Description>
  <ActorPoolSize>2</ActorPoolSize>
  <Item Name="BS.Release" Category="" ClassName="dc.upload.UploadService" PoolSize="1" Enabled="true" Foreground="false" Comment="" LogTraceEvents="true" Schedule="">
  <Setting Target="Host" Name="Folder">/var/tmp/</Setting>
  </Item>
  <Item Name="BP.Upload" Category="" ClassName="dc.upload.UploadBP" PoolSize="1" Enabled="true" Foreground="false" Comment="" LogTraceEvents="true" Schedule="">
  </Item>
  <Item Name="BO.CloudStorage" Category="" ClassName="EnsLib.CloudStorage.BusinessOperation" PoolSize="1" Enabled="true" Foreground="false" Comment="" LogTraceEvents="true" Schedule="">
    <Setting Target="Adapter" Name="StorageProvider">1</Setting>
    <Setting Target="Adapter" Name="EndPoint">https://releasemanagerblob.blob.core.windows.net</Setting>
    <Setting Target="Adapter" Name="ProviderCredentialsFile">/usr/irissys/mgr/cloudstoragecredential</Setting>
    <Setting Target="Host" Name="BucketName">release</Setting>
  </Item>

</Production>
}

}
