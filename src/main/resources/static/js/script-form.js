function validate(){

    let f= false;

    let name=$(".field-username").val();
    let email=$(".field-email").val();
    let password=$(".field-password").val();
    let check= /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/;
    let passw = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,20}$/;


    if(name== '' || name== undefined){
        $(".error-username").html("username is required !!").addClass('text-danger');
        $(".field-username").addClass("is-invalid");
		f=false;
    }else if(name.length >=12){
        $(".error-username").html("username should be between 12 characters").addClass('text-danger');
        $(".field-username").addClass("is-invalid");
		f=false;
    }
    else{
        $(".error-username").html("OK !!").removeClass('text-danger').addClass("text-success");
        $(".field-username").removeClass("is-invalid").addClass("is-valid");
        f=true;

    }

    if(email== '' || email== undefined){
        $(".error-email").html("email is required !!").addClass("text-danger");
        $(".field-email").addClass("is-invalid");
        f=false;
    }else if(check.test(email)==false){
        $(".error-email").html("email should be in proper format(example@gmail.com)").addClass("text-danger");
        $(".field-email").addClass("is-invalid");
        f=false;
    }else{
        $(".error-email").html("OK!!").removeClass("text-danger").addClass("text-success");
        $(".field-email").removeClass("is-invalid").addClass("is-valid");
        f=true;
    }
    
    if(password== '' || password== undefined){
        $(".error-pass").html("password is required !!").addClass("text-danger");
        $(".field-password").addClass("is-invalid");
        f=false;
    }
    else if(passw.test(password)==false){
        $(".error-pass").html("password should between 6 to 20 characters which contain numeric digit,uppercase and one lowercase letter !!").addClass("text-danger");
        $(".field-password").addClass("is-invalid");
        f=false;
    }
    else{
        $(".error-pass").html("OK!!").removeClass("text-danger").addClass("text-success");
        $(".field-password").removeClass("is-invalid").addClass("is-valid");
        f=true;
    }

    
    return f;
}