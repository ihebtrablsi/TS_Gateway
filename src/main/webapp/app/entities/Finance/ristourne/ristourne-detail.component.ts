import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRistourne } from 'app/shared/model/Finance/ristourne.model';

@Component({
  selector: 'jhi-ristourne-detail',
  templateUrl: './ristourne-detail.component.html',
})
export class RistourneDetailComponent implements OnInit {
  ristourne: IRistourne | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ristourne }) => (this.ristourne = ristourne));
  }

  previousState(): void {
    window.history.back();
  }
}
