import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, ParamMap, Router, Data } from '@angular/router';
import { Subscription, combineLatest } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IEcartStock } from 'app/shared/model/ProductInventory/ecart-stock.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { EcartStockService } from './ecart-stock.service';
import { EcartStockDeleteDialogComponent } from './ecart-stock-delete-dialog.component';
import { IProduit } from 'app/shared/model/ProductInventory/produit.model';
import { IAuditStock } from 'app/shared/model/ProductInventory/audit-stock.model';
import { AuditStockService } from 'app/entities/ProductInventory/audit-stock/audit-stock.service';
import { ProduitService } from 'app/entities/ProductInventory/produit/produit.service';

@Component({
  selector: 'jhi-ecart-stock',
  templateUrl: './ecart-stock.component.html',
})
export class EcartStockComponent implements OnInit, OnDestroy {
  ecartStocks!: IEcartStock[];
  ecartStock!: IEcartStock;
  produits!: IProduit[];
  audits!: IAuditStock[];
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
    protected ecartStockService: EcartStockService,
    private produitService: ProduitService,
    private auditStockService: AuditStockService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number, dontNavigate?: boolean): void {
    const pageToLoad: number = page || this.page || 1;

    this.ecartStockService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe(
        (res: HttpResponse<IEcartStock[]>) => this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate),
        () => this.onError()
      );
  }

  ngOnInit(): void {
    this.handleNavigation();
    this.registerChangeInEcartStocks();
    this.produitService.query().subscribe(res => {
      this.produits = res.body ?? [];
    });

    this.auditStockService.query().subscribe(res => {
      this.audits = res.body ?? [];
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

  trackId(index: number, item: IEcartStock): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInEcartStocks(): void {
    this.eventSubscriber = this.eventManager.subscribe('ecartStockListModification', () => this.loadPage());
  }

  getProduitNameById(produitId: number): string {
    if (this.produits) {
      const produit = this.produits.find(r => r.id === produitId);
      return produit ? produit.nom! : 'Unknown';
    }
    return 'Unknown';
  }
  getAuditDateById(auditId: number): string {
    if (this.audits) {
      const audit = this.audits.find(a => a.id === auditId);
      return audit && audit.dateAudit ? audit.dateAudit.toDate().toLocaleDateString() : 'Unknown';
    }
    return 'Unknown';
  }

  produitNom = (rowData: any): string => {
    return this.getProduitNameById(rowData.produitId);
  };

  auditDate = (rowData: any): string => {
    return this.getAuditDateById(rowData.auditId);
  };

  delete(ecartStock: IEcartStock): void {
    const modalRef = this.modalService.open(EcartStockDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.ecartStock = ecartStock;
  }
  onRowDblClick(e: any): void {
    this.router.navigate([`/ecart-stock/${e.data.id}/view`]);
  }

  selectedChanged(e: any): void {
    this.selectedRowIndex = e.component.getRowIndexByKey(e.selectedRowKeys[0]);
  }

  onDeleteBtnClicked(e: any, content: any): void {
    this.ecartStock = e.data;
    this.modalService.open(content, { centered: true });
  }

  confirmDelete(content: any): void {
    this.ecartStockService.delete(this.ecartStock.id!).subscribe(() => {
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

  protected onSuccess(data: IEcartStock[] | null, headers: HttpHeaders, page: number, navigate: boolean): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    if (navigate) {
      this.router.navigate(['/ecart-stock'], {
        queryParams: {
          page: this.page,
          size: this.itemsPerPage,
          sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc'),
        },
      });
    }
    this.ecartStocks = data || [];
    this.ngbPaginationPage = this.page;
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page ?? 1;
  }
}
