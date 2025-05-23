import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, ParamMap, Router, Data } from '@angular/router';
import { Subscription, combineLatest } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IActionCommerciale } from 'app/shared/model/ClientSales/action-commerciale.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { ActionCommercialeService } from './action-commerciale.service';
import { ActionCommercialeDeleteDialogComponent } from './action-commerciale-delete-dialog.component';
import { IPointDeVente } from 'app/shared/model/ClientSales/point-de-vente.model';
import { PointDeVenteService } from 'app/entities/ClientSales/point-de-vente/point-de-vente.service';

@Component({
  selector: 'jhi-action-commerciale',
  templateUrl: './action-commerciale.component.html',
  styleUrls: ['./action.commerciale.component.scss'],
})
export class ActionCommercialeComponent implements OnInit, OnDestroy {
  actionCommerciales!: IActionCommerciale[];
  actionCommerciale!: IActionCommerciale;
  pointsDeVentes!: IPointDeVente[];
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
    protected actionCommercialeService: ActionCommercialeService,
    private pointDeVenteService: PointDeVenteService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number, dontNavigate?: boolean): void {
    const pageToLoad: number = page || this.page || 1;

    this.actionCommercialeService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe(
        (res: HttpResponse<IActionCommerciale[]>) => this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate),
        () => this.onError()
      );
  }

  ngOnInit(): void {
    this.handleNavigation();
    this.registerChangeInActionCommerciales();
    this.pointDeVenteService.query().subscribe(res => {
      this.pointsDeVentes = res.body ?? [];
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

  trackId(index: number, item: IActionCommerciale): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInActionCommerciales(): void {
    this.eventSubscriber = this.eventManager.subscribe('actionCommercialeListModification', () => this.loadPage());
  }

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
  delete(actionCommerciale: IActionCommerciale): void {
    const modalRef = this.modalService.open(ActionCommercialeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.actionCommerciale = actionCommerciale;
  }
  onRowDblClick(e: any): void {
    this.router.navigate([`/action-commerciale/${e.data.id}/view`]);
  }

  selectedChanged(e: any): void {
    this.selectedRowIndex = e.component.getRowIndexByKey(e.selectedRowKeys[0]);
  }

  onDeleteBtnClicked(e: any, content: any): void {
    this.actionCommerciale = e.data;
    this.modalService.open(content, { centered: true });
  }

  confirmDelete(content: any): void {
    this.actionCommercialeService.delete(this.actionCommerciale.id!).subscribe(() => {
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

  protected onSuccess(data: IActionCommerciale[] | null, headers: HttpHeaders, page: number, navigate: boolean): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    if (navigate) {
      this.router.navigate(['/action-commerciale'], {
        queryParams: {
          page: this.page,
          size: this.itemsPerPage,
          sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc'),
        },
      });
    }
    this.actionCommerciales = data || [];
    this.ngbPaginationPage = this.page;
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page ?? 1;
  }
}
