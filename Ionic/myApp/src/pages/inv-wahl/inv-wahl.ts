import { Component } from '@angular/core';
import { IonicPage, NavController, NavParams } from 'ionic-angular';
import { InventurPage} from '../inventur/inventur'
import { GinvPage} from '../ginv/ginv'

/**
 * Generated class for the InvWahlPage page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */

@IonicPage()
@Component({
  selector: 'page-inv-wahl',
  templateUrl: 'inv-wahl.html',
})
export class InvWahlPage {

  constructor(public navCtrl: NavController, public navParams: NavParams) {
  }

  ionViewDidLoad() {
    console.log('ionViewDidLoad InvWahlPage');
  }

  GSelected(object){
    this.navCtrl.push(GinvPage);
  }
  LSelected(object){
    this.navCtrl.push(InventurPage);
  }
}
