import {Component, OnInit, ViewChild} from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IProduit, Produit } from 'app/shared/model/ProductInventory/produit.model';
import { ProduitService } from './produit.service';
import { IFamilleProduit } from 'app/shared/model/ProductInventory/famille-produit.model';
import { FamilleProduitService } from 'app/entities/ProductInventory/famille-produit/famille-produit.service';
import {JsonEditorComponent, JsonEditorOptions} from "ang-jsoneditor";
import {AccountService} from "app/core/auth/account.service";

@Component({
  selector: 'jhi-produit-update',
  templateUrl: './produit-update.component.html',
})
export class ProduitUpdateComponent implements OnInit {
  isSaving = false;
  familleproduits: IFamilleProduit[] = [];
  optionsJSON: any;


  editForm = this.fb.group({
    id: [],
    nom: [null, [Validators.required]],
    description: [],
    prix: [null, [Validators.required, Validators.min(0)]],
    stock: [null, [Validators.required, Validators.min(0)]],
    categorie: [],
    imageUrl: [],
    details: [],
    familleId: [],
  });
  public editorOptions1: JsonEditorOptions | undefined;
  codeArray: any[] = [];
  public editorOptions: JsonEditorOptions | undefined;
  @ViewChild(JsonEditorComponent, { static: false }) editor: JsonEditorComponent | undefined;

  constructor(
    protected produitService: ProduitService,
    protected familleProduitService: FamilleProduitService,
    private accountService: AccountService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}


  ngOnInit(): void {
    this.editorOptions1 = new JsonEditorOptions();
    this.editorOptions1.mode = 'tree';
    this.editorOptions1.mainMenuBar = false;
    this.editorOptions = new JsonEditorOptions();
    this.editorOptions.expandAll = true;
    this.editorOptions.modes = ['tree', 'code'];

    this.activatedRoute.data.subscribe(({ produit }) => {
      this.optionsJSON =
          produit.options !== undefined ? JSON.parse(JSON.stringify(produit.options)) : [];
      this.updateForm(produit);

      this.familleProduitService.query().subscribe((res: HttpResponse<IFamilleProduit[]>) => (this.familleproduits = res.body || []));
    });
  }

  updateForm(produit: IProduit): void {
    this.editForm.patchValue({
      id: produit.id,
      nom: produit.nom,
      description: produit.description,
      prix: produit.prix,
      stock: produit.stock,
      categorie: produit.categorie,
      imageUrl: produit.imageUrl,
      details: produit.details,
      familleId: produit.familleId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const produit = this.createFromForm();
    if (produit.id !== undefined) {
      this.subscribeToSaveResponse(this.produitService.update(produit));
    } else {
      this.subscribeToSaveResponse(this.produitService.create(produit));
    }
  }

  private createFromForm(): IProduit {
    return {
      ...new Produit(),
      id: this.editForm.get(['id'])!.value,
      nom: this.editForm.get(['nom'])!.value,
      description: this.editForm.get(['description'])!.value,
      prix: this.editForm.get(['prix'])!.value,
      stock: this.editForm.get(['stock'])!.value,
      categorie: this.editForm.get(['categorie'])!.value,
      imageUrl: this.editForm.get(['imageUrl'])!.value,
      familleId: this.editForm.get(['familleId'])!.value,
      details: JSON.parse(JSON.stringify(this.optionsJSON)),

    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProduit>>): void {
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

  trackById(index: number, item: IFamilleProduit): any {
    return item.id;
  }
}
