import { Component, OnInit } from '@angular/core';
import {OwnerService} from '../../../service/owner.service';
import {OwnerDto} from '../../../dto/owner/ownerDto';

@Component({
  selector: 'app-owner-list',
  templateUrl: './owner-list.component.html',
  styleUrls: ['./owner-list.component.scss']
})
export class OwnerListComponent implements OnInit {

  owners: OwnerDto[];

  constructor(private ownerService: OwnerService) { }

  ngOnInit(): void {
    this.ownerService.getAll()
      .subscribe((owners) => {
        this.owners = owners;
      });
  }

}
