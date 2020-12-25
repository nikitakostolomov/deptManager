import {Component, ElementRef, OnInit, Renderer2, ViewChild} from '@angular/core';
import {ManagerService} from '../manager.service';
import {FormBuilder, Validators} from "@angular/forms";
import {NotifierService} from "angular-notifier";
import {FormControl, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-group-managment',
  templateUrl: './group-managment.component.html',
  styleUrls: ['./group-managment.component.css']
})
export class GroupManagmentComponent implements OnInit {
  statusWorkflow = true;
  formRequestDept: FormGroup;
  submitted = false;
  application = [];
  participants = [];
  groupId;
  

  constructor(private managerService: ManagerService, private renderer: Renderer2) {
      this.formRequestDept = new FormGroup({
          name: new FormControl('', Validators.required),
      });
  }

  ngOnInit(): void {
      this.getData();
      this.getAllUsers();
  }

  get f() {
    return this.formRequestDept.controls;
}
  

  getData() {
     this.managerService.getWherePersonAdminGroups().subscribe((res: any) => {
       if (res) {
        this.application = res;
        console.log(res);
              }
            });
  }

  getAllUsers(){
    this.managerService.getAllUsers().subscribe((res: any) => {
      if (res) {
       this.participants = res;
       console.log(res);
             }
           });
  }

  
  togleButton(event) {
      const status = event.getAttribute('value');
      if (status === 'Открыть') {
          this.renderer.setAttribute(event, 'value', `Закрыть`);
      } else if (status === 'Закрыть') {
          this.renderer.setAttribute(event, 'value', 'Открыть');
      }
  }

    createGroup(){
      this.submitted = true;
      if (this.formRequestDept.invalid) {
        return;
    }
      this.managerService.createGroup(this.formRequestDept.value.name).subscribe((data: any)=> {
        if (data){
          this.getData();
        }
      });
    }
}

