import { NgModule, ErrorHandler } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { IonicApp, IonicModule, IonicErrorHandler } from 'ionic-angular';
import { MyApp } from './app.component';
import { HttpModule } from '@angular/http';
import { HttpClientModule } from '@angular/common/http';

import { AboutPage } from '../pages/about/about';
import { ContactPage } from '../pages/contact/contact';
import { HomePage } from '../pages/home/home';
import { TabsPage } from '../pages/tabs/tabs';
import { LoginPage } from '../pages/login/login';
import { TodoPage } from '../pages/todo/todo';
import { WelcomePage } from '../pages/welcome/welcome';
import { BestdetailPage} from '../pages/bestdetail/bestdetail'
import { StandPage} from '../pages/stand/stand'
import { InventurPage} from '../pages/inventur/inventur'
import { InvKategoriePage} from '../pages/inv-kategorie/inv-kategorie'
import { InvItemsPage} from '../pages/inv-items/inv-items'
import { TododetailPage } from '../pages/tododetail/tododetail';
import { InvWahlPage} from '../pages/inv-wahl/inv-wahl'
import { GinvPage} from '../pages/ginv/ginv'
import { GinvdetailPage} from '../pages/ginvdetail/ginvdetail'
import { GinvStandPage} from '../pages/ginv-stand/ginv-stand'


import { StatusBar } from '@ionic-native/status-bar';
import { SplashScreen } from '@ionic-native/splash-screen';
import { RemoteServiceProvider } from '../providers/remote-service/remote-service';
import { AuthServiceProvider } from '../providers/auth-service/auth-service';



@NgModule({
  declarations: [
    MyApp,
    AboutPage,
    ContactPage,
    HomePage,
    LoginPage,
    TodoPage,
    WelcomePage,
    TabsPage,
    BestdetailPage,
    StandPage,
    InventurPage,
    InvKategoriePage,
    InvItemsPage,
    InvWahlPage,
    GinvPage,
    GinvdetailPage,
    GinvStandPage,
    TododetailPage
  ],
  imports: [
    BrowserModule,
    IonicModule.forRoot(MyApp),
    HttpModule,
    HttpClientModule
  ],
  bootstrap: [IonicApp],
  entryComponents: [
    MyApp,
    AboutPage,
    ContactPage,
    HomePage,
    LoginPage,
    TodoPage,
    WelcomePage,
    TabsPage,
    BestdetailPage,
    StandPage,
    InventurPage,
    InvKategoriePage,
    InvItemsPage,
    InvWahlPage,
    GinvPage,
    GinvdetailPage,
    GinvStandPage,
    TododetailPage
  ],
  providers: [
    StatusBar,
    SplashScreen,
    {provide: ErrorHandler, useClass: IonicErrorHandler},
    RemoteServiceProvider,
    AuthServiceProvider
  ]
})
export class AppModule {}
