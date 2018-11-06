import { Component } from '@angular/core';
import { NavController } from 'ionic-angular';

@Component({
  selector: 'page-about',
  templateUrl: 'about.html'
})
export class AboutPage {

  items:any[]
  constructor(public navCtrl: NavController) {
    this.items = [];
    for (let i = 0; i < 10; i++){
      this.items.push({text: 'Item ' + i, id: i});
    }
  }

  itemSelected(item){
    alert(item.text);
  }

}
