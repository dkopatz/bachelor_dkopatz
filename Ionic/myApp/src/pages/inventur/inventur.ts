import { Component } from '@angular/core';
import { NavController } from 'ionic-angular';
import {RemoteServiceProvider} from  '../../providers/remote-service/remote-service'
import {Http, Headers, RequestOptions} from '@angular/http';
import {InvKategoriePage} from '../inv-kategorie/inv-kategorie';
import 'rxjs/add/operator/map';

/**
 * Generated class for the InventurPage page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */

@IonicPage()
@Component({
  selector: 'page-inventur',
  templateUrl: 'inventur.html',
})
export class InventurPage {

      public objects: any;

  constructor(public navCtrl: NavController, public http:Http) {

    this.objects = null;
    this.http.get('http://131.173.32.33:8000/public/index.php/api/invdat').map((res) =>res.json()).subscribe(data => {
         this.objects = data;
    });
  }


  doRefresh(refresher) {
    this.objects = null;
    this.http.get('http://131.173.32.33:8000/public/index.php/api/invdat').map((res) =>res.json()).subscribe(data => {
         this.objects = data;
    });

    setTimeout(() => {
      refresher.complete();
    }, 2000);
  }
  update(){
    this.objects = null;
    this.http.get('http://131.173.32.33:8000/public/index.php/api/invdat').map((res) =>res.json()).subscribe(data => {
        this.objects = data;
     });
  }

  objectSelected(object) {
    this.navCtrl.push(InvKategoriePage, {object: object});
  }

}
