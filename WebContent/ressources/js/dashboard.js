//On load
$(function () {
    // Default: hide edit mode
    $(".editMode").hide();

    // Click on "selectall" box
    $("#selectall").click(function () {
        $('.cb').prop('checked', this.checked);
    });

    // Click on a checkbox
    $(".cb").click(function () {
        if ($(".cb").length == $(".cb:checked").length) {
            $("#selectall").prop("checked", true);
        } else {
            $("#selectall").prop("checked", false);
        }
        if ($(".cb:checked").length != 0) {
            $("#deleteSelected").enable();
        } else {
            $("#deleteSelected").disable();
        }
    });

});


// Function setCheckboxValues
(function ($) {

    $.fn.setCheckboxValues = function (formFieldName, checkboxFieldName) {

        var str = $('.' + checkboxFieldName + ':checked').map(function () {
            return this.value;
        }).get().join();

        $(this).attr('value', str);

        return this;
    };

}(jQuery));

// Function toggleEditMode
(function ($) {

    $.fn.toggleEditMode = function () {
        if ($(".editMode").is(":visible")) {
            $(".editMode").hide();
            $("#editComputer").text("Edit");
        } else {
            $(".editMode").show();
            $("#editComputer").text("View");
        }
        return this;
    };

}(jQuery));


// Function delete selected: Asks for confirmation to delete selected computers, then submits it to the deleteForm
(function ($) {
    $.fn.deleteSelected = function () {
        if (confirm("Are you sure you want to delete the selected computers?")) {
            $('#deleteForm input[name=selection]').setCheckboxValues('selection', 'cb');
            $('#deleteForm').submit();
        }
    };
}(jQuery));


//Event handling
//Onkeydown
$(document).keydown(function (e) {

    switch (e.keyCode) {
        //DEL key
        case 46:
            if ($(".editMode").is(":visible") && $(".cb:checked").length != 0) {
                $.fn.deleteSelected();
            }
            break;
        //E key (CTRL+E will switch to edit mode)
        case 69:
            if (e.ctrlKey) {
                $.fn.toggleEditMode();
            }
            break;
    }
});
//=============Added by me=================
jQuery(document).ready(function() {
	   jQuery("#monFormulaire").validate({
	      rules: {
	         computerName:{
	            "required": true,
	         },
	         "introduced": {
	            usedFormat: true
	         },
	         "discontinued": {
		            usedFormat: true
		         },
		     "companyId":{
		    	 digits: true,
		    	 required:false
		     }
	   
	      }})});

jQuery.extend(jQuery.validator.messages, {
    required: "Ce champs est requis",
    remote: "votre message",
    url: "votre message",
    date: "votre message",
    dateISO: "votre message",
    number: "yawld l97ba",
    digits: "Seulement les chiffres sont autorisés",
    creditcard: "votre message",
    equalTo: "votre message",
    accept: "votre message",
    maxlength: jQuery.validator.format("votre message {0} caractéres."),
    minlength: jQuery.validator.format("votre message {0} caractéres."),
    rangelength: jQuery.validator.format("votre message  entre {0} et {1} caractéres."),
    range: jQuery.validator.format("votre message  entre {0} et {1}."),
    max: jQuery.validator.format("votre message  inférieur ou égal à {0}."),
    min: jQuery.validator.format("votre message  supérieur ou égal à {0}.")
  });

$.validator.addMethod(
	    "usedFormat",
	    function(value, element) {
	        // put your own logic here, this is just a (crappy) example
	        return value.match(/^\d{4}-\d{2}-\d{2}$/);
	    },
	    "Please enter a date in the format yyyy-mm-dd."
	);
//=============Added by me=================

function goToPage(index,size){
	window.location.href="window.location.href='dashboard?page='+${current}+'&size='+size";
}