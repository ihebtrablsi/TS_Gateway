import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IRistourne, Ristourne } from 'app/shared/model/Finance/ristourne.model';
import { RistourneService } from './ristourne.service';

@Component({
  selector: 'jhi-ristourne-update',
  templateUrl: './ristourne-update.component.html',
})
export class RistourneUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    montant: [null, [Validators.required, Validators.min(0)]],
    dateAttribution: [null, [Validators.required]],
  });

  constructor(protected ristourneService: RistourneService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ristourne }) => {
      if (!ristourne.id) {
        const today = moment().startOf('day');
        ristourne.dateAttribution = today;
      }

      this.updateForm(ristourne);
    });
  }

  updateForm(ristourne: IRistourne): void {
    this.editForm.patchValue({
      id: ristourne.id,
      montant: ristourne.montant,
      dateAttribution: ristourne.dateAttribution ? ristourne.dateAttribution.format(DATE_TIME_FORMAT) : null,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const ristourne = this.createFromForm();
    if (ristourne.id !== undefined) {
      this.subscribeToSaveResponse(this.ristourneService.update(ristourne));
    } else {
      this.subscribeToSaveResponse(this.ristourneService.create(ristourne));
    }
  }

  private createFromForm(): IRistourne {
    return {
      ...new Ristourne(),
      id: this.editForm.get(['id'])!.value,
      montant: this.editForm.get(['montant'])!.value,
      dateAttribution: this.editForm.get(['dateAttribution'])!.value
        ? moment(this.editForm.get(['dateAttribution'])!.value, DATE_TIME_FORMAT)
        : undefined,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRistourne>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
