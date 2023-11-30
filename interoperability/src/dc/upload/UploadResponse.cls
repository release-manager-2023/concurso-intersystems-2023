Class dc.upload.UploadResponse Extends (%Persistent, %JSON.Adaptor, %Populate)
{

Property FileName As %String;

Property FilePath As %String;

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
</Data>
<DataLocation>^dc.upload.UploadResponseD</DataLocation>
<DefaultData>UploadResponseDefaultData</DefaultData>
<IdLocation>^dc.upload.UploadResponseD</IdLocation>
<IndexLocation>^dc.upload.UploadResponseI</IndexLocation>
<StreamLocation>^dc.upload.UploadResponseS</StreamLocation>
<Type>%Storage.Persistent</Type>
}

}
