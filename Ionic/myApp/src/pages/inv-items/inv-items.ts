import { Component } from '@angular/core';
import { NavController, NavParams} from 'ionic-angular';
import {RemoteServiceProvider} from  '../../providers/remote-service/remote-service'
import {Http, Headers, RequestOptions} from '@angular/http';
import { ActionSheetController } from 'ionic-angular';
import { AlertController } from 'ionic-angular';
import 'rxjs/add/operator/map';

/**
 * Generated class for the InvItemsPage page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */

@IonicPage()
@Component({
  selector: 'page-inv-items',
  templateUrl: 'inv-items.html',
})
export class InvItemsPage {

  object: any;
  public objects: any;
  item = {"Anzahl":"", "Bemerkung":"" }

  constructor(public navCtrl: NavController,
    public navParams: NavParams,
    public http:Http,
    public actionSheetCtrl: ActionSheetController,
    public alertCtrl: AlertController) {
    this.object = navParams.get('object');

    let TIME_IN_MS = 800;
    let hideFooterTimeout = setTimeout( () => {
      this.objects = null;
      var hilf = this.object.Kategorie;
      hilf = hilf.replace("/", "~" );
      this.http.get('http://131.173.32.33:8000/public/index.php/api/inv/"' + this.object.Datum + '"/"' + hilf + '"').map((res) =>res.json()).subscribe(data => {
          this.objects = data;
      });
      console.log(this.objects)
    }, TIME_IN_MS);
  }

  update(){
    this.objects = null;
    this.http.get('http://131.173.32.33:8000/public/index.php/api/inv/"' + this.object.Datum + '"/"' + this.object.Kategorie + '"').map((res) =>res.json()).subscribe(data => {
        this.objects = data;
     });
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



              this.http.put('http://131.173.32.33:8000/public/index.php/api/inv/update/"' + object.Datum + '"/"' +  object.Produkt + '"/"' + object.Anzahl + '"/"' +  object.Bemerkung + '"',
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
              this.http.get('http://131.173.32.33:8000/public/index.php/api/inv/"' + this.object.Datum + '"/"' + this.object.Kategorie + '"').map((res) =>res.json()).subscribe(data => {
                  this.objects = data;
              });
              console.log(this.objects)
            }, TIME_IN_MS);
          }
        },{
          text: 'Artikel hinzufügen',
          handler: () => {
            this.showPrompt();
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

  showPrompt() {
    const prompt = this.alertCtrl.create({
      title: 'Artikel hinzufügen',
      message: "Welchen Artikel möchtest du dieser Kategorie hinzufügen?",
      inputs: [
        {
          name: 'produkt',
          placeholder: 'Produkt'
        },
        {
          name: 'anzahl',
          placeholder: 'Anzahl'
        },
        {
          name: 'einheit',
          placeholder: 'Einheit'
        },
        {
          name: 'bemerkung',
          placeholder: 'Bemerkung'
        },
      ],
      buttons: [
        {
          text: 'Cancel',
          handler: data => {
            console.log('Cancel clicked');
          }
        },
        {
          text: 'Speichern',
          handler: data => {
            const headers = new Headers();
            headers.append('Content-Type', 'application/json');

            const options = new RequestOptions({headers: headers});

            this.http.post('http://131.173.32.33:8000/public/index.php/api/inv/add/"' + this.object.Datum + '"/"' + this.object.Kategorie + '"/"' + data.produkt + '"/"' + data.anzahl + '"/"' +  data.einheit + '"/"' + data.bemerkung + '"',
            {}, options) //TODO
            .subscribe(
              (data:any) =>{console.log(data);}
            );
            let TIME_IN_MS = 800;
            let hideFooterTimeout = setTimeout( () => {
              this.objects = null;
              this.http.get('http://131.173.32.33:8000/public/index.php/api/inv/"' + this.object.Datum + '"/"' + this.object.Kategorie + '"').map((res) =>res.json()).subscribe(data => {
                 this.objects = data;
               });
            }, TIME_IN_MS);
          }
        }
      ]
    });
    prompt.present();
  }
}
