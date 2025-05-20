import { Component, HostListener, OnInit, Renderer2, RendererFactory2 } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { Router, ActivatedRouteSnapshot, NavigationEnd, NavigationError } from '@angular/router';
import { TranslateService, LangChangeEvent } from '@ngx-translate/core';

import { AccountService } from 'app/core/auth/account.service';
import { FindLanguageFromKeyPipe } from 'app/shared/language/find-language-from-key.pipe';
import { DatePipe } from '@angular/common';
import { MainService } from 'app/layouts/main/main.service';

@Component({
  selector: 'jhi-main',
  templateUrl: './main.component.html',
  styleUrls: ['main.scss'],
  providers: [DatePipe],
})
export class MainComponent implements OnInit {
  private renderer: Renderer2;
  myDate: any = Date.now();

  constructor(
    private datePipe: DatePipe,
    private mainService: MainService,
    private accountService: AccountService,
    private titleService: Title,
    private router: Router,
    private findLanguageFromKeyPipe: FindLanguageFromKeyPipe,
    private translateService: TranslateService,
    rootRenderer: RendererFactory2
  ) {
    setInterval(() => {
      this.myDate = Date.now();
    }, 1000);
    // this.myDate = this.datePipe.transform(this.myDate, 'yyyy-MM-dd hh:mm');
    this.renderer = rootRenderer.createRenderer(document.querySelector('html'), null);
  }

  @HostListener('window:resize', ['$event'])
  onResize(event: any): void {
    this.scrWidth = event.target.innerWidth;
    this.scrHeight = event.target.innerHeight;
  }
  get scrWidth(): number {
    return this.mainService.scrWidth;
  }
  set scrWidth(value: number) {
    this.mainService.scrWidth = value;
  }

  get scrHeight(): number {
    return this.mainService.scrHeight;
  }
  set scrHeight(value: number) {
    this.mainService.scrHeight = value;
  }
  // activated(comp: any, data: any): void {
  //   if (comp instanceof LeftmenuComponent) {
  //     comp.selectedParentButton = data.parent;
  //     comp.selectedChildButton = data.child;
  //   }
  // }
  ngOnInit(): void {
    // try to log in automatically
    this.accountService.identity().subscribe();

    this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd) {
        this.updateTitle();
      }
      if (event instanceof NavigationError && event.error.status === 404) {
        this.router.navigate(['/404']);
      }
    });

    this.translateService.onLangChange.subscribe((langChangeEvent: LangChangeEvent) => {
      this.updateTitle();

      this.renderer.setAttribute(document.querySelector('html'), 'lang', langChangeEvent.lang);
      this.updatePageDirection();
    });
  }
  private updatePageDirection(): void {
    this.renderer.setAttribute(
      document.querySelector('html'),
      'dir',
      this.findLanguageFromKeyPipe.isRTL(this.translateService.currentLang) ? 'rtl' : 'ltr'
    );
  }
  isAuthenticated(): boolean {
    return this.accountService.isAuthenticated();
  }
  private getPageTitle(routeSnapshot: ActivatedRouteSnapshot): string {
    let title: string = routeSnapshot.data && routeSnapshot.data['pageTitle'] ? routeSnapshot.data['pageTitle'] : '';
    if (routeSnapshot.firstChild) {
      title = this.getPageTitle(routeSnapshot.firstChild) || title;
    }
    return title;
  }

  private updateTitle(): void {
    let pageTitle = this.getPageTitle(this.router.routerState.snapshot.root);
    if (!pageTitle) {
      pageTitle = 'global.title';
    }
    this.translateService.get(pageTitle).subscribe(title => this.titleService.setTitle(title));
  }
}
