function getHello() {
    var url = "Hello";
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            document.getElementById("test").innerHTML = this.responseText;
        }
    };
    if(confirm("Are you sure?")) {
        url += "?name=tak";
    }
    else
    {
        url += "?name=nie";
    }

    xhttp.open("GET", url, true);
    xhttp.send();
}

function getHello() {
    var url = "Hello";
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            document.getElementById("test").innerHTML = this.responseText;
        }
    };
    if(confirm("Are you sure?")) {
        url += "?name=tak";
    }
    else
    {
        url += "?name=nie";
    }

    xhttp.open("GET", url, true);
    xhttp.send();
}

function newLoanValidation(){
    var description = document.getElementById('newLoan_description');
    var value = document.getElementById('newLoan_value');
    var message = "";
    if(description.innerText.length === 0) {
        message += "Description is empty!\n";
    }
    if(parseFloat(value.innerText) <= 0) {
        message += "Loan must be grater than 0!";
    }
    if(message.length > 0) {
        alert(message);
        return false;
    }
    return true;
}

function newSafeBoxValidation(){
    var description = document.getElementById('newSafeBox_description');
    var column = document.getElementById('newSafeBox_column');
    var row = document.getElementById('newSafeBox_row');
    var message = "";
    if(description.innerText.length === 0) {
        message += "Description is empty!\n";
    }
    if(parseInt(column.innerText) < 0) {
        message += "Column number must be positive";
    }
    if(parseInt(row.innerText) < 0) {
        message += "Row number must be positive";
    }
    if(message.length > 0) {
        alert(message);
        return false;
    }
    alert("DOBRZE");
    return true;
}