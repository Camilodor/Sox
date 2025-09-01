import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NavgoComponent } from './navgo.component';

describe('NavgoComponent', () => {
  let component: NavgoComponent;
  let fixture: ComponentFixture<NavgoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NavgoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NavgoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
