import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ILettrage, Lettrage } from 'app/shared/model/Finance/lettrage.model';
import { LettrageService } from './lettrage.service';
import { IFacture } from 'app/shared/model/Finance/facture.model';
import { FactureService } from 'app/entities/Finance/facture/facture.service';
import { IPaiement } from 'app/shared/model/Finance/paiement.model';
import { PaiementService } from 'app/entities/Finance/paiement/paiement.service';

type SelectableEntity = IFacture | IPaiement;

@Component({
  selector: 'jhi-lettrage-update',
  templateUrl: './lettrage-update.component.html',
})
export class LettrageUpdateComponent implements OnInit {
  isSaving = false;
  factures: IFacture[] = [];
  paiements: IPaiement[] = [];

  editForm = this.fb.group({
    id: [],
    dateLettrage: [null, [Validators.required]],
    montantLettre: [null, [Validators.required, Validators.min(0)]],
    factureId: [],
    paiementId: [],
  });

  constructor(
    protected lettrageService: LettrageService,
    protected factureService: FactureService,
    protected paiementService: PaiementService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ lettrage }) => {
      if (!lettrage.id) {
        const today = moment().startOf('day');
        lettrage.dateLettrage = today;
      }

      this.updateForm(lettrage);

      this.factureService.query().subscribe((res: HttpResponse<IFacture[]>) => (this.factures = res.body || []));

      this.paiementService.query().subscribe((res: HttpResponse<IPaiement[]>) => (this.paiements = res.body || []));
    });
  }

  updateForm(lettrage: ILettrage): void {
    this.editForm.patchValue({
      id: lettrage.id,
      dateLettrage: lettrage.dateLettrage ? lettrage.dateLettrage.format(DATE_TIME_FORMAT) : null,
      montantLettre: lettrage.montantLettre,
      factureId: lettrage.factureId,
      paiementId: lettrage.paiementId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const lettrage = this.createFromForm();
    if (lettrage.id !== undefined) {
      this.subscribeToSaveResponse(this.lettrageService.update(lettrage));
    } else {
      this.subscribeToSaveResponse(this.lettrageService.create(lettrage));
    }
  }

  private createFromForm(): ILettrage {
    return {
      ...new Lettrage(),
      id: this.editForm.get(['id'])!.value,
      dateLettrage: this.editForm.get(['dateLettrage'])!.value
        ? moment(this.editForm.get(['dateLettrage'])!.value, DATE_TIME_FORMAT)
        : undefined,
      montantLettre: this.editForm.get(['montantLettre'])!.value,
      factureId: this.editForm.get(['factureId'])!.value,
      paiementId: this.editForm.get(['paiementId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILettrage>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
