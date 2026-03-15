import {
   BrowserModule,
   bootstrapApplication,
} from '@angular/platform-browser';

import { enableProdMode, importProvidersFrom } from '@angular/core';
import { environment } from './environments/environment';
import {
   provideHttpClient,
   withInterceptorsFromDi,
} from '@angular/common/http';
import { SessaoStore } from './app/features/pautas/sessao-store';
import { PautaStore } from './app/features/pautas/pauta-store';
import { VotacaoStore } from './app/features/votacao/votacao-store';
import { AssociadoStore } from './app/features/associados/associado-store';
import { TitleService } from './app/core/title.service';
import { LoadingService } from './app/core/loading.service';
import { DateFormatPipe } from './app/shared/pipes/date-format.pipe';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ReactiveFormsModule } from '@angular/forms';
import { AppRoutingModule } from './app/app-routing.module';
import { AppComponent } from './app/app.component';

if (environment.production) {
   enableProdMode();
}

bootstrapApplication(AppComponent, {
   providers: [
      importProvidersFrom(
         BrowserModule,
         BrowserAnimationsModule,
         ReactiveFormsModule,
         AppRoutingModule,
      ),
      provideHttpClient(withInterceptorsFromDi()),
      SessaoStore,
      PautaStore,
      VotacaoStore,
      AssociadoStore,
      TitleService,
      LoadingService,
      DateFormatPipe,
   ],
}).catch((err) => console.error(err));
