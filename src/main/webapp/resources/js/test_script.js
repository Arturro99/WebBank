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
    var form = document.getElementById('newLoanForm')
    var description = document.getElementById(form.id +':newLoan_description');
    var value = document.getElementById(form.id +':newLoan_value');
    var message = "";
    if(description.value.length === 0) {
        message += "Description is empty!\n";
    }
    if(parseFloat(value.value) <= 0 || value.value.length === 0) {
        message += "Loan must be grater than 0!";
    }
    if(message.length > 0) {
        alert(message);
        return false;
    }
    return true;
}

function newSafeBoxValidation(){
    var form = document.getElementById('newLoanForm')
    var description = document.getElementById(form.id +':newSafeBox_description');
    var column = document.getElementById(form.id +':newSafeBox_column');
    var row = document.getElementById(form.id +':newSafeBox_row');
    var message = "";
    if(description.value.length === 0) {
        message += "Description is empty!\n";
    }
    if(parseInt(column.value) < 0 || column.value.length === 0) {
        message += "Column number must be positive\n";
    }
    if(parseInt(row.value) < 0 || row.value.length === 0) {
        message += "Row number must be positive";
    }
    if(message.length > 0) {
        alert(message);
        return false;
    }
    return true;
}