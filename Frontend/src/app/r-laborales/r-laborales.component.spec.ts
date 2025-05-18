import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RLaboralesComponent } from './r-laborales.component';

describe('RLaboralesComponent', () => {
  let component: RLaboralesComponent;
  let fixture: ComponentFixture<RLaboralesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RLaboralesComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RLaboralesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
