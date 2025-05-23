import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, ParamMap, Router, Data } from '@angular/router';
import { Subscription, combineLatest } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAuditStock } from 'app/shared/model/ProductInventory/audit-stock.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { AuditStockService } from './audit-stock.service';
import { AuditStockDeleteDialogComponent } from './audit-stock-delete-dialog.component';
import { DepotService } from 'app/entities/ProductInventory/depot/depot.service';
import { IDepot } from 'app/shared/model/ProductInventory/depot.model';

@Component({
  selector: 'jhi-audit-stock',
  templateUrl: './audit-stock.component.html',
  styleUrls: ['./audit-stock.component.scss'],
})
export class AuditStockComponent implements OnInit, OnDestroy {
  auditStocks: IAuditStock[] = [];
  auditStock!: IAuditStock;
  depots!: IDepot[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;
  selectedRowIndex = -1;

  searchPanel = {
    visible: true,
    width: 350,
    placeholder: 'Search',
  };
  constructor(
    protected auditStockService: AuditStockService,
    private depotService: DepotService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number, dontNavigate?: boolean): void {
    const pageToLoad: number = page || this.page || 1;

    this.auditStockService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe(
        (res: HttpResponse<IAuditStock[]>) => this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate),
        () => this.onError()
      );
  }

  ngOnInit(): void {
    this.handleNavigation();
    this.registerChangeInAuditStocks();
    this.depotService.query().subscribe(res => {
      this.depots = res.body ?? [];
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

  trackId(index: number, item: IAuditStock): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInAuditStocks(): void {
    this.eventSubscriber = this.eventManager.subscribe('auditStockListModification', () => this.loadPage());
  }

  getDepotNomById(depotId: number): string {
    if (this.depots) {
      const depot = this.depots.find(r => r.id === depotId);
      return depot ? depot.nom! : 'Unknown';
    }
    return 'Unknown';
  }
  depotNom = (rowData: any): string => {
    return this.getDepotNomById(rowData.depotId);
  };
  delete(auditStock: IAuditStock): void {
    const modalRef = this.modalService.open(AuditStockDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.auditStock = auditStock;
  }
  onRowDblClick(e: any): void {
    this.router.navigate([`/audit-stock/${e.data.id}/view`]);
  }

  selectedChanged(e: any): void {
    this.selectedRowIndex = e.component.getRowIndexByKey(e.selectedRowKeys[0]);
  }

  onDeleteBtnClicked(e: any, content: any): void {
    this.auditStock = e.data;
    this.modalService.open(content, { centered: true });
  }

  confirmDelete(content: any): void {
    this.auditStockService.delete(this.auditStock.id!).subscribe(() => {
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

  protected onSuccess(data: IAuditStock[] | null, headers: HttpHeaders, page: number, navigate: boolean): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    if (navigate) {
      this.router.navigate(['/audit-stock'], {
        queryParams: {
          page: this.page,
          size: this.itemsPerPage,
          sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc'),
        },
      });
    }
    this.auditStocks = data || [];
    this.ngbPaginationPage = this.page;
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page ?? 1;
  }
}
