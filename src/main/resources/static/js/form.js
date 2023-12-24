$(function() {
	var $applyForm = $("#applyForm");
	$applyForm.validate({
		rules : {

			fullName : {
				required : true,
				lettersonly : true
			},
			dob : {
				required : true,
			},
			gender:{
				required : true,
			},
			fatherName:{
				required : true,
				lettersonly : true
			},
			email : {
				required : true,
				space : true,
				email : true
			},			
			mobNo : {
				required : true,
				space : true,
				numericOnly : true,
				minlength : 10,
				maxlength : 12

			},
			address : {
				required : true,
				all : true

			},
			district:{
				required : true
			},
			state:{
				required : true
			},
			panNum:{
				required : true
			},
			pan:{
				required : true
			},
			photo:{
				required : true
			}
			
			

		},
		messages : {
			fullName : {
				required : "name required",
				lettersonly : "invalid name"
			},
			dob : {
				required : "dob required",
			},
			gender:{
				required : "gender required",
			},
			fatherName:{
				required : "fathername required",
				lettersonly : "invalid name"
			},
			email : {
				required : "email required",
				space : "space not allowed",
				email : "invalid email"
			},			
			mobNo : {
				required  : "mobno required",
				space :  "space not allowed",
				numericOnly : "invalid ",
				minlength : "min 10 digit",
				maxlength : "max 12 digit"

			},
			address : {
				required : "address required",
				all : "invalid"

			},
			district:{
				required : "district required",
			},
			state:{
				required :"state required",
			},
			panNum:{
				required : "pan number required",
			},
			pan:{
				required : "pancard required",
			},
			photo:{
				required : "photo required",
			}
		}
	})

	jQuery.validator.addMethod('lettersonly', function(value, element) {
		return /^[^-\s][a-zA-Z_\s-]+$/.test(value);
	});

	jQuery.validator.addMethod('space', function(value, element) {
		return /^[^-\s]+$/.test(value);
	});

	jQuery.validator.addMethod('all', function(value, element) {
		return /^[^-\s][a-zA-Z0-9_,.\s-]+$/.test(value);
	});

	jQuery.validator.addMethod('numericOnly', function(value, element) {
		return /^[0-9]+$/.test(value);
	});
})
