import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, ParamMap, Router, Data } from '@angular/router';
import { Subscription, combineLatest } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPaiement } from 'app/shared/model/Finance/paiement.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { PaiementService } from './paiement.service';
import { PaiementDeleteDialogComponent } from './paiement-delete-dialog.component';
import { FactureService } from 'app/entities/Finance/facture/facture.service';
import { CompteService } from 'app/entities/Finance/compte/compte.service';
import { IFacture } from 'app/shared/model/Finance/facture.model';
import { ICompte } from 'app/shared/model/Finance/compte.model';

@Component({
  selector: 'jhi-paiement',
  templateUrl: './paiement.component.html',
})
export class PaiementComponent implements OnInit, OnDestroy {
  paiements!: IPaiement[];
  paiement!: IPaiement;
  factures!: IFacture[];
  comptes!: ICompte[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;
  columnResizingMode = 'nextColumn';
  selectedRowIndex = -1;

  searchPanel = {
    visible: true,
    width: 350,
    placeholder: 'Search',
  };
  constructor(
    protected paiementService: PaiementService,
    private factureService: FactureService,
    private compteService: CompteService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number, dontNavigate?: boolean): void {
    const pageToLoad: number = page || this.page || 1;

    this.paiementService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe(
        (res: HttpResponse<IPaiement[]>) => this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate),
        () => this.onError()
      );
  }

  ngOnInit(): void {
    this.handleNavigation();
    this.registerChangeInPaiements();

    this.factureService.query().subscribe(res => {
      this.factures = res.body ?? [];
    });

    this.compteService.query().subscribe(res => {
      this.comptes = res.body ?? [];
    });
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

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IPaiement): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInPaiements(): void {
    this.eventSubscriber = this.eventManager.subscribe('paiementListModification', () => this.loadPage());
  }

  getFactureNumeroById(factureId: number): string {
    const facture = this.factures.find(f => f.id === factureId);
    return facture?.numeroFacture ?? 'Inconnu';
  }

  getCompteNumeroById(compteId: number): string {
    const compte = this.comptes.find(c => c.id === compteId);
    return compte?.numeroCompte ?? 'Inconnu';
  }

  factureNumero = (rowData: any): string => this.getFactureNumeroById(rowData.factureId);
  compteNumero = (rowData: any): string => this.getCompteNumeroById(rowData.compteId);

  delete(paiement: IPaiement): void {
    const modalRef = this.modalService.open(PaiementDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.paiement = paiement;
  }
  onRowDblClick(e: any): void {
    this.router.navigate([`/paiement/${e.data.id}/view`]);
  }

  selectedChanged(e: any): void {
    this.selectedRowIndex = e.component.getRowIndexByKey(e.selectedRowKeys[0]);
  }

  onDeleteBtnClicked(e: any, content: any): void {
    this.paiement = e.data;
    this.modalService.open(content, { centered: true });
  }

  confirmDelete(content: any): void {
    this.paiementService.delete(this.paiement.id!).subscribe(() => {
      content.close();
      this.loadPage();
    });
  }
  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: IPaiement[] | null, headers: HttpHeaders, page: number, navigate: boolean): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    if (navigate) {
      this.router.navigate(['/paiement'], {
        queryParams: {
          page: this.page,
          size: this.itemsPerPage,
          sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc'),
        },
      });
    }
    this.paiements = data || [];
    this.ngbPaginationPage = this.page;
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page ?? 1;
  }
}
