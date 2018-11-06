import { Component, ViewChild } from '@angular/core';
import { IonicPage, NavController, NavParams } from 'ionic-angular';
import { TabsPage } from '../tabs/tabs';
import {Http, Headers, RequestOptions} from '@angular/http';
import 'rxjs/add/operator/map';
import CryptoJS from 'crypto-js';
/**
 * Generated class for the LoginPage page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */

@IonicPage()
@Component({
  selector: 'page-login',
  templateUrl: 'login.html',
})


export class LoginPage {


  public connection: any;
  userData = {"user_id":"", "username":"", "password":"" }

    @ViewChild('username') uname;
    @ViewChild('password') password;

  constructor(public navCtrl: NavController, public http:Http) {
    if(localStorage.getItem('userData')){
      var test;
      test = JSON.parse(localStorage.getItem('userData').slice(1,-1));//.slice(1,-1);

      this.http.get('http://131.173.32.33:8000/public/index.php/api/login/"' + test.username + '"/"' + test.password + '"').map((res) =>res.json()).subscribe(data => {
           //this.objects = data;
          this.connection = data;
          //console.log(this.connection);

      });
      let TIME_IN_MS = 800;
      let hideFooterTimeout = setTimeout( () => {
        if (this.connection == null){
          alert("Es gibt ein Verbindungsproblem!");
        } else if(this.connection.length != 1){
          alert("Nutzername oder Passwort falsch!");
        } else if(this.connection.length == 1){
          this.navCtrl.push(TabsPage);
        }
      }, TIME_IN_MS);
    }
  }

  ionViewDidLoad() {
    console.log('ionViewDidLoad LoginPage');
  }

  login(){

    this.connection = null;
    let hash = CryptoJS.SHA256(this.password.value).toString(CryptoJS.enc.Hex);

    this.http.get('http://131.173.32.33:8000/public/index.php/api/login/"' + this.uname.value + '"/"' + hash + '"').map((res) =>res.json()).subscribe(data => {
         //this.objects = data;
        this.connection = data;
        //console.log(this.connection);

    });
    let TIME_IN_MS = 800;
    let hideFooterTimeout = setTimeout( () => {
      if (this.connection == null){
        alert("Es gibt ein Verbindungsproblem!");
      } else if(this.connection.length != 1){
        alert("Nutzername oder Passwort falsch!");
      } else if(this.connection.length == 1){
        console.log(this.connection);
        localStorage.setItem('userData', JSON.stringify(this.connection));
        this.navCtrl.push(TabsPage);
      }
    }, TIME_IN_MS);



  }


}
