import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IEcartStock, EcartStock } from 'app/shared/model/ProductInventory/ecart-stock.model';
import { EcartStockService } from './ecart-stock.service';
import { IProduit } from 'app/shared/model/ProductInventory/produit.model';
import { ProduitService } from 'app/entities/ProductInventory/produit/produit.service';
import { IAuditStock } from 'app/shared/model/ProductInventory/audit-stock.model';
import { AuditStockService } from 'app/entities/ProductInventory/audit-stock/audit-stock.service';

type SelectableEntity = IProduit | IAuditStock;

@Component({
  selector: 'jhi-ecart-stock-update',
  templateUrl: './ecart-stock-update.component.html',
})
export class EcartStockUpdateComponent implements OnInit {
  isSaving = false;
  produits: IProduit[] = [];
  auditstocks: IAuditStock[] = [];

  editForm = this.fb.group({
    id: [],
    quantiteTheorique: [null, [Validators.required]],
    quantitePhysique: [null, [Validators.required]],
    ecart: [null, [Validators.required]],
    commentaire: [],
    produitId: [],
    auditId: [],
  });

  constructor(
    protected ecartStockService: EcartStockService,
    protected produitService: ProduitService,
    protected auditStockService: AuditStockService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ecartStock }) => {
      this.updateForm(ecartStock);

      this.produitService.query().subscribe((res: HttpResponse<IProduit[]>) => (this.produits = res.body || []));

      this.auditStockService.query().subscribe((res: HttpResponse<IAuditStock[]>) => (this.auditstocks = res.body || []));
    });
  }

  updateForm(ecartStock: IEcartStock): void {
    this.editForm.patchValue({
      id: ecartStock.id,
      quantiteTheorique: ecartStock.quantiteTheorique,
      quantitePhysique: ecartStock.quantitePhysique,
      ecart: ecartStock.ecart,
      commentaire: ecartStock.commentaire,
      produitId: ecartStock.produitId,
      auditId: ecartStock.auditId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const ecartStock = this.createFromForm();
    if (ecartStock.id !== undefined) {
      this.subscribeToSaveResponse(this.ecartStockService.update(ecartStock));
    } else {
      this.subscribeToSaveResponse(this.ecartStockService.create(ecartStock));
    }
  }

  private createFromForm(): IEcartStock {
    return {
      ...new EcartStock(),
      id: this.editForm.get(['id'])!.value,
      quantiteTheorique: this.editForm.get(['quantiteTheorique'])!.value,
      quantitePhysique: this.editForm.get(['quantitePhysique'])!.value,
      ecart: this.editForm.get(['ecart'])!.value,
      commentaire: this.editForm.get(['commentaire'])!.value,
      produitId: this.editForm.get(['produitId'])!.value,
      auditId: this.editForm.get(['auditId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEcartStock>>): void {
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
