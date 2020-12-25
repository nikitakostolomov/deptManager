import {Component, OnInit, Renderer2, ViewChild} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {ManagerService} from "../manager.service";
import {IDept} from '../_interfaces/IDept';

@Component({
    selector: 'app-archive-documents',
    templateUrl: './archive-documents.component.html',
    styleUrls: ['./archive-documents.component.css']
})
export class ArchiveDocumentsComponent implements OnInit {
    statusWorkflow = true;
    formRequestDept: FormGroup;
    application = [];
    participants = [];
    newDept:IDept;
    groupId;
    error ='';
    errorSender ='';
    errorReceiver ='';
    errorDeptDelete = '';
    submitted = false;


    constructor(private managerService: ManagerService, private renderer: Renderer2) {
        this.formRequestDept = new FormGroup({
            amount: new FormControl('',Validators.compose(
                [Validators.min(1), Validators.required])),
            comment: new FormControl('', Validators.required),
            payerId:  new FormControl('', Validators.required)

        });
    }

    ngOnInit(): void {
        this.groupId = this.managerService.groupId;
        this.error ='';
        this.errorSender ='';
        this.errorReceiver ='';
        this.errorDeptDelete = '';
        this.getDocuments(this.groupId);
    
    }

    get f() {
        return this.formRequestDept.controls;
    }

    resetErrors(){
        this.error ='';
        this.errorSender ='';
        this.errorReceiver ='';
        this.errorDeptDelete = '';
    }

    changeCategory(status) {
        this.resetErrors();
        if (status === 'in_progress') {
            this.statusWorkflow = true;
        } else if (status === 'complete') {
            this.statusWorkflow = false;
        }      
        this.getDocuments(this.groupId);
    }

    getDocuments(groupId) {
        if (this.statusWorkflow) {
            this.managerService.getInProgressDepts(groupId).subscribe((res: any) => {
                if (res) {
                    this.application = res;
            }
        });
            this.managerService.getParticipantsOfGroup(groupId).subscribe((res: any) => {
                if (res) {
                    this.participants = res;
                    console.log(res);
                }

            });
        } else {
            this.managerService.getCompleteDepts(groupId).subscribe((res: any) => {
                if (res) {
                    this.application = res;
                }
            });
            this.managerService.getParticipantsOfGroup(groupId).subscribe((res: any) => {
                if (res) {
                    this.participants = res;
                }

            });
        }
    }

    addNewDept(){
        this.resetErrors();
        this.submitted = true;
        if (this.formRequestDept.invalid) {
            return;
        }
        this.managerService.requestNewDept(this.f.amount.value,
            this.formRequestDept.value.comment,
            this.formRequestDept.value.payerId, this.groupId).subscribe(res => {
                if (res){
                    this.getDocuments(this.groupId);
                }
            }, error => {
                this.error = error;
            });
             
    }

    approveDeptAsSender(deptId) {
        this.resetErrors();
        this.managerService.approveDeptAsSender(deptId).subscribe(res => {
                if (res){
                    this.getDocuments(this.groupId);
                }
            }, error => {
                this.errorSender = error;
            });
    }

    approveDeptAsReceiver(deptId) {
        this.resetErrors();
        this.managerService.approveDeptAsReceiver(deptId).subscribe(res => {
                if (res){
                    this.getDocuments(this.groupId);
                }
            }, error => {
                    this.errorReceiver = error;
                });
    }

    getDeptsWherePayer(){
        this.resetErrors();
        let status;
        if (this.statusWorkflow) {
             status = 'in_progress'
        } else {
            status = 'complete'
        }
        this.managerService.getDeptsWherePayer(this.groupId, status).subscribe((res: any) => {
            if (res) {
                this.application = res;
        }
    });
}

    getDeptsWhereReceiver(){
        this.resetErrors();
        let status;
        if (this.statusWorkflow) {
             status = 'in_progress'
        } else {
            status = 'complete'
        }
        this.managerService.getDeptsWhereReceiver(this.groupId, status).subscribe((res: any) => {
            if (res) {
                this.application = res;
        }
    });
}
    deleteDept(deptId){
        this.resetErrors();
        console.log(deptId)
        this.managerService.deleteDept(deptId).subscribe(res => {
                if (res){
                    this.getDocuments(this.groupId);
                }
            }, error => {
                    this.errorDeptDelete = error;
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
}
