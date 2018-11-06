import { NgModule } from '@angular/core';
import { IonicPageModule } from 'ionic-angular';
import { BestueckungPage } from './bestueckung';

@NgModule({
  declarations: [
    BestueckungPage,
  ],
  imports: [
    IonicPageModule.forChild(BestueckungPage),
  ],
})
export class BestueckungPageModule {}
