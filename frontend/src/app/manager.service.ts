import {Injectable} from '@angular/core';
import {HttpClient, HttpRequest} from '@angular/common/http';

@Injectable({
    providedIn: 'root'
})
export class ManagerService {
    baseUrl = '/api';
    groupId = '';

    constructor(private http: HttpClient) {

    }

    getInProgressDepts( groupId) {
        const endpoint = `${this.baseUrl}/dept/${groupId}?status=in_progress`;
        return this.http.get(endpoint);
    }

    getCompleteDepts(groupId) {
        const endpoint = `${this.baseUrl}/dept/${groupId}?status=complete`;
        return this.http.get(endpoint);
    }

    getPersonGroups(){
        const endpoint = `${this.baseUrl}/person/groups`;
        return this.http.get(endpoint);
    }

    getWherePersonAdminGroups(){
        const endpoint = `${this.baseUrl}/groups`;
        return this.http.get(endpoint);
    }

    createGroup(name : string){
        const endpoint = `${this.baseUrl}/groups?name=${name}`;
        const req = new HttpRequest('POST', endpoint, {
            responseType: 'json'
        });
        return this.http.request(req);
    }

    renameGroup(groupId: string, name: string){
        const endpoint = `${this.baseUrl}/groups/${groupId}?newName=${name}`;
        const req = new HttpRequest('PUT', endpoint, {
            responseType: 'json'
        });
        return this.http.request(req);
    }

    addUserToGroup(groupId: string, userId: string){
        const endpoint = `${this.baseUrl}/groups/${groupId}?personId=${userId}`;
        const req = new HttpRequest('POST', endpoint, {
            responseType: 'json'
        });
        return this.http.request(req);
    }

    deleteUserFromGroup(groupId, personId){
        const endpoint = `${this.baseUrl}/groups/${groupId}/person/?personId=${personId}`;
        const req = new HttpRequest('DELETE', endpoint, {
            responseType: 'json'
        });
        return this.http.request(req);
    }

    deleteGroup(groupId){
        const endpoint = `${this.baseUrl}/groups/${groupId}`;
        const req = new HttpRequest('DELETE', endpoint, {
            responseType: 'json'
        });
        return this.http.request(req);
    }

    setVariableGroup(groupId){
        console.log(groupId);
        this.groupId = groupId;
    }

    getParticipantsOfGroup(groupId) {
        const endpoint = `${this.baseUrl}/groups/${groupId}/participants`;
        return this.http.get(endpoint);
    }

    requestNewDept(amount: string, comment:string, payerId:string, groupId){
        const endpoint = `${this.baseUrl}/dept/${groupId}`;
        
        const deptRequestDto = {
            amount: amount,
            comment: comment,
            payerId: payerId
        };
        console.log(deptRequestDto);
        const req = new HttpRequest('POST', endpoint, deptRequestDto, {
            responseType: 'json'
        });
        return this.http.request(req);
    }

    approveDeptAsSender(deptId: string){
        const endpoint = `${this.baseUrl}/dept/${deptId}/approve/payer`;
        const req = new HttpRequest('POST', endpoint, {
            responseType: 'json'
        });
        return this.http.request(req);
    }

    approveDeptAsReceiver(deptId: string){
        const endpoint = `${this.baseUrl}/dept/${deptId}/approve/receiver`;
        const req = new HttpRequest('POST', endpoint, {
            responseType: 'json'
        });
        return this.http.request(req);
    }

    getDeptsWherePayer(groupId: string, _status : string){
        const endpoint = `${this.baseUrl}/dept/${groupId}/payer?status=${_status}`;
        return this.http.get(endpoint);
    }

    getDeptsWhereReceiver(groupId: string, _status : string){
        const endpoint = `${this.baseUrl}/dept/${groupId}/receiver?status=${_status}`;
        return this.http.get(endpoint);
    }

    getAllDeptsShortenPayer() {
        const endpoint = `${this.baseUrl}/dept/statistics/payer`;
        return this.http.get(endpoint);
    }

    getAllDeptsShortenReceiver() {
        const endpoint = `${this.baseUrl}/dept/statistics/receiver`;
        return this.http.get(endpoint);
    }

    approveAllDeptsShortenAsPayer(deptIds : String[]) {
        const endpoint = `${this.baseUrl}/dept/approve/statistics/payer`;
        const req = new HttpRequest('POST', endpoint,deptIds, {
            responseType: 'json'
        });
        return this.http.request(req);
    }

    approveAllDeptsShortenAsReceiver(deptIds : String[]) {
        const endpoint = `${this.baseUrl}/dept/approve/statistics/receiver`;
        const req = new HttpRequest('POST', endpoint,deptIds, {
            responseType: 'json'
        });
        return this.http.request(req);
    }

    getAllUsers(){
        const endpoint = `${this.baseUrl}/person`;
        return this.http.get(endpoint);
    }


    deleteDept(deptId){
        const endpoint = `${this.baseUrl}/dept/${deptId}/del`;
        const req = new HttpRequest('DELETE', endpoint, {
            responseType: 'json'
        });
        return this.http.request(req);
    }

}
