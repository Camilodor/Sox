import { Component, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';


@Component({
  selector: 'app-pagina',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './pagina.component.html',
  styleUrl: './pagina.component.css'
})
export class PaginaComponent implements OnInit {
  constructor() { }
  ngOnInit(): void {

  }
}
