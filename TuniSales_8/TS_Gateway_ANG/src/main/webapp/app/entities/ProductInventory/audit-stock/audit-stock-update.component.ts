import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IAuditStock, AuditStock } from 'app/shared/model/ProductInventory/audit-stock.model';
import { AuditStockService } from './audit-stock.service';
import { IDepot } from 'app/shared/model/ProductInventory/depot.model';
import { DepotService } from 'app/entities/ProductInventory/depot/depot.service';

@Component({
  selector: 'jhi-audit-stock-update',
  templateUrl: './audit-stock-update.component.html',
})
export class AuditStockUpdateComponent implements OnInit {
  isSaving = false;
  depots: IDepot[] = [];

  editForm = this.fb.group({
    id: [],
    dateAudit: [null, [Validators.required]],
    statut: [null, [Validators.required]],
    commentaire: [],
    depotId: [],
  });

  constructor(
    protected auditStockService: AuditStockService,
    protected depotService: DepotService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ auditStock }) => {
      if (!auditStock.id) {
        const today = moment().startOf('day');
        auditStock.dateAudit = today;
      }

      this.updateForm(auditStock);

      this.depotService.query().subscribe((res: HttpResponse<IDepot[]>) => (this.depots = res.body || []));
    });
  }

  updateForm(auditStock: IAuditStock): void {
    this.editForm.patchValue({
      id: auditStock.id,
      dateAudit: auditStock.dateAudit ? auditStock.dateAudit.format(DATE_TIME_FORMAT) : null,
      statut: auditStock.statut,
      commentaire: auditStock.commentaire,
      depotId: auditStock.depotId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const auditStock = this.createFromForm();
    if (auditStock.id !== undefined) {
      this.subscribeToSaveResponse(this.auditStockService.update(auditStock));
    } else {
      this.subscribeToSaveResponse(this.auditStockService.create(auditStock));
    }
  }

  private createFromForm(): IAuditStock {
    return {
      ...new AuditStock(),
      id: this.editForm.get(['id'])!.value,
      dateAudit: this.editForm.get(['dateAudit'])!.value ? moment(this.editForm.get(['dateAudit'])!.value, DATE_TIME_FORMAT) : undefined,
      statut: this.editForm.get(['statut'])!.value,
      commentaire: this.editForm.get(['commentaire'])!.value,
      depotId: this.editForm.get(['depotId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAuditStock>>): void {
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

  trackById(index: number, item: IDepot): any {
    return item.id;
  }
}
