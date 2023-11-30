Class dc.upload.UploadResponse Extends (%Persistent, %JSON.Adaptor, %Populate)
{

Property releaseUrl As %String;

Storage Default
{
<Data name="UploadResponseDefaultData">
<Value name="1">
<Value>%%CLASSNAME</Value>
</Value>
<Value name="2">
<Value>FileName</Value>
</Value>
<Value name="3">
<Value>FilePath</Value>
</Value>
<Value name="4">
<Value>releaseURL</Value>
</Value>
<Value name="5">
<Value>releaseUrl</Value>
</Value>
</Data>
<DataLocation>^dc.upload.UploadResponseD</DataLocation>
<DefaultData>UploadResponseDefaultData</DefaultData>
<IdLocation>^dc.upload.UploadResponseD</IdLocation>
<IndexLocation>^dc.upload.UploadResponseI</IndexLocation>
<StreamLocation>^dc.upload.UploadResponseS</StreamLocation>
<Type>%Storage.Persistent</Type>
}

}
