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

function refreshTable(events) {
    console.log(events);
    var table = document.getElementById("historyTable");
    var newTable = document.createElement('table');
    newTable.appendChild(table.getElementsByTagName("tr")[0]);
    console.log(newTable.innerHTML);
    for (var i = 0; i < events.length; i++) {
        var evt = events[i];
        var tr = document.createElement('tr');
        var td1 = document.createElement('td');
        var text1 = document.createTextNode(evt.uuid);
        td1.appendChild(text1);
        var td2 = document.createElement('td');
        var text2 = document.createTextNode(evt.resource.id);
        td2.appendChild(text2);
        var td3 = document.createElement('td');
        var text3 = document.createTextNode(evt.resource.description);
        td3.appendChild(text3);
        var td4 = document.createElement('td');
        var text4 = document.createTextNode(evt.client.login);
        td4.appendChild(text4);
        var td5 = document.createElement('td');
        var text5 = document.createTextNode(evt.startDate);
        td5.appendChild(text5);
        var td6 = document.createElement('td');
        var text6 = document.createTextNode(evt.endDate);
        td6.appendChild(text6);
        tr.appendChild(td1);
        tr.appendChild(td2);
        tr.appendChild(td3);
        tr.appendChild(td4);
        tr.appendChild(td5);
        tr.appendChild(td6);
        newTable.appendChild(tr);
    }
    table.innerHTML = newTable.innerHTML;
}

function filterTable() {
    console.log('filtering');
    var xhttp = new XMLHttpRequest();
    var url = "Filtering";
    var column = document.getElementById("filterForm:filtering").value;
    var pattern = document.getElementById("filterForm:filterinPattern").value;
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            var events = JSON.parse(xhttp.response);
            refreshTable(events);
        }
    }
    url += '?column=' + column + '&pattern=' + pattern;
    xhttp.open("GET", url, true);
    xhttp.send();
}

// if (loans) {
//     var table = document.getElementById("historyTable");
//     var tr = table.getElementsByTagName("tr");
//     // console.log('txtValue: ', txtValue);
//     // console.log('filter: ', filter);
//
//     for (i = 0; i < tr.length; i++) {
//         var td = tr[i].getElementsByTagName("td")[column];
//         if (td) {
//             txtValue = td.textContent || td.innerText;
//             // console.log('td.textContent: ', td.textContent);
//             // console.log('td.innerText: ', td.innerText);
//             if (txtValue.toUpperCase().indexOf(filter) > -1) {
//                 tr[i].style.display = "";
//             } else {
//                 tr[i].style.display = "none";
//             }
//         }
//     }


// if (safeBoxes) {
//     var table = document.getElementById("historyTable2");
//     var tr = table.getElementsByTagName("tr");
//     // console.log('txtValue: ', txtValue);
//     // console.log('filter: ', filter);
//
//     for (i = 0; i < tr.length; i++) {
//         var td = tr[i].getElementsByTagName("td")[column];
//         if (td) {
//             pattern = td.textContent || td.innerText;
//             console.log('td.textContent: ', td.textContent);
//             console.log('td.innerText: ', td.innerText);
//             if (pattern.toUpperCase().indexOf(filter) > -1) {
//                 tr[i].style.display = "";
//             } else {
//                 tr[i].style.display = "none";
//             }
//         }
//     }
// }
// }

function paging() {
    var url = "Paging";
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            var events = JSON.parse(xhttp.response);
            refreshTable(events);
        }
    }
    var pageNo = document.getElementById('pagingForm:pageNo').value;
    var pageSize = document.getElementById('pagingForm:pageSize').value;
    url += '?pageSize=' + pageSize + '&pageNo=' + pageNo;
    xhttp.open("GET", url, true);
    xhttp.send();
}
