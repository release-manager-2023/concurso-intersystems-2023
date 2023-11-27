import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Stakeholder, StakeholderService, VersionStatus } from '@realworld/stakeholder/stakeholder-data-access/src';

@Component({
  selector: 'lib-stakeholder-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './stakeholder-list.component.html',
  styleUrls: ['./stakeholder-list.component.css'],
})
export class StakeholderListComponent {

  stakeholders: Stakeholder[] = [{
    name: "Cesar",
    email: "cesarcruz.ti@gmail.com",
    stakeholder_role: "Administrador de sistemas",
    versionStatuses: [{ id: 1, name: "Canary" }, { id: 2, name: "Internal" }],
    id: 1
  }];

  constructor(private service:StakeholderService){

  }

  ngOnInit() {
    this.service.listStakeholders().subscribe(list=>{
      this.stakeholders = list;
    })
  }

  versionStatus(versionStatuses:VersionStatus[]){
    return versionStatuses.map(objeto => objeto.name).join(', ');
  }
}
