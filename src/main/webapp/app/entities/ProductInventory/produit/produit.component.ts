import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, ParamMap, Router, Data } from '@angular/router';
import { Subscription, combineLatest } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IProduit } from 'app/shared/model/ProductInventory/produit.model';
import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { ProduitService } from './produit.service';
import { ProduitDeleteDialogComponent } from './produit-delete-dialog.component';
import { IFamilleProduit } from 'app/shared/model/ProductInventory/famille-produit.model';
import { FamilleProduitService } from 'app/entities/ProductInventory/famille-produit/famille-produit.service';

type Alignment = 'left' | 'right';

@Component({
  selector: 'jhi-produit',
  templateUrl: './produit.component.html',
  styleUrls: ['./produit.component.scss'],
})
export class ProduitComponent implements OnInit, OnDestroy {
  produits: IProduit[] = [];
  produit!: IProduit;
  familleProduits!: IFamilleProduit[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page = 1;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;
  alignment: Alignment = 'right';
  columnResizingMode = 'nextColumn';
  selectedRowIndex = -1;

  searchPanel = {
    visible: true,
    width: 350,
    placeholder: 'Search',
  };

  constructor(
    protected produitService: ProduitService,
    private familleProduitService: FamilleProduitService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  ngOnInit(): void {
    this.handleNavigation();
    this.registerChangeInProduits();
    this.familleProduitService.query().subscribe(res => {
      this.familleProduits = res.body ?? [];
    });
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  handleNavigation(): void {
    combineLatest([this.activatedRoute.data, this.activatedRoute.queryParamMap]).subscribe(([data, params]: [Data, ParamMap]) => {
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
    });
  }

  loadPage(page?: number, dontNavigate?: boolean): void {
    const pageToLoad: number = page || this.page || 1;

    this.produitService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe({
        next: (res: HttpResponse<IProduit[]>) => this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate),
        error: () => this.onError(),
      });
  }

  trackId(index: number, item: IProduit): number {
    return item.id!;
  }

  registerChangeInProduits(): void {
    this.eventSubscriber = this.eventManager.subscribe('produitListModification', () => this.loadPage());
  }

  getFamilleProduitNameById(familleId: number): string {
    if (this.familleProduits) {
      const familleProduit = this.familleProduits.find(r => r.id === familleId);
      return familleProduit ? familleProduit.nom! : 'Unknown';
    }
    return 'Unknown';
  }

  FamilleProduitNom = (rowData: any): string => {
    return this.getFamilleProduitNameById(rowData.familleId);
  };
  delete(produit: IProduit): void {
    const modalRef = this.modalService.open(ProduitDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.produit = produit;
  }

  onRowDblClick(e: any): void {
    this.router.navigate([`/produit/${e.data.id}/view`]);
  }

  selectedChanged(e: any): void {
    this.selectedRowIndex = e.component.getRowIndexByKey(e.selectedRowKeys[0]);
  }

  onDeleteBtnClicked(e: any, content: any): void {
    this.produit = e.data;
    this.modalService.open(content, { centered: true });
  }

  confirmDelete(content: any): void {
    this.produitService.delete(this.produit.id!).subscribe(() => {
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

  protected onSuccess(data: IProduit[] | null, headers: HttpHeaders, page: number, navigate: boolean): void {
    console.log('Produits reçus:', data); // Ajoutez cette ligne pour vérifier les produits
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    if (navigate) {
      this.router.navigate(['/produit'], {
        queryParams: {
          page: this.page,
          size: this.itemsPerPage,
          sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc'),
        },
      });
    }
    this.produits = data || [];
    this.ngbPaginationPage = this.page;
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page ?? 1;
  }
}
