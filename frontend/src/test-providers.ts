import { EnvironmentProviders, Provider } from '@angular/core';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
const testProviders: Array<Provider | EnvironmentProviders> = [
	provideHttpClient(),
	provideHttpClientTesting(),
];
export default testProviders;