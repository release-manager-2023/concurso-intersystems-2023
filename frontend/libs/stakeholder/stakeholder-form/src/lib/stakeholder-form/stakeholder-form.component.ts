import { ChangeDetectionStrategy, Component, inject } from '@angular/core';
import { Validators } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { Store } from '@ngrx/store';
import { DynamicFormComponent, Field, formsActions, ListErrorsComponent, ngrxFormsQuery } from '@realworld/core/forms/src';

const structure: Field[] = [
  {
    type: 'INPUT',
    name: 'name',
    placeholder: 'Name',
    validator: [Validators.required],
  },
  {
    type: 'INPUT',
    name: 'email',
    placeholder: 'Email',
    validator: [Validators.required],
  },
  {
    type: 'INPUT',
    name: 'stakeholderRole',
    placeholder: 'Role',
    validator: [Validators.required],
  },
];

@Component({
  selector: 'lib-stakeholder-form',
  standalone: true,
  templateUrl: './stakeholder-form.component.html',
  styleUrls: ['./stakeholder-form.component.css'],
  imports: [ListErrorsComponent, DynamicFormComponent, RouterModule],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class StakeholderFormComponent {
  private readonly store = inject(Store);

  structure$ = this.store.select(ngrxFormsQuery.selectStructure);
  data$ = this.store.select(ngrxFormsQuery.selectData);

  ngOnInit() {
    this.store.dispatch(formsActions.setStructure({ structure }));
  }

  updateForm(changes: any) {
    this.store.dispatch(formsActions.updateData({ data: changes }));
  }

  submit() {
    console.log(this.data$)
    // this.store.dispatch(stakeholderActions.register());
  }

  ngOnDestroy() {
    this.store.dispatch(formsActions.initializeForm());
  }
}
