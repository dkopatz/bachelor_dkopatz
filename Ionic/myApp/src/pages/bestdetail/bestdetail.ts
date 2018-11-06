import { Component } from '@angular/core';
import { NavController, NavParams} from 'ionic-angular';
import {RemoteServiceProvider} from  '../../providers/remote-service/remote-service'
import {Http, Headers, RequestOptions} from '@angular/http';
import { ActionSheetController } from 'ionic-angular';
import { StandPage } from '../stand/stand';
import 'rxjs/add/operator/map';
/**
 * Generated class for the BestdetailPage page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */

@IonicPage()
@Component({
  selector: 'page-bestdetail',
  templateUrl: 'bestdetail.html',
})
export class BestdetailPage {

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
      this.http.get('http://131.173.32.33:8000/public/index.php/api/best/"' + this.object.Datum + '"').map((res) =>res.json()).subscribe(data => {
          this.objects = data;
      });
      console.log(this.objects)
    }, TIME_IN_MS);
    this.date = this.object.Datum;
  }

//(click)="nextSection()">
  update(){
    this.objects = null;
    this.http.get('http://131.173.32.33:8000/public/index.php/api/best"' + this.object.Datum + '"').map((res) =>res.json()).subscribe(data => {
        this.objects = data;
     });
  }

  standSelected(object) {
    object.Datum = this.date;
    this.navCtrl.push(StandPage, {object: object});
  }
  //ionViewDidLoad() {
    //console.log('ionViewDidLoad BestdetailPage');
  //}

  menu(object){
    const actionSheet = this.actionSheetCtrl.create({
      title: 'Welche Stände willst du bestücken?',
      buttons: [
        {
          text: 'Alle',
          handler: () => {
            object.Datum = this.date;
            object.Stand_nr = "Alle";
            this.navCtrl.push(StandPage, {object: object});
          }
        },{
          text: 'Norden',
          handler: () => {
            object.Datum = this.date;
            object.Stand_nr = "Norden";
            this.navCtrl.push(StandPage, {object: object});
          }
        },{
          text: 'Osten',
          handler: () => {
            object.Datum = this.date;
            object.Stand_nr = "Osten";
            this.navCtrl.push(StandPage, {object: object});
          }
        },{
          text: 'Süden',
          handler: () => {
            object.Datum = this.date;
            object.Stand_nr = "Süden";
            this.navCtrl.push(StandPage, {object: object});
          }
        },{
          text: 'Westen',
          handler: () => {
            object.Datum = this.date;
            object.Stand_nr = "Westen";
            this.navCtrl.push(StandPage, {object: object});
          }
        },{
          text: 'Bier Caddy',
          handler: () => {
            object.Datum = this.date;
            object.Stand_nr = "Bier Caddy";
            this.navCtrl.push(StandPage, {object: object});
          }
        },{
          text: 'Cancel',
          role: 'cancel',
          handler: () => {
            console.log('Cancel clicked');
          }
        }
      ]
    });
    actionSheet.present();
  }
}
