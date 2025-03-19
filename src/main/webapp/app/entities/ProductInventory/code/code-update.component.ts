import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICode, Code } from 'app/shared/model/ProductInventory/code.model';
import { CodeService } from './code.service';
import { IProduit } from 'app/shared/model/ProductInventory/produit.model';
import { ProduitService } from 'app/entities/ProductInventory/produit/produit.service';

@Component({
  selector: 'jhi-code-update',
  templateUrl: './code-update.component.html',
})
export class CodeUpdateComponent implements OnInit {
  isSaving = false;
  produits: IProduit[] = [];

  editForm = this.fb.group({
    id: [],
    code: [null, [Validators.required]],
    produitId: [],
  });

  constructor(
    protected codeService: CodeService,
    protected produitService: ProduitService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ code }) => {
      this.updateForm(code);

      this.produitService.query().subscribe((res: HttpResponse<IProduit[]>) => (this.produits = res.body || []));
    });
  }

  updateForm(code: ICode): void {
    this.editForm.patchValue({
      id: code.id,
      code: code.code,
      produitId: code.produitId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const code = this.createFromForm();
    if (code.id !== undefined) {
      this.subscribeToSaveResponse(this.codeService.update(code));
    } else {
      this.subscribeToSaveResponse(this.codeService.create(code));
    }
  }

  private createFromForm(): ICode {
    return {
      ...new Code(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      produitId: this.editForm.get(['produitId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICode>>): void {
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

  trackById(index: number, item: IProduit): any {
    return item.id;
  }
}
