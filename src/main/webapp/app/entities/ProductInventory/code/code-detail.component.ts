import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICode } from 'app/shared/model/ProductInventory/code.model';

@Component({
  selector: 'jhi-code-detail',
  templateUrl: './code-detail.component.html',
})
export class CodeDetailComponent implements OnInit {
  code: ICode | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ code }) => (this.code = code));
  }

  previousState(): void {
    window.history.back();
  }
}
