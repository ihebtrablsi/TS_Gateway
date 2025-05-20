import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, ParamMap, Router, Data } from '@angular/router';
import { Subscription, combineLatest } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IMouvementStock } from 'app/shared/model/ProductInventory/mouvement-stock.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { MouvementStockService } from './mouvement-stock.service';
import { MouvementStockDeleteDialogComponent } from './mouvement-stock-delete-dialog.component';
import { IDepot } from 'app/shared/model/ProductInventory/depot.model';
import { IProduit } from 'app/shared/model/ProductInventory/produit.model';
import { ProduitService } from 'app/entities/ProductInventory/produit/produit.service';
import { DepotService } from 'app/entities/ProductInventory/depot/depot.service';

@Component({
  selector: 'jhi-mouvement-stock',
  templateUrl: './mouvement-stock.component.html',
})
export class MouvementStockComponent implements OnInit, OnDestroy {
  mouvementStocks!: IMouvementStock[];
  mouvementStock!: IMouvementStock;
  produits!: IProduit[];
  depots!: IDepot[];
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
    protected mouvementStockService: MouvementStockService,
    private produitService: ProduitService,
    private depotService: DepotService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number, dontNavigate?: boolean): void {
    const pageToLoad: number = page || this.page || 1;

    this.mouvementStockService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe(
        (res: HttpResponse<IMouvementStock[]>) => this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate),
        () => this.onError()
      );
  }

  ngOnInit(): void {
    this.handleNavigation();
    this.registerChangeInMouvementStocks();
    this.produitService.query().subscribe(res => {
      this.produits = res.body ?? [];
    });
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

  trackId(index: number, item: IMouvementStock): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInMouvementStocks(): void {
    this.eventSubscriber = this.eventManager.subscribe('mouvementStockListModification', () => this.loadPage());
  }

  getProduitNameById(produitId: number): string {
    if (this.produits) {
      const produit = this.produits.find(r => r.id === produitId);
      return produit ? produit.nom! : 'Unknown';
    }
    return 'Unknown';
  }

  getDepotNomById(depotId: number): string {
    if (this.depots) {
      const depot = this.depots.find(r => r.id === depotId);
      return depot ? depot.nom! : 'Unknown';
    }
    return 'Unknown';
  }

  produitNom = (rowData: any): string => {
    return this.getProduitNameById(rowData.produitId);
  };

  depotNom = (rowData: any): string => {
    return this.getDepotNomById(rowData.depotId);
  };

  delete(mouvementStock: IMouvementStock): void {
    const modalRef = this.modalService.open(MouvementStockDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.mouvementStock = mouvementStock;
  }
  onRowDblClick(e: any): void {
    this.router.navigate([`/mouvement-stock/${e.data.id}/view`]);
  }

  selectedChanged(e: any): void {
    this.selectedRowIndex = e.component.getRowIndexByKey(e.selectedRowKeys[0]);
  }

  onDeleteBtnClicked(e: any, content: any): void {
    this.mouvementStock = e.data;
    this.modalService.open(content, { centered: true });
  }

  confirmDelete(content: any): void {
    this.mouvementStockService.delete(this.mouvementStock.id!).subscribe(() => {
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

  protected onSuccess(data: IMouvementStock[] | null, headers: HttpHeaders, page: number, navigate: boolean): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    if (navigate) {
      this.router.navigate(['/mouvement-stock'], {
        queryParams: {
          page: this.page,
          size: this.itemsPerPage,
          sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc'),
        },
      });
    }
    this.mouvementStocks = data || [];
    this.ngbPaginationPage = this.page;
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page ?? 1;
  }
}
