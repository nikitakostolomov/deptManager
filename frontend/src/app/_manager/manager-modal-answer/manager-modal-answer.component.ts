import {Component, ElementRef, Input, Renderer2, ViewChild, Output, EventEmitter} from '@angular/core';
import {FormBuilder, Validators} from '@angular/forms';
import {ManagerService} from '../../manager.service';
import {NotifierService} from 'angular-notifier';
import {HttpService} from '../../http.service';

@Component({
    selector: 'app-manager-modal-answer',
    templateUrl: './manager-modal-answer.component.html',
    styleUrls: ['./manager-modal-answer.component.css']
})
export class ManagerModalAnswerComponent {
    @Input() application: any;
    @Input() finance = false;
    @ViewChild('fileInput', {static: false})
    fileInput: ElementRef;
    @Output() closeEvent = new EventEmitter();
    form = this.fb.group({
        files: [[], Validators.required],
        commentManager: [null]
    });
    id: string;
    button: ElementRef;
    hide = false;
    sendingResultApproved = false;
    sendingResultRejected = false;

    constructor(
        private fb: FormBuilder,
        private managerService: ManagerService,
        private render: Renderer2,
        private notifier: NotifierService,
        private httpService: HttpService) {
    }

    outProcessing() {
        this.form.reset();
        this.hide = false;
        this.render.removeAttribute(this.button, 'disabled');
    }

    inWork(buttonIn) {
        this.button = buttonIn;
        this.render.setAttribute(buttonIn, 'disabled', 'disabled');
        this.hide = true;
    }

    sendGroupIdAndRedirect(groupId){
        this.managerService.setVariableGroup(groupId);
    }

    deleteApplication() {
        this.closeEvent.emit();
    }

   

}

