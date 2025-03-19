import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILettrage } from 'app/shared/model/Finance/lettrage.model';

@Component({
  selector: 'jhi-lettrage-detail',
  templateUrl: './lettrage-detail.component.html',
})
export class LettrageDetailComponent implements OnInit {
  lettrage: ILettrage | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ lettrage }) => (this.lettrage = lettrage));
  }

  previousState(): void {
    window.history.back();
  }
}
