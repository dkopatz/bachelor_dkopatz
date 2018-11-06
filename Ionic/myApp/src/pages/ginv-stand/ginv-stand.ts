import { Component } from '@angular/core';
import { IonicPage, NavController, NavParams } from 'ionic-angular';
import {RemoteServiceProvider} from  '../../providers/remote-service/remote-service'
import {Http, Headers, RequestOptions} from '@angular/http';
import { AlertController } from 'ionic-angular';
import { ActionSheetController } from 'ionic-angular';

/**
 * Generated class for the GinvStandPage page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */

@IonicPage()
@Component({
  selector: 'page-ginv-stand',
  templateUrl: 'ginv-stand.html',
})
export class GinvStandPage {

  object: any;
  public objects: any;

  constructor(public navCtrl: NavController,
    public navParams: NavParams,
    private alertCtrl: AlertController,
    public actionSheetCtrl: ActionSheetController,
    public http:Http) {
    this.object = navParams.get('object');

    let TIME_IN_MS = 800;
    let hideFooterTimeout = setTimeout( () => {
      this.objects = null;
      this.http.get('http://131.173.32.33:8000/public/index.php/api/ginven/"' + this.object.Datum + '"/"' + this.object.Stand_nr + '"').map((res) =>res.json()).subscribe(data => {
          this.objects = data;
      });
      console.log(this.objects)
    }, TIME_IN_MS);
  }


//(click)="nextSection()">
  update(){
    this.objects = null;
    this.http.get('http://131.173.32.33:8000/public/index.php/api/ginven/"' + this.object.Datum + '"/"' + this.object.Stand_nr + '"').map((res) =>res.json()).subscribe(data => {
        this.objects = data;
     });
  }

  presentConfirm(aufgabe) {
let alert = this.alertCtrl.create({
 title: 'Achtung',
 message: 'Willst du diese Bestückung wirklich hinzufügen?',
 buttons: [
 {
     text: 'Cancel',
     role: 'cancel',
     handler: () => {
       console.log('Cancel clicked');
     }
 },
 {
   text: 'Absenden',
   handler: () => {
     this.objects.forEach((object) => {
        const headers = new Headers();
        headers.append('Content-Type', 'application/json');
        const options = new RequestOptions({headers: headers});
        console.log(options);


        this.http.put('http://131.173.32.33:8000/public/index.php/api/ginv/update/"' + this.object.Datum + '"/"' +  this.object.Stand_nr + '"/"' + object.Sorte + '"/"' +  object.Menge + '"',
            {}, options) //TODO
            .subscribe(
              (data:any) =>{console.log(data);});
      });

   }
 }
 ]
 });
 alert.present();
}


menu(test){
  const actionSheet = this.actionSheetCtrl.create({
    title: 'Menü',
    buttons: [
      {
        text: 'Inventur abschicken',
        handler: () => {



         this.objects.forEach((object) => {
            const headers = new Headers();
            headers.append('Content-Type', 'application/json');
            const options = new RequestOptions({headers: headers});
            console.log(options);


            this.http.put('http://131.173.32.33:8000/public/index.php/api/ginv/update/"' + this.object.Datum + '"/"' +  this.object.Stand_nr + '"/"' + object.Sorte + '"/"' +  object.Menge + '"',
                {}, options) //TODO
                .subscribe(
                  (data:any) =>{console.log(data);});
          });
        }
      },{
        text: 'Wiederherstellen',
        handler: () => {
          let TIME_IN_MS = 800;
          let hideFooterTimeout = setTimeout( () => {
            this.objects = null;
            this.http.get('http://131.173.32.33:8000/public/index.php/api/ginven/"' + this.object.Datum + '"/"' + this.object.Stand_nr + '"').map((res) =>res.json()).subscribe(data => {
                this.objects = data;
            });
            console.log(this.objects)
          }, TIME_IN_MS);
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
