import { Component } from '@angular/core';
import { NavController, NavParams} from 'ionic-angular';
import {RemoteServiceProvider} from  '../../providers/remote-service/remote-service'
import {Http, Headers, RequestOptions} from '@angular/http';
import { ActionSheetController } from 'ionic-angular';
import { InvItemsPage } from '../inv-items/inv-items';
import 'rxjs/add/operator/map';

/**
 * Generated class for the InvKategoriePage page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */

@IonicPage()
@Component({
  selector: 'page-inv-kategorie',
  templateUrl: 'inv-kategorie.html',
})
export class InvKategoriePage {

  object: any;
  public objects: any;
  date: any;

  constructor(public navCtrl: NavController,
    public navParams: NavParams,
    public http:Http,
    public actionSheetCtrl: ActionSheetController) {
    this.object = navParams.get('object');

    let TIME_IN_MS = 800;
    let hideFooterTimeout = setTimeout( () => {
      this.objects = null;
      this.http.get('http://131.173.32.33:8000/public/index.php/api/inv/"' + this.object.Datum + '"').map((res) =>res.json()).subscribe(data => {
          this.objects = data;
      });
      console.log(this.objects)
    }, TIME_IN_MS);
    this.date = this.object.Datum;
  }

//(click)="nextSection()">
  update(){
    this.objects = null;
    this.http.get('http://131.173.32.33:8000/public/index.php/api/inv"' + this.object.Datum + '"').map((res) =>res.json()).subscribe(data => {
        this.objects = data;
     });
  }

  kategorieSelected(object) {
    object.Datum = this.date;
    this.navCtrl.push(InvItemsPage, {object: object});
  }

  AlleSelected(object) {
    object.Datum = this.date;
    object.Kategorie = "Alle";
    this.navCtrl.push(InvItemsPage, {object: object});
  }

}
