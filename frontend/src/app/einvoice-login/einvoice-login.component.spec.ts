import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EinvoiceLoginComponent } from './einvoice-login.component';

describe('EinvoiceLoginComponent', () => {
  let component: EinvoiceLoginComponent;
  let fixture: ComponentFixture<EinvoiceLoginComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [EinvoiceLoginComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EinvoiceLoginComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
