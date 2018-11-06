import { NgModule } from '@angular/core';
import { IonicPageModule } from 'ionic-angular';
import { InventurPage } from './inventur';

@NgModule({
  declarations: [
    InventurPage,
  ],
  imports: [
    IonicPageModule.forChild(InventurPage),
  ],
})
export class InventurPageModule {}
