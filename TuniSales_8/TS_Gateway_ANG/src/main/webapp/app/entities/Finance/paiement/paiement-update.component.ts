import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IPaiement, Paiement } from 'app/shared/model/Finance/paiement.model';
import { PaiementService } from './paiement.service';
import { IFacture } from 'app/shared/model/Finance/facture.model';
import { FactureService } from 'app/entities/Finance/facture/facture.service';
import { ICompte } from 'app/shared/model/Finance/compte.model';
import { CompteService } from 'app/entities/Finance/compte/compte.service';

type SelectableEntity = IFacture | ICompte;

@Component({
  selector: 'jhi-paiement-update',
  templateUrl: './paiement-update.component.html',
})
export class PaiementUpdateComponent implements OnInit {
  isSaving = false;
  factures: IFacture[] = [];
  comptes: ICompte[] = [];

  editForm = this.fb.group({
    id: [],
    reference: [null, [Validators.required]],
    datePaiement: [null, [Validators.required]],
    montant: [null, [Validators.required, Validators.min(0)]],
    modePaiement: [null, [Validators.required]],
    statut: [null, [Validators.required]],
    factureId: [],
    compteId: [],
  });

  constructor(
    protected paiementService: PaiementService,
    protected factureService: FactureService,
    protected compteService: CompteService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ paiement }) => {
      if (!paiement.id) {
        const today = moment().startOf('day');
        paiement.datePaiement = today;
      }

      this.updateForm(paiement);

      this.factureService.query().subscribe((res: HttpResponse<IFacture[]>) => (this.factures = res.body || []));

      this.compteService.query().subscribe((res: HttpResponse<ICompte[]>) => (this.comptes = res.body || []));
    });
  }

  updateForm(paiement: IPaiement): void {
    this.editForm.patchValue({
      id: paiement.id,
      reference: paiement.reference,
      datePaiement: paiement.datePaiement ? paiement.datePaiement.format(DATE_TIME_FORMAT) : null,
      montant: paiement.montant,
      modePaiement: paiement.modePaiement,
      statut: paiement.statut,
      factureId: paiement.factureId,
      compteId: paiement.compteId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const paiement = this.createFromForm();
    if (paiement.id !== undefined) {
      this.subscribeToSaveResponse(this.paiementService.update(paiement));
    } else {
      this.subscribeToSaveResponse(this.paiementService.create(paiement));
    }
  }

  private createFromForm(): IPaiement {
    return {
      ...new Paiement(),
      id: this.editForm.get(['id'])!.value,
      reference: this.editForm.get(['reference'])!.value,
      datePaiement: this.editForm.get(['datePaiement'])!.value
        ? moment(this.editForm.get(['datePaiement'])!.value, DATE_TIME_FORMAT)
        : undefined,
      montant: this.editForm.get(['montant'])!.value,
      modePaiement: this.editForm.get(['modePaiement'])!.value,
      statut: this.editForm.get(['statut'])!.value,
      factureId: this.editForm.get(['factureId'])!.value,
      compteId: this.editForm.get(['compteId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPaiement>>): void {
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
