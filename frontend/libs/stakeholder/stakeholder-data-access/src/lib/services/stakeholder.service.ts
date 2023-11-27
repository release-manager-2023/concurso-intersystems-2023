import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiService } from '@realworld/core/http-client';
import { Stakeholder } from '../models/stakeholder.model';

@Injectable({ providedIn: 'root' })
export class StakeholderService {
  private readonly apiService = inject(ApiService);

  listStakeholders(): Observable<Stakeholder[]> {
    return this.apiService.get<Stakeholder[]>('/stakeholder');
  }
}
