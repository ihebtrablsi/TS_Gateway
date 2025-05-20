import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, ParamMap, Router, Data } from '@angular/router';
import { Subscription, combineLatest } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISuiviAction } from 'app/shared/model/ClientSales/suivi-action.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { SuiviActionService } from './suivi-action.service';
import { SuiviActionDeleteDialogComponent } from './suivi-action-delete-dialog.component';
import { IActionCommerciale } from 'app/shared/model/ClientSales/action-commerciale.model';
import { ActionCommercialeService } from 'app/entities/ClientSales/action-commerciale/action-commerciale.service';

@Component({
  selector: 'jhi-suivi-action',
  templateUrl: './suivi-action.component.html',
})
export class SuiviActionComponent implements OnInit, OnDestroy {
  suiviActions!: ISuiviAction[];
  suiviAction!: ISuiviAction;
  actionCommerciales!: IActionCommerciale[];
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
    protected suiviActionService: SuiviActionService,
    private actionCommerciale: ActionCommercialeService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number, dontNavigate?: boolean): void {
    const pageToLoad: number = page || this.page || 1;

    this.suiviActionService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe(
        (res: HttpResponse<ISuiviAction[]>) => this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate),
        () => this.onError()
      );
  }

  ngOnInit(): void {
    this.handleNavigation();
    this.registerChangeInSuiviActions();
    this.actionCommerciale.query().subscribe(res => {
      this.actionCommerciales = res.body ?? [];
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

  trackId(index: number, item: ISuiviAction): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInSuiviActions(): void {
    this.eventSubscriber = this.eventManager.subscribe('suiviActionListModification', () => this.loadPage());
  }

  getActionCommercialeNameById(actionCommercialeId: number): string {
    if (this.actionCommerciales) {
      const actionCommerciale = this.actionCommerciales.find(r => r.id === actionCommercialeId);
      return actionCommerciale ? actionCommerciale.nom! : 'Unknown';
    }
    return 'Unknown';
  }

  ActionCommercialeNom = (rowData: any): string => {
    return this.getActionCommercialeNameById(rowData.actionCommercialeId);
  };
  delete(suiviAction: ISuiviAction): void {
    const modalRef = this.modalService.open(SuiviActionDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.suiviAction = suiviAction;
  }

  onRowDblClick(e: any): void {
    this.router.navigate([`/suivi-action/${e.data.id}/view`]);
  }

  selectedChanged(e: any): void {
    this.selectedRowIndex = e.component.getRowIndexByKey(e.selectedRowKeys[0]);
  }

  onDeleteBtnClicked(e: any, content: any): void {
    this.suiviAction = e.data;
    this.modalService.open(content, { centered: true });
  }

  confirmDelete(content: any): void {
    this.suiviActionService.delete(this.suiviAction.id!).subscribe(() => {
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

  protected onSuccess(data: ISuiviAction[] | null, headers: HttpHeaders, page: number, navigate: boolean): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    if (navigate) {
      this.router.navigate(['/suivi-action'], {
        queryParams: {
          page: this.page,
          size: this.itemsPerPage,
          sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc'),
        },
      });
    }
    this.suiviActions = data || [];
    this.ngbPaginationPage = this.page;
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page ?? 1;
  }
}
