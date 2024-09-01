export default function Validation(values) {

    const errors = {};


    if(values.input===null)
        errors.input = "Input has to be more than 4 characters!";
    else if(values.input.length <= 4)
        errors.input = "Input has to be more than 4 characters!";

    console.log(errors);
    console.log(values);
    
    

    return errors;
}