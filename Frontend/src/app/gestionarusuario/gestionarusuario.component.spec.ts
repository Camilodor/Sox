import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GestionarusuarioComponent } from './gestionarusuario.component';

describe('GestionarusuarioComponent', () => {
  let component: GestionarusuarioComponent;
  let fixture: ComponentFixture<GestionarusuarioComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GestionarusuarioComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GestionarusuarioComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
