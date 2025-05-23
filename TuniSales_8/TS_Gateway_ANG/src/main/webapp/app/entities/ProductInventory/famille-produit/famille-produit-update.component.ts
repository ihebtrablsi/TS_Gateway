import {Component, OnInit, ViewChild} from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators, FormArray } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IFamilleProduit, FamilleProduit } from 'app/shared/model/ProductInventory/famille-produit.model';
import { FamilleProduitService } from './famille-produit.service';
import {JsonEditorComponent, JsonEditorOptions} from "ang-jsoneditor";

@Component({
  selector: 'jhi-famille-produit-update',
  templateUrl: './famille-produit-update.component.html',
})
export class FamilleProduitUpdateComponent implements OnInit {
  isSaving = false;

  optionsJSON: any;

  editForm = this.fb.group({
    id: [],
    nom: [null, [Validators.required]],
    description: [],
    options: [],
  });

  public editorOptions1: JsonEditorOptions | undefined;
  codeArray: any[] = [];
  public editorOptions: JsonEditorOptions | undefined;
  @ViewChild(JsonEditorComponent, { static: false }) editor: JsonEditorComponent | undefined;

  constructor(
      protected familleProduitService: FamilleProduitService,
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

    this.activatedRoute.data.subscribe(({ familleProduit }) => {

          this.optionsJSON =
              familleProduit.options !== undefined ? JSON.parse(JSON.stringify(familleProduit.options)) : [];
      this.updateForm(familleProduit);
    });
  }

  get options(): FormArray {
    return this.editForm.get('options') as FormArray;
  }

  addOption(): void {
    this.options.push(this.fb.group({ key: '', value: '' }));
  }

  removeOption(index: number): void {
    this.options.removeAt(index);
  }

  updateForm(familleProduit: IFamilleProduit): void {
    this.editForm.patchValue({
      id: familleProduit.id,
      nom: familleProduit.nom,
      description: familleProduit.description,
      options:familleProduit.options
    });

  //   this.options.clear();
  //   try {
  //     const parsedOptions = JSON.parse(familleProduit.options as string ?? '[]');
  //     if (Array.isArray(parsedOptions)) {
  //       parsedOptions.forEach((opt: any) => {
  //         this.options.push(this.fb.group({ key: opt.key, value: opt.value }));
  //       });
  //     }
  //   } catch {
  //     // ignore malformed JSON
  //   }
  }

  save(): void {
    this.isSaving = true;

    const familleProduit = this.createFromForm();
    if (familleProduit.id !== undefined) {
      this.subscribeToSaveResponse(this.familleProduitService.update(familleProduit));
    } else {
      this.subscribeToSaveResponse(this.familleProduitService.create(familleProduit));
    }
  }

  private createFromForm(): IFamilleProduit {
    return {
      ...new FamilleProduit(),
      id: this.editForm.get(['id'])!.value,
      nom: this.editForm.get(['nom'])!.value,
      description: this.editForm.get(['description'])!.value,
      options: JSON.parse(JSON.stringify(this.optionsJSON)),
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFamilleProduit>>): void {
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

  previousState(): void {
    window.history.back();
  }
}
