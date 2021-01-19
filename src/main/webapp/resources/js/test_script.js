function getHello() {
    var url = "Hello";
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            document.getElementById("test").innerHTML = this.responseText;
        }
    };
    if (confirm("Are you sure?")) {
        url += "?name=tak";
    } else {
        url += "?name=nie";
    }

    xhttp.open("GET", url, true);
    xhttp.send();
}

function newLoanValidation() {
    var form = document.getElementById('newLoanForm')
    var description = document.getElementById(form.id + ':newLoan_description');
    var value = document.getElementById(form.id + ':newLoan_value');
    var message = "";
    if (description.value.length === 0) {
        message += "Description is empty!\n";
    }
    if (parseFloat(value.value) <= 0 || value.value.length === 0) {
        message += "Loan must be grater than 0!";
    }
    if (message.length > 0) {
        alert(message);
        return false;
    }
    return true;
}

function newSafeBoxValidation() {
    var form = document.getElementById('newLoanForm')
    var description = document.getElementById(form.id + ':newSafeBox_description');
    var column = document.getElementById(form.id + ':newSafeBox_column');
    var row = document.getElementById(form.id + ':newSafeBox_row');
    var message = "";
    if (description.value.length === 0) {
        message += "Description is empty!\n";
    }
    if (parseInt(column.value) < 0 || column.value.length === 0) {
        message += "Column number must be positive\n";
    }
    if (parseInt(row.value) < 0 || row.value.length === 0) {
        message += "Row number must be positive";
    }
    if (message.length > 0) {
        alert(message);
        return false;
    }
    return true;
}

function refreshTable() {
    var column = 0;
    var loans = true;
    var safeBoxes = true;
    var menu = document.getElementById("filterForm:filtering");
    var option = menu.value;
    var txtValue = document.getElementById("filterForm:filterinPattern").value;
    var filter = txtValue.toUpperCase();
    switch (option) {
        case 'rID':
            column = 1;
            loans = true;
            safeBoxes = true;
            break;
        case 'rDesc':
            column = 2;
            loans = true;
            safeBoxes = true;
            break;
        case 'aNum':
            loans = true;
            safeBoxes = false;
            column = 3;
            break;
        case 'cLog':
            loans = false;
            safeBoxes = true;
            column = 4;
            break;
    }

    if (loans) {
        var table = document.getElementById("historyTable");
        var tr = table.getElementsByTagName("tr");
        // console.log('txtValue: ', txtValue);
        // console.log('filter: ', filter);

        for (i = 0; i < tr.length; i++) {
            var td = tr[i].getElementsByTagName("td")[column];
            if (td) {
                txtValue = td.textContent || td.innerText;
                // console.log('td.textContent: ', td.textContent);
                // console.log('td.innerText: ', td.innerText);
                if (txtValue.toUpperCase().indexOf(filter) > -1) {
                    tr[i].style.display = "";
                } else {
                    tr[i].style.display = "none";
                }
            }
        }
    }
    if (safeBoxes) {
        var table = document.getElementById("historyTable2");
        var tr = table.getElementsByTagName("tr");
        // console.log('txtValue: ', txtValue);
        // console.log('filter: ', filter);

        for (i = 0; i < tr.length; i++) {
            var td = tr[i].getElementsByTagName("td")[column];
            if (td) {
                txtValue = td.textContent || td.innerText;
                console.log('td.textContent: ', td.textContent);
                console.log('td.innerText: ', td.innerText);
                if (txtValue.toUpperCase().indexOf(filter) > -1) {
                    tr[i].style.display = "";
                } else {
                    tr[i].style.display = "none";
                }
            }
        }
    }
}

function paging() {
    var url = "Paging";
    var xhttp = new XMLHttpRequest();
    var table = document.getElementById('clientsForm:clientTable');
    var newTable = document.createElement('table');
    newTable.appendChild(table.getElementsByTagName("tr")[0]);

    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            var clients = JSON.parse(xhttp.response);
          //  alert(clients.length);
            for(var i=0; i<clients.length;i++) {
                var c = clients[i];
                var tr = document.createElement('tr');
                var td1 = document.createElement('td');
                var text1 = document.createTextNode(c.pid);
                td1.appendChild(text1);
                var td2 = document.createElement('td');
                var text2 = document.createTextNode(c.login);
                td2.appendChild(text2);
                var td3 = document.createElement('td');
                var text3 = document.createTextNode(c.name);
                td3.appendChild(text3);
                var td4 = document.createElement('td');
                var text4 = document.createTextNode(c.surname);
                td4.appendChild(text4);
                var td5 = document.createElement('td');
                var text5 = document.createTextNode(c.age);
                td5.appendChild(text5);
                var td6 = document.createElement('td');
                var text6 = document.createTextNode(c.blocked);
                td6.appendChild(text6);
                var td7 = document.createElement('td');
                var accTable = document.createElement('table');
                var accTr = document.createElement('tr');
                var th1 = document.createElement('th');
                var textTh1 = document.createTextNode('Account Number');
                var th2 = document.createElement('th');
                var textTh2 = document.createTextNode('State');
                var th3 = document.createElement('th');
                var textTh3 = document.createTextNode('');
                accTr.appendChild(th1);
                accTr.appendChild(th2);
                accTr.appendChild(th3);
                accTable.appendChild(accTr);
                for(var j=0; j < c.listOfAccounts.length; j++) {
                    var account = c.listOfAccounts[j];
                    var accTr1 = document.createElement('tr');
                    var accTd1 = document.createElement('td');
                    var accText1 = document.createTextNode(account.accountNumber);
                    accTd1.appendChild(accText1);
                    var accTd2 = document.createElement('td');
                    var accText2 = document.createTextNode(account.stateOfAccount + account.currency);
                    accTd2.appendChild(accText2);
                    var accTd3 = document.createElement('td');
                    accTd3.innerHTML = '<input type="submit" name="clientsForm:clientTable:0:j_idt58:0:j_idt67" value="Delete Account" onclick="return confirm(\'Are you sure?\')" />'
                    accTr1.appendChild(accTd1);
                    accTr1.appendChild(accTd2);
                    accTr1.appendChild(accTd3);
                    accTable.appendChild(accTr1);
                }
                td7.appendChild(accTable);
                var td8 = document.createElement('td');
                td8.innerHTML = '<input type="submit" name="clientsForm:clientTable:1:j_idt70" value="Delete" onclick="return confirm(\'Are you sure?\')" /><input type="submit" name="clientsForm:clientTable:1:j_idt71" value="Edit" /><input type="submit" name="clientsForm:clientTable:1:j_idt73" value="Block" />\n' +
                    '                        <br />';

                tr.appendChild(td1);
                tr.appendChild(td2);
                tr.appendChild(td3);
                tr.appendChild(td4);
                tr.appendChild(td5);
                tr.appendChild(td6);
                tr.appendChild(td7);
                tr.appendChild(td8);
                newTable.appendChild(tr);
            }
          //  alert(xhttp.response);
            table.innerHTML = newTable.innerHTML;
        }
    };
    var pageNo = document.getElementById('clientsForm:pageNo').value;
    var pageSize = document.getElementById('clientsForm:pageSize').value;
    url += '?pageSize=' + pageSize + '&pageNo=' + pageNo;
    xhttp.open("GET", url, true);
    xhttp.send();
}