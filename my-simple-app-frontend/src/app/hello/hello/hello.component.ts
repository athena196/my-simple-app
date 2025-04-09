import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-hello',
  templateUrl: './hello.component.html',
  styleUrl: './hello.component.css'
})
export class HelloComponent implements OnInit {

  message: String = '';

  constructor(private http: HttpClient) { }

  ngOnInit(): void {

    this.http.get("http://eedpra-simple-app-part2.ap-southeast-3.elasticbeanstalk.com/api/hello", { responseType: 'text' })
      .subscribe(res => this.message = res);
  }

}
