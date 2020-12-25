import {Component, ElementRef, OnInit, Input, Renderer2, ViewChild, Output, EventEmitter} from '@angular/core';
import {FormBuilder, Validators} from '@angular/forms';
import {ManagerService} from '../manager.service';
import {FormControl, FormGroup, Validator} from "@angular/forms";
import {NotifierService} from 'angular-notifier';
import {HttpService} from '../http.service';

@Component({
  selector: 'app-group-management-child',
  templateUrl: './group-management-child.component.html',
  styleUrls: ['./group-management-child.component.css']
})
export class GroupManagementChildComponent {
    @Input() application: any;
    @Input() participants: any;
    @Output() closeEvent = new EventEmitter();
  
    id: string;
    button: ElementRef;
    hide = false;
    submittedName = false;
    submittedPerson = false;
    sendingResultApproved = false;
    sendingResultRejected = false;
    formRequestDept: FormGroup;
    formAddUser: FormGroup;
    errorAdd = '';
    errorDelete = '';
    errorRename ='';
    errorDeleteGroup ='';
    

    constructor(
        private fb: FormBuilder,
        private managerService: ManagerService,
        private render: Renderer2,
        private notifier: NotifierService,
        private httpService: HttpService) {
          this.formRequestDept = new FormGroup({
            name: new FormControl('', Validators.required)
        }),
        this.formAddUser = new FormGroup({
          personId: new FormControl('', Validators.required)
      });
      this.errorAdd = '';
    this.errorDelete = '';
    this.errorRename ='';
    this.errorDeleteGroup ='';

    }

    outProcessing() {
        this.hide = false;
        this.render.removeAttribute(this.button, 'disabled');
    }

    inWork(buttonIn) {
        this.button = buttonIn;
        this.render.setAttribute(buttonIn, 'disabled', 'disabled');
        this.hide = true;
    }

    getAllUsers(){
      this.managerService.getAllUsers().subscribe((res: any) => {
        if (res) {
         this.participants = res;
               }
             });
    }


    deleteApplication() {
        this.closeEvent.emit();
    }

    renameGroup(groupId : string){
      this.submittedName = true;
        if (this.formRequestDept.invalid) {
            return;
        }
      this.errorRename = '';  
      this.managerService.renameGroup(groupId, this.formRequestDept.value.name).subscribe(
        data => {
                  
        },
        error => {
            this.errorRename = error;
        });
    }

    get f() {
      return this.formRequestDept.controls;
  }

  get add() {
    return this.formAddUser.controls;
}

    addUserToGroup(groupId : string){
      this.errorAdd = '';
      this.submittedPerson = true;
      if (this.formAddUser.invalid) {
          return;
      }
      this.managerService.addUserToGroup(groupId, this.formAddUser.value.personId).subscribe(
        data => {
                  
        },
        error => {
            this.errorAdd = error;
        });
    }

    deleteUserFromGroup(groupId, personId){
      this.errorDelete = '';
      this.managerService.deleteUserFromGroup(groupId, personId).subscribe(
        data => {
                  
        },
        error => {
            this.errorDelete = error;
        });
    }

    deleteGroup(groupId){
      this.errorDeleteGroup ='';
      this.managerService.deleteGroup(groupId).subscribe(
        data => {
                  
        },
        error => {
            this.errorDeleteGroup = error;
        });
    }

}

