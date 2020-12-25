import {Component, ElementRef, OnInit, Renderer2, ViewChild} from '@angular/core';
import {ManagerService} from '../../manager.service';
import {FormBuilder, Validators} from "@angular/forms";
import {NotifierService} from "angular-notifier";

@Component({
    selector: 'app-manager-workflow',
    templateUrl: './manager-workflow.component.html',
    styleUrls: ['./manager-workflow.component.css']
})
export class ManagerWorkflowComponent implements OnInit {
    @ViewChild('buttonInWork', {static: false})
    private buttonInWork: ElementRef;
    page = 0;
    inWorking = false;
    showMoreButton = false;
    incomingApplications = [];
    errorChoiceEmitent = false;
    errorChoiceCategory = false;
    sendingFile = false;
    form = this.fb.group({
        messageManager: [''],
        emitentId: ['Выбрать эмитента', Validators.required],
    });
    emitentForSelect: any;

    constructor(private managerService: ManagerService,
                private notifer: NotifierService,
                private fb: FormBuilder, private renderer: Renderer2) {
    }

    ngOnInit() {
      
        this.getData();
    }

    inOutWork() {
        this.inWorking = !this.inWorking;
    }

    deleteApplication(target) {
        this.renderer.setStyle(target, 'display', 'none');
    }

    getData() {
        const offset = this.page * 20;
        this.managerService.getPersonGroups().subscribe((data: any) => {
            this.incomingApplications = this.incomingApplications.concat(data);
            console.log(this.incomingApplications);
        });

    }


}
