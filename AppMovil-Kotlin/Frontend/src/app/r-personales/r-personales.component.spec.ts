import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RPersonalesComponent } from './r-personales.component';

describe('RPersonalesComponent', () => {
  let component: RPersonalesComponent;
  let fixture: ComponentFixture<RPersonalesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RPersonalesComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RPersonalesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
