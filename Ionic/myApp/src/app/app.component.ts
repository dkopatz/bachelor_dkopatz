import { Component } from '@angular/core';
import { Platform } from 'ionic-angular';
import { StatusBar } from '@ionic-native/status-bar';
import { SplashScreen } from '@ionic-native/splash-screen';

import { LoginPage } from '../pages/login/login';

@Component({
  templateUrl: 'app.html'
})
export class MyApp {
  rootPage:any = LoginPage;


  constructor(platform: Platform, statusBar: StatusBar, splashScreen: SplashScreen) {
    platform.ready().then(() => {
      statusBar.styleDefault();
      splashScreen.hide();

      // OneSignal Code start:
      // Enable to debug issues:
      // window["plugins"].OneSignal.setLogLevel({logLevel: 4, visualLevel: 4});

      //var notificationOpenedCallback = function(jsonData) {
        //alert('notificationOpenedCallback: ' + JSON.stringify(jsonData));
      //};

      //window["plugins"].OneSignal
        //.startInit("0c78c1df-013f-4dcc-bdb7-d6cb7eb2ee49", "444234738993")
        //.handleNotificationOpened(notificationOpenedCallback)
        //.endInit();
    });
  }
}
