import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IIncentive } from 'app/shared/model/Finance/incentive.model';

@Component({
  selector: 'jhi-incentive-detail',
  templateUrl: './incentive-detail.component.html',
})
export class IncentiveDetailComponent implements OnInit {
  incentive: IIncentive | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ incentive }) => (this.incentive = incentive));
  }

  previousState(): void {
    window.history.back();
  }
}
