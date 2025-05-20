import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, ParamMap, Router, Data } from '@angular/router';
import { Subscription, combineLatest } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPromotion } from 'app/shared/model/ProductInventory/promotion.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { PromotionService } from './promotion.service';
import { PromotionDeleteDialogComponent } from './promotion-delete-dialog.component';
import { IProduit } from 'app/shared/model/ProductInventory/produit.model';
import { ProduitService } from 'app/entities/ProductInventory/produit/produit.service';

@Component({
  selector: 'jhi-promotion',
  templateUrl: './promotion.component.html',
  styleUrls: ['./promotion.component.scss'],
})
export class PromotionComponent implements OnInit, OnDestroy {
  promotions!: IPromotion[];
  promotion!: IPromotion;
  produits!: IProduit[];
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
    protected promotionService: PromotionService,
    private produitService: ProduitService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number, dontNavigate?: boolean): void {
    const pageToLoad: number = page || this.page || 1;

    this.promotionService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe(
        (res: HttpResponse<IPromotion[]>) => this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate),
        () => this.onError()
      );
  }

  ngOnInit(): void {
    this.handleNavigation();
    this.registerChangeInPromotions();
    this.produitService.query().subscribe(res => {
      this.produits = res.body ?? [];
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

  trackId(index: number, item: IPromotion): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInPromotions(): void {
    this.eventSubscriber = this.eventManager.subscribe('promotionListModification', () => this.loadPage());
  }

  getProduitNameById(produitId: number): string {
    if (this.produits) {
      const produit = this.produits.find(r => r.id === produitId);
      return produit ? produit.nom! : 'Unknown';
    }
    return 'Unknown';
  }

  ProduitNom = (rowData: any): string => {
    return this.getProduitNameById(rowData.produitId);
  };
  delete(promotion: IPromotion): void {
    const modalRef = this.modalService.open(PromotionDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.promotion = promotion;
  }
  onRowDblClick(e: any): void {
    this.router.navigate([`/promotion/${e.data.id}/view`]);
  }

  selectedChanged(e: any): void {
    this.selectedRowIndex = e.component.getRowIndexByKey(e.selectedRowKeys[0]);
  }

  onDeleteBtnClicked(e: any, content: any): void {
    this.promotion = e.data;
    this.modalService.open(content, { centered: true });
  }

  confirmDelete(content: any): void {
    this.promotionService.delete(this.promotion.id!).subscribe(() => {
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

  protected onSuccess(data: IPromotion[] | null, headers: HttpHeaders, page: number, navigate: boolean): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    if (navigate) {
      this.router.navigate(['/promotion'], {
        queryParams: {
          page: this.page,
          size: this.itemsPerPage,
          sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc'),
        },
      });
    }
    this.promotions = data || [];
    this.ngbPaginationPage = this.page;
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page ?? 1;
  }
}
