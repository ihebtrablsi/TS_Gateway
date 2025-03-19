import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ICommande, Commande } from 'app/shared/model/ClientSales/commande.model';
import { CommandeService } from './commande.service';
import { IClient } from 'app/shared/model/ClientSales/client.model';
import { ClientService } from 'app/entities/ClientSales/client/client.service';
import { IPointDeVente } from 'app/shared/model/ClientSales/point-de-vente.model';
import { PointDeVenteService } from 'app/entities/ClientSales/point-de-vente/point-de-vente.service';

type SelectableEntity = IClient | IPointDeVente;

@Component({
  selector: 'jhi-commande-update',
  templateUrl: './commande-update.component.html',
})
export class CommandeUpdateComponent implements OnInit {
  isSaving = false;
  clients: IClient[] = [];
  pointdeventes: IPointDeVente[] = [];

  editForm = this.fb.group({
    id: [],
    dateCommande: [null, [Validators.required]],
    statut: [null, [Validators.required]],
    total: [null, [Validators.required]],
    clientId: [],
    pointDeVenteId: [],
  });

  constructor(
    protected commandeService: CommandeService,
    protected clientService: ClientService,
    protected pointDeVenteService: PointDeVenteService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ commande }) => {
      if (!commande.id) {
        const today = moment().startOf('day');
        commande.dateCommande = today;
      }

      this.updateForm(commande);

      this.clientService.query().subscribe((res: HttpResponse<IClient[]>) => (this.clients = res.body || []));

      this.pointDeVenteService.query().subscribe((res: HttpResponse<IPointDeVente[]>) => (this.pointdeventes = res.body || []));
    });
  }

  updateForm(commande: ICommande): void {
    this.editForm.patchValue({
      id: commande.id,
      dateCommande: commande.dateCommande ? commande.dateCommande.format(DATE_TIME_FORMAT) : null,
      statut: commande.statut,
      total: commande.total,
      clientId: commande.clientId,
      pointDeVenteId: commande.pointDeVenteId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const commande = this.createFromForm();
    if (commande.id !== undefined) {
      this.subscribeToSaveResponse(this.commandeService.update(commande));
    } else {
      this.subscribeToSaveResponse(this.commandeService.create(commande));
    }
  }

  private createFromForm(): ICommande {
    return {
      ...new Commande(),
      id: this.editForm.get(['id'])!.value,
      dateCommande: this.editForm.get(['dateCommande'])!.value
        ? moment(this.editForm.get(['dateCommande'])!.value, DATE_TIME_FORMAT)
        : undefined,
      statut: this.editForm.get(['statut'])!.value,
      total: this.editForm.get(['total'])!.value,
      clientId: this.editForm.get(['clientId'])!.value,
      pointDeVenteId: this.editForm.get(['pointDeVenteId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICommande>>): void {
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
