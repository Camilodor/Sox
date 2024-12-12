import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NavgestionarComponent } from './navgestionar.component';

describe('NavgestionarComponent', () => {
  let component: NavgestionarComponent;
  let fixture: ComponentFixture<NavgestionarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NavgestionarComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NavgestionarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
