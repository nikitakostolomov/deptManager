import {Component, OnInit, Renderer2, ViewChild} from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {ManagerService} from "../manager.service";
import {PaginationComponent} from "../pagination/pagination.component";
import {IDept} from '../_interfaces/IDept';

@Component({
  selector: 'app-my-dept-budget',
  templateUrl: './my-dept-budget.component.html',
  styleUrls: ['./my-dept-budget.component.css']
})
export class MyDeptBudgetComponent implements OnInit {
    iAmPayer = true;
    formRequestDept: FormGroup;
    application = [];
    participants = [];
    error = '';
    newDept:IDept;
    groupId;
    @ViewChild(PaginationComponent, {static: false})
    private pagination: PaginationComponent;

    constructor(private managerService: ManagerService, private renderer: Renderer2) {
        this.formRequestDept = new FormGroup({
            amount: new FormControl(''),
            comment: new FormControl(''),
            payerId:  new FormControl('')

        });
    }

    ngOnInit(): void {
        this.getAllDeptsWherePayer();
        this.error = '';
    }



    getAllDeptsWherePayer(){
      this.error = '';
      this.iAmPayer = true;
        this.managerService.getAllDeptsShortenPayer().subscribe((res: any) => {
            if (res) {
                this.application = res;
        }  
    });
}

    getAllDeptsWhereReceiver(){
      this.error = '';
      this.iAmPayer = false;
        this.managerService.getAllDeptsShortenReceiver().subscribe((res: any) => {
            if (res) {
                this.application = res;
        }
    });
}

    approveAllByRole(deptIds : String[]){
      this.error = '';
      if (this.iAmPayer){
        this.managerService.approveAllDeptsShortenAsPayer(deptIds).subscribe(res  =>{
          if (res){
            this.getAllDeptsWherePayer();
          }
        }, error => {
          this.error = error;
      });
      }
      else {
        this.managerService.approveAllDeptsShortenAsReceiver(deptIds).subscribe(res  =>{
          if (res){
            this.getAllDeptsWhereReceiver();
          }
        }, error => {
          this.error = error;
      });
      }
  //     this.managerService.approveAllDeptsShortenAsPayer(deptIds).subscribe((res: any) => {
  //         if (res) {
  //             this.application = res;
  //     }
  // });
  }


    togleButton(event) {
        const status = event.getAttribute('value');
        if (status === 'Открыть') {
            this.renderer.setAttribute(event, 'value', `Закрыть`);
        } else if (status === 'Закрыть') {
            this.renderer.setAttribute(event, 'value', 'Открыть');
        }
    }
}

