$(function() {
	var $UserregisterForm = $("#homeRegister");
	$UserregisterForm.validate({
		rules : {
			title : {
				required : true,
			
			},
			ownerName : {
				required : true,
				lettersonly : true,
				
			},
			email : {
				required : true,
				email : true
			},
			mobNo : {
				required : true,
				space : true,
				numericOnly : true,
				minlength : 10,
				maxlength : 12

			},
			description : {
				required : true,
			},
			address : {
				required : true,
				
			},
			city : {
				required : true,
				
			},
			state : {
				required : true,
				
			},
			pincode : {
				required : true,
				space : true,
				numericOnly : true,
				minlength : 5,
				maxlength : 6
			},
			img : {
				required : true
			}
		},
		messages : {
			title : {
				required : 'title must be required',
				
			},
			ownerName : {
				required : 'owner name must be required',
				lettersonly : 'invalid name',
				
			},
			email : {
				required : 'email name must be required',
				email : 'Invalid email'
			},
			mobNo : {
				required : 'mob no must be required',
				space : 'space not allowed',
				numericOnly : 'invalid mob no',
				minlength : 'min 10 digit',
				maxlength : 'max 12 digit'
			},
			description : {
				required : 'description must be required',
				
			},
			address : {
				required : 'address must be required',
				
			},
			city : {
				required : 'city must be required',
				
			},
			state : {
				required : 'state must be required',
				
			},
			pincode : {
				required : 'pincode must be required',
				space : 'space not allowed',
				numericOnly : 'invalid pincode ',
				minlength : 'min 5 digit',
				maxlength : 'max 6 digit'
			},
			img : {
				required : 'img must be required'
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
