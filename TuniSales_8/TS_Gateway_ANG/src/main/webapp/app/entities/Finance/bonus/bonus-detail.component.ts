import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBonus } from 'app/shared/model/Finance/bonus.model';
import { IncentiveService } from 'app/entities/Finance/incentive/incentive.service';

@Component({
  selector: 'jhi-bonus-detail',
  templateUrl: './bonus-detail.component.html',
})
export class BonusDetailComponent implements OnInit {
  bonus: IBonus | null = null;
  incentiveNom = 'Inconnu';

  constructor(protected activatedRoute: ActivatedRoute, private incentiveService: IncentiveService) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bonus }) => {
      this.bonus = bonus;

      if (bonus?.incentiveId) {
        this.incentiveService.find(bonus.incentiveId).subscribe(res => {
          this.incentiveNom = res.body?.nom ?? 'Inconnu';
        });
      }
    });
  }

  previousState(): void {
    window.history.back();
  }
}
