import { VersionStatus } from "./version-status";

export interface Stakeholder {
    id:BigInteger;
    name:String;
    stakeholder_role:String;
    email:String;
    versionStatuses:VersionStatus[];
  }