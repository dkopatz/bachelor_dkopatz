import { NgModule } from '@angular/core';
import { IonicPageModule } from 'ionic-angular';
import { InvItemsPage } from './inv-items';

@NgModule({
  declarations: [
    InvItemsPage,
  ],
  imports: [
    IonicPageModule.forChild(InvItemsPage),
  ],
})
export class InvItemsPageModule {}
