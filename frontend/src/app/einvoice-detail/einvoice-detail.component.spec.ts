import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EinvoiceDetailComponent } from './einvoice-detail.component';

describe('EinvoiceDetailComponent', () => {
  let component: EinvoiceDetailComponent;
  let fixture: ComponentFixture<EinvoiceDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [EinvoiceDetailComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EinvoiceDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
