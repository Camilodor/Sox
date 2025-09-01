import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TiposRolComponent } from './tipos-rol.component';

describe('TiposRolComponent', () => {
  let component: TiposRolComponent;
  let fixture: ComponentFixture<TiposRolComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TiposRolComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TiposRolComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
