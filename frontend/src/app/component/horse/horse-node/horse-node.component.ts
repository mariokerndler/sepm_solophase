import {Component, Input} from '@angular/core';
import {HorseService} from '../../../service/horse.service';
import {NotificationService} from '../../../service/notification.service';
import {Router} from '@angular/router';
import {HorseDto} from '../../../dto/horse/horseDto';

@Component({
  selector: 'app-horse-node',
  templateUrl: './horse-node.component.html',
  styleUrls: ['./horse-node.component.scss']
})
export class HorseNodeComponent {

  @Input() horse: HorseDto;
  expanded = true;

  constructor(
    private horseService: HorseService,
    private notificationService: NotificationService,
    private router: Router
  ) { }

  onRowClick(id: number) {
    this.router.navigate(['/horses', id, 'detail'])
      .catch( (reason) => this.notificationService.notifyError(reason.toString()));
  }

  onToggleCollapse() {
    this.expanded = !this.expanded;
  }

  delete(id: number) {
    this.horseService.delete(id).subscribe(this.horse = null);
  }
}
