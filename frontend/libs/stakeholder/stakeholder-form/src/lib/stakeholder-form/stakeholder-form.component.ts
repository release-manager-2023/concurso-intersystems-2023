import { CommonModule } from '@angular/common';
import { ChangeDetectionStrategy, Component, inject } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
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
  imports: [ListErrorsComponent, RouterModule, CommonModule, ReactiveFormsModule],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class StakeholderFormComponent {

  registerForm !: FormGroup
  title = 'formReractive';
  submitted = false;

  constructor(private formBuilder: FormBuilder) { }

  ngOnInit() {
    // Validation 
    this.registerForm = this.formBuilder.group({
      name: ['', Validators.required, Validators.minLength(8)],
      email: ['', [Validators.required, Validators.email]],
      role: ['', [Validators.required, Validators.minLength(3)]]
    })
  }

  onSubmit() {
    this.submitted = true

    if (this.registerForm.invalid) {
      return
    }
    alert('success')
  }


}
