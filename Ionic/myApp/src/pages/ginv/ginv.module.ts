import { NgModule } from '@angular/core';
import { IonicPageModule } from 'ionic-angular';
import { GinvPage } from './ginv';

@NgModule({
  declarations: [
    GinvPage,
  ],
  imports: [
    IonicPageModule.forChild(GinvPage),
  ],
})
export class GinvPageModule {}
