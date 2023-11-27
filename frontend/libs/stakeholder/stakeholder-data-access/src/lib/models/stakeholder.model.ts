import { VersionStatus } from "./version-status.model";

export interface Stakeholder {
    id:number;
    name:String;
    stakeholderRole:String;
    email:String;
    versionStatuses:VersionStatus[];
}