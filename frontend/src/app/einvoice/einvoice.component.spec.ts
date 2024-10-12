import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EinvoiceComponent } from './einvoice.component';

describe('EinvoiceComponent', () => {
  let component: EinvoiceComponent;
  let fixture: ComponentFixture<EinvoiceComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [EinvoiceComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EinvoiceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
