import classes from './Button.module.css'


const Button = (props) => {
  
  const className = classes[props.type];

  console.log()

  return (
    <>
      <button type="button" className={className} onClick={props.onClick}> Add New + </button>
    </>
  );
};

export default Button;