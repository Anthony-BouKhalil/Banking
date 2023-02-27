function giveWarning(allAccountsClosed) {
    if (allAccountsClosed == true) {
        return confirm("Are you sure? There are still open accounts!");
    } else {
        return confirm("Are you sure?");
    }
}

function giveOpsWarning(status="reopen", balance=0) {
    if (status = "close" && balance>0) {
        return confirm("Are you sure? There is still money in this account!");
    } else {
        return confirm("Are you sure?");
    }
}

function validateInput() {
    var amount = document.getElementById("amount").value;
    if (isNaN(amount)) {
        alert("Is not a number");
        return false;
    }
}

function changeLabel(transaction) {
    if (document.getElementById("transaction").value == "Deposit") {
        document.getElementById('amountLabel').innerHTML = 'Deposit money';
        document.getElementById('transferdiv').style.display="none";
        document.getElementById("amount").removeAttribute('max');
        console.log(document.getElementById("transaction").value);
    } else if (document.getElementById("transaction").value == "Withdraw") {
        document.getElementById("amount").max = document.getElementById('balance').innerHTML;
        document.getElementById('amountLabel').innerHTML = 'Withdraw money';
        document.getElementById('transferdiv').style.display="none";
        console.log(document.getElementById("transaction").value);
    } else if (document.getElementById("transaction").value == "Transfer") {
        document.getElementById("amount").max = document.getElementById('balance').innerHTML;
        document.getElementById('amountLabel').innerHTML = 'Transfer Amount: ';
        document.getElementById('transferdiv').style.display="block";
        console.log(document.getElementById("transaction").value);
    } 
}

function statementType(radioButton) {
    if (radioButton.value == "numberOfTransactions") {
        document.getElementById('numDiv').style.display="block";
        document.getElementById('dateDiv').style.display="none";     
    } else if (radioButton.value == "date") {
        document.getElementById('numDiv').style.display="none";
        document.getElementById('dateDiv').style.display="block";    
    }
}

function checkDate() {
    var startDt = document.getElementById("startDate").value;
    var endDt = document.getElementById("endDate").value;

    if ((new Date(startDt).getTime() > new Date(endDt).getTime())) {
        alert("The start date cannot be greater than the end date!");
        return false;
    }
}

function abc(startDate) {
    console.log(startDate.value);

    document.getElementById("endDate").min=startDate.value;
    var utc = new Date().toJSON.slice(0, 10).replace(/-/g, '-');

    document.getElementById("endDate").max=utc;
}