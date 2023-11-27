import { VersionStatus } from "./version-status.model";

export interface Stakeholder {
    id:number;
    name:String;
    stakeholder_role:String;
    email:String;
    versionStatuses:VersionStatus[];
  }