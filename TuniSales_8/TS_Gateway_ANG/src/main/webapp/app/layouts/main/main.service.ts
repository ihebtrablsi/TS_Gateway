import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class MainService {
  scrWidth: number;
  scrHeight: number;

  constructor() {
    this.scrWidth = window.innerWidth;
    this.scrHeight = window.innerHeight;
  }
}
