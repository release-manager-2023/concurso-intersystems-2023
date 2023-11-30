Class dc.upload.UploadBP Extends Ens.BusinessProcessBPL
{

Storage Default
{
<Type>%Storage.Persistent</Type>
}

/// BPL Definition
XData BPL [ XMLNamespace = "http://www.intersystems.com/bpl" ]
{
<process language='objectscript' request='dc.upload.UploadResponse' response='EnsLib.CloudStorage.UploadRequest' height='2000' width='2000' >
<sequence xend='200' yend='450' >
<code name='UploadFileProcess' xpos='200' ypos='250' >
<annotation><![CDATA[Upload File Process]]></annotation>
<![CDATA[ 
  $$$LOGINFO("file: "_request)
  Set response.BlobName = request.FileName
  

  // Open the file for reading
  Set file = ##class(%File).%New(request.FilePath)
  If file.Open("R") {
      // Create a global binary stream
      Set globalStream = ##class(%GlobalBinaryStream).%New()

      // Read the entire content of the file into a variable
      Set content = file.Read(file.Size)
      If content'="" {
          // Write the file content into the global binary stream
          Do globalStream.Write(content)
      }

      // Close the file
      Do file.Close()

      Set response.Content = globalStream

      // At this point, the file content is in the global binary stream (globalStream)
      // You can use globalStream as needed (e.g., store it in a global node)
  } else {
      $$$LOGINFO("Erro: File could not be opened.")
  }

]]>
</code>
<call name='UploadFileCloudStorage' target='CloudStorage' async='0' xpos='200' ypos='350' >
<annotation><![CDATA[Upload file to cloud storage]]></annotation>
<request type='EnsLib.CloudStorage.UploadRequest' >
<assign property="callrequest" value="response" action="set" />
</request>
</call>
</sequence>
</process>
}

}
