import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'famille-produit',
        loadChildren: () =>
          import('./ProductInventory/famille-produit/famille-produit.module').then(m => m.ProductInventoryFamilleProduitModule),
      },
      {
        path: 'produit',
        loadChildren: () => import('./ProductInventory/produit/produit.module').then(m => m.ProductInventoryProduitModule),
      },
      {
        path: 'code',
        loadChildren: () => import('./ProductInventory/code/code.module').then(m => m.ProductInventoryCodeModule),
      },
      {
        path: 'mouvement-stock',
        loadChildren: () =>
          import('./ProductInventory/mouvement-stock/mouvement-stock.module').then(m => m.ProductInventoryMouvementStockModule),
      },
      {
        path: 'depot',
        loadChildren: () => import('./ProductInventory/depot/depot.module').then(m => m.ProductInventoryDepotModule),
      },
      {
        path: 'audit-stock',
        loadChildren: () => import('./ProductInventory/audit-stock/audit-stock.module').then(m => m.ProductInventoryAuditStockModule),
      },
      {
        path: 'ecart-stock',
        loadChildren: () => import('./ProductInventory/ecart-stock/ecart-stock.module').then(m => m.ProductInventoryEcartStockModule),
      },
      {
        path: 'promotion',
        loadChildren: () => import('./ProductInventory/promotion/promotion.module').then(m => m.ProductInventoryPromotionModule),
      },
      {
        path: 'client',
        loadChildren: () => import('./ClientSales/client/client.module').then(m => m.ClientSalesClientModule),
      },
      {
        path: 'point-de-vente',
        loadChildren: () => import('./ClientSales/point-de-vente/point-de-vente.module').then(m => m.ClientSalesPointDeVenteModule),
      },
      {
        path: 'action-commerciale',
        loadChildren: () =>
          import('./ClientSales/action-commerciale/action-commerciale.module').then(m => m.ClientSalesActionCommercialeModule),
      },
      {
        path: 'suivi-action',
        loadChildren: () => import('./ClientSales/suivi-action/suivi-action.module').then(m => m.ClientSalesSuiviActionModule),
      },
      {
        path: 'commande',
        loadChildren: () => import('./ClientSales/commande/commande.module').then(m => m.ClientSalesCommandeModule),
      },
      {
        path: 'ligne-commande',
        loadChildren: () => import('./ClientSales/ligne-commande/ligne-commande.module').then(m => m.ClientSalesLigneCommandeModule),
      },
      {
        path: 'incentive',
        loadChildren: () => import('./Finance/incentive/incentive.module').then(m => m.FinanceIncentiveModule),
      },
      {
        path: 'bonus',
        loadChildren: () => import('./Finance/bonus/bonus.module').then(m => m.FinanceBonusModule),
      },
      {
        path: 'panier-bonus',
        loadChildren: () => import('./ClientSales/panier-bonus/panier-bonus.module').then(m => m.ClientSalesPanierBonusModule),
      },
      {
        path: 'ristourne',
        loadChildren: () => import('./Finance/ristourne/ristourne.module').then(m => m.FinanceRistourneModule),
      },
      {
        path: 'facture',
        loadChildren: () => import('./Finance/facture/facture.module').then(m => m.FinanceFactureModule),
      },
      {
        path: 'paiement',
        loadChildren: () => import('./Finance/paiement/paiement.module').then(m => m.FinancePaiementModule),
      },
      {
        path: 'compte',
        loadChildren: () => import('./Finance/compte/compte.module').then(m => m.FinanceCompteModule),
      },
      {
        path: 'lettrage',
        loadChildren: () => import('./Finance/lettrage/lettrage.module').then(m => m.FinanceLettrageModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class TsGatewayAngEntityModule {}
