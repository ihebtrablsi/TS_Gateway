import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { SessionStorageService } from 'ngx-webstorage';

import { LANGUAGES } from 'app/core/language/language.constants';
import { AccountService } from 'app/core/auth/account.service';
import { LoginModalService } from 'app/core/login/login-modal.service';
import { LoginService } from 'app/core/login/login.service';
import { ProfileService } from 'app/layouts/profiles/profile.service';
import { SidenavService } from 'app/layouts/sidenav/sidenav.service';
import { Authority } from 'app/shared/constants/authority.constants';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { MainService } from 'app/layouts/main/main.service';
import { Account } from 'app/core/user/account.model';
import { Subscription } from 'rxjs';

interface SideNavToggle {
  screenWidth: number;
  collapsed: boolean;
}
interface MenuItem {
  // label ,label1,label2,label1: string;
  route: string;
  icon: string;
  translationKey: string;
  children?: MenuItem[];
}

@Component({
  selector: 'jhi-sidenav',
  templateUrl: './sidenav.component.html',
  styleUrls: ['./sidenav.scss'],
})
export class SidenavComponent implements OnInit {
  account: Account | null = null;
  authSubscription?: Subscription;

  inProduction?: boolean;
  isNavbarCollapsed = true;
  languages = LANGUAGES;
  swaggerEnabled?: boolean;
  // version: string;

  Authority = Authority;

  constructor(
    private loginService: LoginService,
    private languageService: JhiLanguageService,
    private sessionStorage: SessionStorageService,
    private mainService: MainService,
    protected modalService: NgbModal,

    private accountService: AccountService,
    private loginModalService: LoginModalService,

    private profileService: ProfileService,
    private sidenavService: SidenavService,
    private router: Router
  ) {
    // this.version = VERSION ? (VERSION.toLowerCase().startsWith('v') ? VERSION : 'v' + VERSION) : '';
    this.sidenavService.currentNav = router.url.split('/')[1] || 'tableaubord';
  }

  ngOnInit(): void {
    this.accountService.getAuthenticationState().subscribe(account => (this.account = account));
    this.profileService.getProfileInfo().subscribe(profileInfo => {
      this.inProduction = profileInfo.inProduction;
      this.swaggerEnabled = profileInfo.swaggerEnabled;
    });
  }

  changeLanguage(languageKey: string): void {
    this.sessionStorage.store('locale', languageKey);
    this.languageService.changeLanguage(languageKey);
  }

  set currentNav(value: string) {
    this.sidenavService.currentNav = value;
  }

  get currentNav(): string {
    return this.sidenavService.currentNav;
  }

  set selectedChildButton(value: string) {
    this.sidenavService.selectedChildButton = value;
  }

  get selectedChildButton(): string {
    return this.sidenavService.selectedChildButton;
  }

  set selectedParentButton(value: string) {
    this.sidenavService.selectedParentButton = value;
  }

  get selectedParentButton(): string {
    return this.sidenavService.selectedParentButton;
  }

  get scrWidth(): number {
    return this.mainService.scrWidth;
  }

  get scrHeight(): number {
    return this.mainService.scrHeight;
  }

  collapseNavbar(): void {
    this.isNavbarCollapsed = true;
  }

  isAuthenticated(): boolean {
    return this.accountService.isAuthenticated();
  }

  hasAnyAuthority(authorities: string[] | string): boolean {
    return this.accountService.hasAnyAuthority(authorities);
  }

  login(): void {
    this.loginModalService.open();
  }

  logout(): void {
    this.collapseNavbar();
    this.loginService.logout();
    this.router.navigate(['']);
  }

  toggleNavbar(): void {
    this.isNavbarCollapsed = !this.isNavbarCollapsed;
  }

  getImageUrl(): string {
    return this.isAuthenticated() ? this.accountService.getImageUrl() : '';
  }
}
