import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Stakeholder, StakeholderService, VersionStatus } from '@realworld/stakeholder/stakeholder-data-access/src';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'lib-stakeholder-list',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './stakeholder-list.component.html',
  styleUrls: ['./stakeholder-list.component.css'],
})
export class StakeholderListComponent {

  stakeholders: Stakeholder[] = [];

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
