import { Component } from '@angular/core';
import { NavController } from 'ionic-angular';
import {RemoteServiceProvider} from  '../../providers/remote-service/remote-service'
import {Http, Headers, RequestOptions} from '@angular/http';
import {BestdetailPage} from '../bestdetail/bestdetail';
import 'rxjs/add/operator/map';




@Component({
  selector: 'page-home',
  templateUrl: 'home.html'
})
export class HomePage {

    public objects: any;


    constructor(public navCtrl: NavController, public http:Http){

      this.objects = null;
      this.http.get('http://131.173.32.33:8000/public/index.php/api/bestdat').map((res) =>res.json()).subscribe(data => {
           this.objects = data;
      });

    }

    doRefresh(refresher) {
      this.objects = null;
      this.http.get('http://131.173.32.33:8000/public/index.php/api/bestdat').map((res) =>res.json()).subscribe(data => {
           this.objects = data;
      });

      setTimeout(() => {
        refresher.complete();
      }, 2000);
    }

    update(){
      this.objects = null;
      this.http.get('http://131.173.32.33:8000/public/index.php/api/bestdat').map((res) =>res.json()).subscribe(data => {
          this.objects = data;
       });
    }

    objectSelected(object) {
      this.navCtrl.push(BestdetailPage, {object: object});
    }

}
