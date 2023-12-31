Class dc.upload.UploadService Extends Ens.BusinessService
{

// extends Ens.BusinessService to create a custom Business service using Object Script

// This class receive a file from a multipart http request and save to the folder configured into folder parameter

// choose an adapter to get data from a source of data

// HTTP.InboundAdapter allows you get data from an http request

Parameter ADAPTER = "EnsLib.HTTP.InboundAdapter";

// custom parameter to allows production user set destination folder to multipart file uploaded 

Property Folder As %String(MAXLEN = 100);

// when you set parameter Folder to SETTINGS parameter, the production IRIS interface create a field to the user fills

// so the user will inform host path for the uploaded file 

Parameter SETTINGS = "Folder,Basic";

// This method is mandatory to have a business service. It receives the multipart file into pInput 

// and returns a result to the caller using pOutput

Method OnProcessInput(pInput As %GlobalBinaryStream, pOutput As dc.upload.UploadResponse) As %Status
{
    //try to do the actions
    try {
        Set reader = ##class(%Net.MIMEReader).%New() //creates a MIMEReader to extract files from multipart requests 
        Do reader.OpenStream(pInput) //reader open the file

        Set tSC = reader.ReadMIMEMessage(.message) //the reader put the file uploaded into a MIME Message
        //Get Header obtains headers from the request and the multipart file, like content-type or content disposition
        //the content disposition have 3 headers: Content-Disposition: form-data; name="file"; filename="filename.ext"
        //This split content-disposition header into 3 parts
        Set filenameHeader = $PIECE(message.GetHeader("CONTENT-DISPOSITION", .header),";",3) 
        //get filename header value
        Set filename = $EXTRACT(filenameHeader, 12, $LENGTH(filenameHeader)-1)
        //Headers are not more needed. It clean the header to remains only the file content to be saved
        Do message.ClearHeaders()

        //create a file object to save the multipart file
        Set file=##class(%Stream.FileBinary).%New()
        //points the file to folder informed into folder parameter, plus upload filename from header
        Set file.Filename=..Folder_filename 
        //save body message (the file content) to file object
        Do file.CopyFromAndSave(message.Body)

        Set bucketName = "release/1/2"

        Set adapter=##class(EnsLib.CloudStorage.OutboundAdapter).%New()
        Set adapter.StorageProvider = 1
        Set adapter.EndPoint = "https://releasemanagerblob.blob.core.windows.net"
        Set adapter.ProviderCredentialsFile = "/usr/irissys/mgr/cloudstoragecredential"
        Do adapter.OnInit()
        Set status = adapter.UploadBlobFromFile(bucketName,filename,..Folder_filename)

        Set uploadResponse=##class(dc.upload.UploadResponse).%New()
        Set uploadResponse.releaseUrl = adapter.EndPoint_"/"_bucketName_"/"_filename

        // return a sucess message to the user/caller
        // Set pOutput = "File "_filename_" uploaded with success to: "_..Folder_filename_" status:"_pProductId
        Do uploadResponse.%JSONExportToString(.pOutput)
        Set tSC=$$$OK
    
    //returns error message to the user
    } catch e {
        Set tSC=e.AsStatus()
        Set pOutput = tSC
    }

    Quit tSC
}

}
