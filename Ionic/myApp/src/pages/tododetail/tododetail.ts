import { Component } from '@angular/core';
import { NavController, NavParams } from 'ionic-angular';
import {RemoteServiceProvider} from  '../../providers/remote-service/remote-service'
import {Http, Headers, RequestOptions} from '@angular/http';
import { AlertController } from 'ionic-angular';
import 'rxjs/add/operator/map';

/**
 * Generated class for the TododetailPage page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */

 @Component({
   selector: 'page-tododetail',
   templateUrl: 'tododetail.html'
 })
 export class TododetailPage {

     public objects: any;
     public items: any;
     aufgabe: any;
     object: any;
     //public userDetails: {"user_id":"", "username":""};
     public userDetails: any;

     constructor(public navCtrl: NavController,
                  public navParams: NavParams,
                  private alertCtrl: AlertController,
                  public http:Http){
       this.object = navParams.get('object');
       this.objects = null;
       this.http.get('http://131.173.32.33:8000/public/index.php/api/todo/"'  + this.object.Datum + '"').map((res) =>res.json()).subscribe(data => {
            this.objects = data;
       });
       this.items = null;
       this.http.get('http://131.173.32.33:8000/public/index.php/api/todone/"' + this.object.Datum + '"').map((res) =>res.json()).subscribe(data => {
          this.items = data;
        });

     }


     doRefresh(refresher) {
       this.objects = null;
       this.http.get('http://131.173.32.33:8000/public/index.php/api/todo/"' + this.object.Datum + '"').map((res) =>res.json()).subscribe(data => {
           this.objects = data;
        });
        this.items = null;
        this.http.get('http://131.173.32.33:8000/public/index.php/api/todone/"' + this.object.Datum + '"').map((res) =>res.json()).subscribe(data => {
           this.items = data;
         });

       setTimeout(() => {
         refresher.complete();
       }, 2000);
     }

     fertig(object){
       const headers = new Headers();
       headers.append('Content-Type', 'application/json');

       const options = new RequestOptions({headers: headers});
       var hilf = object.Aufgabe;
       var data3 = JSON.parse(localStorage.getItem('userData'));
       var user = (data3[0].username);
      /* hilf = hilf.replace(/ü/g, "uuee" );
       hilf = hilf.replace(/Ü/g, "UUEE" );
       hilf = hilf.replace(/ö/g, "ooee" );
       hilf = hilf.replace(/Ö/g, "OOEE" );
       hilf = hilf.replace(/ä/g, "aaee" );
       hilf = hilf.replace(/Ä/g, "AAEE" );
       hilf = hilf.replace(/ß/g, "sszz" );


       user = user.replace(/ü/g, "uuee" );
       user = user.replace(/Ü/g, "UUEE" );
       user = user.replace(/ö/g, "ooee" );
       user = user.replace(/Ö/g, "OOEE" );
       user = user.replace(/ä/g, "aaee" );
       user = user.replace(/Ä/g, "AAEE" );
       user = user.replace(/ß/g, "sszz" );*/

       this.http.put('http://131.173.32.33:8000/public/index.php/api/todo/update/"' + this.object.Datum + '"/"' + hilf + '"/"' + user + '"',
       {'Status':'gelöst'}, options) //TODO
       .subscribe(
         (data:any) =>{console.log(data);}
       );
       let TIME_IN_MS = 800;
       let hideFooterTimeout = setTimeout( () => {
         this.objects = null;
         this.http.get('http://131.173.32.33:8000/public/index.php/api/todo/"' + this.object.Datum + '"').map((res) =>res.json()).subscribe(data => {
            this.objects = data;
          });
          this.items = null;
          this.http.get('http://131.173.32.33:8000/public/index.php/api/todone/"' + this.object.Datum + '"').map((res) =>res.json()).subscribe(data => {
             this.items = data;
           });
       }, TIME_IN_MS);
     }

     update(){
       this.objects = null;
       this.http.get('http://131.173.32.33:8000/public/index.php/api/todo/"' + this.object.Datum + '"').map((res) =>res.json()).subscribe(data => {
           this.objects = data;
        });
     }

     presentConfirm(aufgabe) {
  let alert = this.alertCtrl.create({
    title: 'Achtung',
    message: 'Willst du diese Aufgabe wirklich hinzufügen?',
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
        const headers = new Headers();
        headers.append('Content-Type', 'application/json');

        const options = new RequestOptions({headers: headers});
         var hilf = aufgabe;



        this.http.post('http://131.173.32.33:8000/public/index.php/api/todo/add/"' + this.object.Datum + '"/"' + hilf + '"',
        {}, options) //TODO
        .subscribe(
          (data:any) =>{console.log(data);}
        );
        this.aufgabe = "";
        let TIME_IN_MS = 800;
        let hideFooterTimeout = setTimeout( () => {
          this.objects = null;
          this.http.get('http://131.173.32.33:8000/public/index.php/api/todo/"' + this.object.Datum + '"').map((res) =>res.json()).subscribe(data => {
             this.objects = data;
           });
           this.items = null;
           this.http.get('http://131.173.32.33:8000/public/index.php/api/todone/"' + this.object.Datum + '"').map((res) =>res.json()).subscribe(data => {
              this.items = data;
            });
        }, TIME_IN_MS);

      }
    }
    ]
    });
    alert.present();
  }

 }
