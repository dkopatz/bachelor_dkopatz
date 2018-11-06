import { NgModule } from '@angular/core';
import { IonicPageModule } from 'ionic-angular';
import { TododetailPage } from './tododetail';

@NgModule({
  declarations: [
    TododetailPage,
  ],
  imports: [
    IonicPageModule.forChild(TododetailPage),
  ],
})
export class TododetailPageModule {}
