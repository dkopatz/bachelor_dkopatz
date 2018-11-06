import { Component } from '@angular/core';
import { IonicPage, NavController, NavParams } from 'ionic-angular';
import {RemoteServiceProvider} from  '../../providers/remote-service/remote-service'
import {Http, Headers, RequestOptions} from '@angular/http';
import {GinvdetailPage} from '../ginvdetail/ginvdetail';
import 'rxjs/add/operator/map';
/**
 * Generated class for the GinvPage page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */

@IonicPage()
@Component({
  selector: 'page-ginv',
  templateUrl: 'ginv.html',
})
export class GinvPage {

  public objects: any;


  constructor(public navCtrl: NavController, public http:Http){

    this.objects = null;
    this.http.get('http://131.173.32.33:8000/public/index.php/api/ginvdat').map((res) =>res.json()).subscribe(data => {
         this.objects = data;
    });

  }

  doRefresh(refresher) {
    this.objects = null;
    this.http.get('http://131.173.32.33:8000/public/index.php/api/ginvdat').map((res) =>res.json()).subscribe(data => {
         this.objects = data;
    });

    setTimeout(() => {
      refresher.complete();
    }, 2000);
  }

  update(){
    this.objects = null;
    this.http.get('http://131.173.32.33:8000/public/index.php/api/ginvdat').map((res) =>res.json()).subscribe(data => {
        this.objects = data;
     });
  }

  objectSelected(object) {
    this.navCtrl.push(GinvdetailPage, {object: object});
  }


}
