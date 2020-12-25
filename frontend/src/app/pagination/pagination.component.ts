import {Component, EventEmitter, Input, OnInit, Output, OnChanges} from '@angular/core';

@Component({
    selector: 'app-pagination',
    templateUrl: './pagination.component.html',
    styleUrls: ['./pagination.component.css']
})
export class PaginationComponent implements OnChanges {
    @Input() numberPage: number;
    @Output()
    selected: EventEmitter<number> = new EventEmitter<number>();
    showPagination = true;
    arrayPage = [];
    numberThisPage = 0;
    isActive = [];
    disableNextButton = false;
    disablePrevButton = true;

    constructor() {
    }

    ngOnChanges() {
        this.countNumbersPage();
    }

    prevPage() {
        if (this.numberThisPage === 0) {
            this.disablePrevButton = true;
            return;
        }
        this.disablePrevButton = false;
        this.numberThisPage -= 1;
        this.clickNumberPage(this.numberThisPage);
    }

    nextPage() {
        if (this.numberThisPage >= this.numberPage - 1) {
            this.disableNextButton = true;
            return;
        }
        this.disablePrevButton = false;
        this.numberThisPage += 1;
        this.clickNumberPage(this.numberThisPage);
    }

    clickNumberPage(page) {
        this.numberThisPage = +page;
        this.isActive.fill(false);
        this.isActive[page] = true;
        this.selected.emit(this.numberThisPage);
    }

    countNumbersPage() {
        if (this.numberPage <= 1) {
            this.showPagination = false;
            return;
        }
        this.showPagination = true;
        for (let i = 0; i < this.numberPage; i++) {
            this.arrayPage[i] = i + 1;
        }
        this.isActive[this.numberThisPage] = true;
    }

    nullifyState() {
        this.numberThisPage = 0;
        this.isActive = [];
        this.countNumbersPage();
    }

    // countNumbers() {
    //     for (let i = 0; i < this.numberPage; i++) {
    //         this.arrayPage[i] = i + 1;
    //     }
    //     this.isActive[this.numberThisPage - 1] = true;
    //     if (this.numberPage < 10) {
    //         return;
    //     }
    //     this.isActive.fill(false);
    //     this.isActive[this.numberThisPage - 1] = true;
    //     for (let i = 1; i < (this.numberPage + 1); i++) {
    //         this.arrayPage[i] = '.';
    //     }
    //     this.isActive[this.numberThisPage] = true;
    //     // if (this.numberPage !== this.numberThisPage) {
    //     //     this.arrayPage[this.numberThisPage - 1] = this.numberThisPage;
    //     // }
    //     // if (this.numberPage !== this.numberThisPage) {
    //     console.log(this.arrayPage);
    // }
}
