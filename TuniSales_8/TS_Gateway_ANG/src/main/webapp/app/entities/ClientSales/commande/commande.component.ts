import { Component, OnInit, OnDestroy, ViewChild } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, ParamMap, Router, Data } from '@angular/router';
import { Subscription, combineLatest } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICommande } from 'app/shared/model/ClientSales/commande.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { CommandeService } from './commande.service';
import { CommandeDeleteDialogComponent } from './commande-delete-dialog.component';
import { Authority } from 'app/shared/constants/authority.constants';
import { AccountService } from 'app/core/auth/account.service';
import { PointDeVenteService } from 'app/entities/ClientSales/point-de-vente/point-de-vente.service';
import { ClientService } from 'app/entities/ClientSales/client/client.service';
import { IClient } from 'app/shared/model/ClientSales/client.model';
import { IPointDeVente } from 'app/shared/model/ClientSales/point-de-vente.model';

@Component({
  selector: 'jhi-commande',
  templateUrl: './commande.component.html',
})
export class CommandeComponent implements OnInit, OnDestroy {
  commandes!: ICommande[];
  commande!: ICommande;
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;
  columnResizingMode = 'nextColumn';
  selectedRowIndex = -1;
  statuts: string[] = ['EN_ATTENTE', 'VALIDEE', 'REFUSEE', 'ANNULEE'];
  Authority = Authority;
  clients!: IClient[];
  pointsDeVentes!: IPointDeVente[];

  @ViewChild('deleteModal') deleteModal: any;

  searchPanel = {
    visible: true,
    width: 350,
    placeholder: 'Search',
  };

  constructor(
    protected commandeService: CommandeService,
    protected activatedRoute: ActivatedRoute,
    protected accountService: AccountService,
    private clientService: ClientService,
    private pointDeVenteService: PointDeVenteService,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  ngOnInit(): void {
    this.handleNavigation();
    this.registerChangeInCommandes();
    this.clientService.query().subscribe(res => {
      this.clients = res.body ?? [];
    });
    this.pointDeVenteService.query().subscribe(res => {
      this.pointsDeVentes = res.body ?? [];
    });
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  loadPage(page?: number, dontNavigate?: boolean): void {
    const pageToLoad: number = page || this.page || 1;

    this.commandeService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe(
        (res: HttpResponse<ICommande[]>) => this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate),
        () => this.onError()
      );
  }

  protected handleNavigation(): void {
    combineLatest(this.activatedRoute.data, this.activatedRoute.queryParamMap, (data: Data, params: ParamMap) => {
      const page = params.get('page');
      const pageNumber = page !== null ? +page : 1;
      const sort = (params.get('sort') ?? data['defaultSort']).split(',');
      const predicate = sort[0];
      const ascending = sort[1] === 'asc';
      if (pageNumber !== this.page || predicate !== this.predicate || ascending !== this.ascending) {
        this.predicate = predicate;
        this.ascending = ascending;
        this.loadPage(pageNumber, true);
      }
    }).subscribe();
  }

  trackId(index: number, item: ICommande): number {
    return item.id!;
  }

  registerChangeInCommandes(): void {
    this.eventSubscriber = this.eventManager.subscribe('commandeListModification', () => this.loadPage());
  }

  delete(commande: ICommande): void {
    const modalRef = this.modalService.open(CommandeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.commande = commande;
  }

  confirmDelete(content: any): void {
    this.commandeService.delete(this.commande.id!).subscribe(() => {
      content.close();
      this.loadPage();
    });
  }

  onDeleteBtnClicked(data: ICommande, content: any): void {
    this.commande = data;
    this.modalService.open(content, { centered: true });
  }

  getClientNameById(clientId: number): string {
    if (this.clients) {
      const client = this.clients.find(r => r.id === clientId);
      return client ? client.nom! : 'Unknown';
    }
    return 'Unknown';
  }

  clientNom = (rowData: any): string => {
    return this.getClientNameById(rowData.clientId);
  };

  getPointDeVenteNameById(pointDeVenteId: number): string {
    if (this.pointsDeVentes) {
      const pointsDeVente = this.pointsDeVentes.find(r => r.id === pointDeVenteId);
      return pointsDeVente ? pointsDeVente.nom! : 'Unknown';
    }
    return 'Unknown';
  }

  pointDeVenteNom = (rowData: any): string => {
    return this.getPointDeVenteNameById(rowData.pointDeVenteId);
  };
  onRowDblClick(e: any): void {
    this.router.navigate([`/commande/${e.data.id}/view`]);
  }

  selectedChanged(e: any): void {
    this.selectedRowIndex = e.component.getRowIndexByKey(e.selectedRowKeys[0]);
  }

  onStatutChange(commande: ICommande, nouveauStatut: string): void {
    const id = commande.id;
    if (!id) return;

    switch (nouveauStatut) {
      case 'VALIDEE':
        this.commandeService.valider(id).subscribe(() => this.loadPage());
        break;
      case 'REFUSEE':
        this.commandeService.refuser(id).subscribe(() => this.loadPage());
        break;
      case 'ANNULEE':
        this.commandeService.annuler(id).subscribe(() => this.loadPage());
        break;
    }
  }

  hasAnyAuthority(authorities: string[] | string): boolean {
    return this.accountService.hasAnyAuthority(authorities);
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: ICommande[] | null, headers: HttpHeaders, page: number, navigate: boolean): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    if (navigate) {
      this.router.navigate(['/commande'], {
        queryParams: {
          page: this.page,
          size: this.itemsPerPage,
          sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc'),
        },
      });
    }
    this.commandes = data || [];
    this.ngbPaginationPage = this.page;
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page ?? 1;
  }

  renderStatutCell = (cellElement: HTMLElement, cellInfo: any): void => {
    const statut = cellInfo.data.statut;

    if (statut === 'EN_ATTENTE' || statut === 'ANNULEE') {
      const select = document.createElement('select');
      select.className = 'form-control form-control-sm';

      this.statuts.forEach(s => {
        const option = document.createElement('option');
        option.value = s;
        option.text = s;
        if (s === statut) option.selected = true;
        select.appendChild(option);
      });

      select.addEventListener('change', event => {
        const newStatut = (event.target as HTMLSelectElement).value;
        this.onStatutChange(cellInfo.data, newStatut);
      });

      cellElement.appendChild(select);
    } else {
      const badge = document.createElement('span');
      badge.className = 'badge';
      badge.textContent = statut;

      if (statut === 'VALIDEE') badge.classList.add('badge-success');
      else if (statut === 'REFUSEE') badge.classList.add('badge-danger');
      else if (statut === 'EN_ATTENTE') badge.classList.add('badge-secondary');
      else if (statut === 'ANNULEE') badge.classList.add('badge-warning');

      cellElement.appendChild(badge);
    }
  };

  renderActionCell = (cellElement: HTMLElement, cellInfo: any): void => {
    const btn = document.createElement('button');
    btn.className = 'btn btn-danger btn-sm dataGridActionbtn';
    btn.innerHTML = `<i class="fas fa-trash"></i>`;
    btn.onclick = () => this.onDeleteBtnClicked(cellInfo.data, this.deleteModal);
    cellElement.appendChild(btn);
  };
  onCellPrepared(e: any): void {
    if (e.rowType === 'data') {
      if (e.column.dataField === 'statut') {
        this.renderStatutCell(e.cellElement, e);
      } else if (e.column.caption === 'Action') {
        this.renderActionCell(e.cellElement, e);
      }
    }
  }
}
