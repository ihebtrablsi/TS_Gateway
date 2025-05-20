import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, ParamMap, Router, Data } from '@angular/router';
import { Subscription, combineLatest } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ILettrage } from 'app/shared/model/Finance/lettrage.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { LettrageService } from './lettrage.service';
import { LettrageDeleteDialogComponent } from './lettrage-delete-dialog.component';
import { FactureService } from 'app/entities/Finance/facture/facture.service';
import { PaiementService } from 'app/entities/Finance/paiement/paiement.service';
import { IFacture } from 'app/shared/model/Finance/facture.model';
import { IPaiement } from 'app/shared/model/Finance/paiement.model';

@Component({
  selector: 'jhi-lettrage',
  templateUrl: './lettrage.component.html',
})
export class LettrageComponent implements OnInit, OnDestroy {
  lettrages!: ILettrage[];
  lettrage!: ILettrage;
  factures!: IFacture[];
  paiementsList!: IPaiement[];
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
    protected lettrageService: LettrageService,
    protected factureService: FactureService,
    protected paiementService: PaiementService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number, dontNavigate?: boolean): void {
    const pageToLoad: number = page || this.page || 1;

    this.lettrageService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe(
        (res: HttpResponse<ILettrage[]>) => this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate),
        () => this.onError()
      );
  }

  ngOnInit(): void {
    this.handleNavigation();
    this.registerChangeInLettrages();
    this.factureService.query().subscribe(res => (this.factures = res.body ?? []));
    this.paiementService.query().subscribe(res => (this.paiementsList = res.body ?? []));
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

  trackId(index: number, item: ILettrage): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInLettrages(): void {
    this.eventSubscriber = this.eventManager.subscribe('lettrageListModification', () => this.loadPage());
  }

  getFactureNumeroById(id: number): string {
    const facture = this.factures.find(f => f.id === id);
    return facture ? facture.numeroFacture ?? 'Inconnu' : 'Inconnu';
  }

  getPaiementReferenceById(id: number): string {
    const paiement = this.paiementsList.find(p => p.id === id);
    return paiement ? paiement.reference ?? 'Inconnu' : 'Inconnu';
  }

  factureNumero = (rowData: any): string => this.getFactureNumeroById(rowData.factureId);
  paiementReference = (rowData: any): string => this.getPaiementReferenceById(rowData.paiementId);

  delete(lettrage: ILettrage): void {
    const modalRef = this.modalService.open(LettrageDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.lettrage = lettrage;
  }
  onRowDblClick(e: any): void {
    this.router.navigate([`/lettrage/${e.data.id}/view`]);
  }

  selectedChanged(e: any): void {
    this.selectedRowIndex = e.component.getRowIndexByKey(e.selectedRowKeys[0]);
  }

  onDeleteBtnClicked(e: any, content: any): void {
    this.lettrage = e.data;
    this.modalService.open(content, { centered: true });
  }

  confirmDelete(content: any): void {
    this.lettrageService.delete(this.lettrage.id!).subscribe(() => {
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

  protected onSuccess(data: ILettrage[] | null, headers: HttpHeaders, page: number, navigate: boolean): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    if (navigate) {
      this.router.navigate(['/lettrage'], {
        queryParams: {
          page: this.page,
          size: this.itemsPerPage,
          sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc'),
        },
      });
    }
    this.lettrages = data || [];
    this.ngbPaginationPage = this.page;
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page ?? 1;
  }
}
