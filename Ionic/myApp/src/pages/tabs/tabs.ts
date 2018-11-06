import { Component } from '@angular/core';

import { TodoPage } from '../todo/todo';
import { InvWahlPage } from '../inv-wahl/inv-wahl';
import { HomePage } from '../home/home';
import { WelcomePage } from '../welcome/welcome'

@Component({
  templateUrl: 'tabs.html'
})
export class TabsPage {

  tab4Root = HomePage;
  tab2Root = TodoPage;
  tab3Root = InvWahlPage;
  tab1Root = WelcomePage;

  constructor() {

  }
}
